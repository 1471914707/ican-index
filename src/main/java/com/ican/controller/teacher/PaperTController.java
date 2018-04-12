package com.ican.controller.teacher;

import com.ican.config.Constant;
import com.ican.controller.school.StudentSController;
import com.ican.domain.*;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
import com.ican.vo.PaperVO;
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
import java.util.*;

@Api("二级学院操作学生")
@Controller
@RequestMapping("/teacher/paper")
public class PaperTController {
    private final static Logger logger = LoggerFactory.getLogger(StudentSController.class);

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public String index(@RequestParam("activityId") int activityId,
                        HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("activityId", activityId);
        return "/teacher/paper/list";
    }

    @ApiOperation("获取选题列表")
    @RequestMapping(value = "/listJson", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listJson(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "20") int size,
                               @RequestParam(value = "activityId",required = true) int activityId,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            List<Paper> paperList = Constant.ServiceFacade.getPaperService().list(null, activityId, 0, 0, 0, 0,
                    self.getId(), null, 0,"id desc", page, size);
            List<PaperVO> paperVOList = Constant.ServiceFacade.getPaperWebService().listVO(paperList);
            int total = Constant.ServiceFacade.getPaperService().count(null, activityId, 0, 0, 0, 0, self.getId(),
                    null,0);
            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            Map data = new HashMap();
            data.put("list", paperVOList);
            data.put("total", total);
            data.put("activity", activity);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取选题列表异常", e);
            return result;
        }
    }

    @ApiOperation("保存选题")
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult info(Paper paper,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            Activity activity = Constant.ServiceFacade.getActivityService().select(paper.getActivityId());
            paper.setCollegeId(activity.getUserId());
            paper.setCurrent(activity.getCurrent());
            College college = Constant.ServiceFacade.getCollegeService().select(activity.getUserId());
            paper.setSchoolId(college.getSchoolId());
            paper.setTeacherId(self.getId());
            List<DepartmentTeacher> departmentTeacherList = Constant.ServiceFacade.getDepartmentTeacherService().list(null, 0, self.getId(), null, 1, 100);
            for (DepartmentTeacher departmentTeacher : departmentTeacherList) {
                Department department = Constant.ServiceFacade.getDepartmentService().select(departmentTeacher.getDepartmentId());
                if (department.getCollegeId() == activity.getUserId()) {
                    paper.setDepartmentId(department.getId());
                    break;
                }
            }
            Constant.ServiceFacade.getPaperService().save(paper);
            BaseResultUtil.setSuccess(result, paper);
            return result;
        } catch (Exception e) {
            logger.error("保存选题异常", e);
            return result;
        }
    }

    @ApiOperation("删除选题")
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult delete(@RequestParam(value = "id", defaultValue = "0") int id,
                             HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            Paper paper = Constant.ServiceFacade.getPaperService().select(id);
            if (paper == null || paper.getTeacherId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Constant.ServiceFacade.getPaperService().delete(id);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("删除选题异常", e);
            return result;
        }
    }


    @ApiOperation("获取选题详情")
    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public BaseResult info(@RequestParam(value = "id", defaultValue = "0") int id,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            Paper paper = Constant.ServiceFacade.getPaperService().select(id);
            if (paper == null || paper.getTeacherId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            BaseResultUtil.setSuccess(result, paper);
            return result;
        } catch (Exception e) {
            logger.error("获取选题详情异常", e);
            return result;
        }
    }

    @ApiOperation("获取选题学生关联")
    @ResponseBody
    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public BaseResult student(@RequestParam(value = "id", defaultValue = "0") int id,
                              @RequestParam(value = "activityId", defaultValue = "0") int activityId,
                              HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (id <= 0 || activityId < 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            List<PaperStudent> paperStudentList = Constant.ServiceFacade.getPaperStudentService().list(null, activityId, 0, 0, 0, 0,
                    0, 0, self.getId(), 0, id, "id desc", 1, 100);
            List<StudentVO> studentVOList = new ArrayList<>();
            if (paperStudentList != null && paperStudentList.size() > 0) {
                Set<String> studentSet = new HashSet<>();
                for (PaperStudent paperStudent : paperStudentList) {
                    studentSet.add(paperStudent.getStudentId() + "");
                }
                String studentIds = String.join(",", studentSet);
                List<Student> studentList = Constant.ServiceFacade.getStudentService().list(studentIds, 0, 0, 0, 0,
                        0, 0, null, null, 1, 100);
                studentVOList = Constant.ServiceFacade.getStudentWebService().listVO(studentList);
            }

            BaseResultUtil.setSuccess(result, studentVOList);
            return result;
        } catch (Exception e) {
            logger.error("获取选题学生关联异常", e);
            return result;
        }
    }

}
