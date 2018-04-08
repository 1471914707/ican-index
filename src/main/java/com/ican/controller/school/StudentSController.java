package com.ican.controller.school;

import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.service.UserInfoService;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
import com.ican.vo.StudentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Api("学校操作学生")
@Controller
@RequestMapping("/school/student")
public class StudentSController {
    private final static Logger logger = LoggerFactory.getLogger(StudentSController.class);

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public String index(@RequestParam(value = "activityId", defaultValue = "0", required = false) int activityId,
                        HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("activityId", activityId);
        return "school/student/list";
    }

    @RequestMapping(value = "detail",method = RequestMethod.GET)
    public String detail(@RequestParam(value = "studentId", defaultValue = "0") String studentId,
                         @RequestParam(value = "activityId",defaultValue = "0",required = false) int activityId,
                         HttpServletRequest request, HttpServletResponse response){
        request.setAttribute("studentId", studentId);
        request.setAttribute("activityId", activityId);
        return "school/student/detail";
    }

    @ApiOperation("获取学生列表")
    @ResponseBody
    @RequestMapping(value = "/listJson", method = RequestMethod.GET)
    public BaseResult listJson(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "20") int size,
                               @RequestParam(value = "activityId", defaultValue = "0") int activityId,
                               @RequestParam(value = "collegeId", defaultValue = "0") int collegeId,
                               @RequestParam(value = "departmentId", defaultValue = "0") int departmentId,
                               @RequestParam(value = "clazzId", defaultValue = "0") int clazzId,
                               @RequestParam(value = "teacherId", defaultValue = "0") int teacherId,
                               @RequestParam(value = "current", defaultValue = "0") int current,
                               @RequestParam(value = "jobId", defaultValue = "",required = false) String jobId,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            List<Student> studentList = Constant.ServiceFacade.getStudentService().list(null, self.getId(),
                    collegeId, departmentId, clazzId, teacherId, current, jobId, "current desc,id desc", page, size);
            List<StudentVO> studentVOList = Constant.ServiceFacade.getStudentWebService().listVO(studentList);
            int total = Constant.ServiceFacade.getStudentService().count(null, self.getId(),
                    collegeId, departmentId, clazzId, teacherId, current, jobId);
            Map data = new HashMap();
            data.put("list", studentVOList);
            data.put("total", total);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取学生列表异常", e);
            return result;
        }
    }

    @ApiOperation("获取学生详情")
    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public BaseResult info(@RequestParam(value = "id", defaultValue = "0") int id,
                           @RequestParam(value = "activityId", defaultValue = "0") int activityId,
                           HttpServletRequest request, HttpServletResponse response){
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            //基本信息
            Student student = Constant.ServiceFacade.getStudentService().select(id);
            if (student == null || student.getSchoolId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            StudentVO studentVO = Constant.ServiceFacade.getStudentWebService().studentVO(student, activityId);
            BaseResultUtil.setSuccess(result, studentVO);
            return result;
        } catch (Exception e) {
            logger.error("获取学生详情异常", e);
            return result;
        }
    }

    @ApiOperation("删除学生")
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult delete(@RequestParam(value = "id", defaultValue = "0") int id,
                             HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            Student student = Constant.ServiceFacade.getStudentService().select(id);
            if (student == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            student.setSchoolId(0);
            student.setCollegeId(0);
            student.setClazzId(0);
            student.setTeacherId(0);
            student.setDepartmentId(0);
            Constant.ServiceFacade.getStudentService().save(student);
            BaseResultUtil.setSuccess(result, student);
            return result;
        } catch (Exception e) {
            logger.error("删除学生异常", e);
            return result;
        }

    }

}
