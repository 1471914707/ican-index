package com.ican.controller.teacher;

import com.ican.config.Constant;
import com.ican.controller.school.StudentSController;
import com.ican.domain.Activity;
import com.ican.domain.Student;
import com.ican.domain.UserInfo;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
import com.ican.vo.StudentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("二级学院操作学生")
@Controller
@RequestMapping("/teacher/student")
public class StudentTController {
    private final static Logger logger = LoggerFactory.getLogger(StudentSController.class);

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public String index(@RequestParam(value = "activityId", defaultValue = "0", required = false) int activityId,
                        @RequestParam(value = "collegeId", defaultValue = "0", required = false) int collegeId,
                        HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("activityId", activityId);
        request.setAttribute("collegeId", collegeId);
        return "teacher/student/list";
    }

    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public String detail(@RequestParam(value = "studentId", defaultValue = "0") String studentId,
                         @RequestParam(value = "activityId",defaultValue = "0",required = false) int activityId,
                         HttpServletRequest request, HttpServletResponse response){
        request.setAttribute("studentId", studentId);
        request.setAttribute("activityId", activityId);
        return "teacher/student/detail";
    }

    @ApiOperation("获取学生列表")
    @ResponseBody
    @RequestMapping(value = "/listJson", method = RequestMethod.GET)
    public BaseResult listJson(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "20") int size,
                               @RequestParam(value = "activityId", defaultValue = "0") int activityId,
                               @RequestParam(value = "collegeId", defaultValue = "0") int collegeId,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            if (activityId <= 0) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            if (activity == null || activity.getUserId() != collegeId) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }

            List<Student> studentList = Constant.ServiceFacade.getStudentService().list(null, 0,
                    collegeId, 0, 0, 0, activity.getCurrent(), null, "current desc,id desc", page, size);
            List<StudentVO> studentVOList = Constant.ServiceFacade.getStudentWebService().listVO(studentList);
            int total = Constant.ServiceFacade.getStudentService().count(null, 0,
                    collegeId, 0, 0, 0, activity.getCurrent(), null);
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
        try {
            //基本信息
            Student student = Constant.ServiceFacade.getStudentService().select(id);
            if (student == null) {
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
}
