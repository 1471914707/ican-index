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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "school/student/list";
    }

    @ApiOperation("获取学生列表")
    @ResponseBody
    @RequestMapping(value = "/listJson", method = RequestMethod.GET)
    public BaseResult listJson(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "20") int size,
                               @RequestParam(value = "collegeId", defaultValue = "0") int collegeId,
                               @RequestParam(value = "departmentId", defaultValue = "0") int departmentId,
                               @RequestParam(value = "clazzId", defaultValue = "0") int clazzId,
                               @RequestParam(value = "teacherId", defaultValue = "0") int teacherId,
                               @RequestParam(value = "current", defaultValue = "0") int current,
                               @RequestParam(value = "jobId", defaultValue = "") String jobId,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            List<Student> studentList = Constant.ServiceFacade.getStudentService().list(null, self.getId(),
                    collegeId, departmentId, clazzId, teacherId, current, jobId, "current desc,id desc", page, size);
            Set<String> studentSet = new HashSet<>();
            for (Student student : studentList) {
                studentSet.add(student.getId() + "");
            }
            String ids = String.join(",", studentSet);
            List<UserInfo> userInfoList = Constant.ServiceFacade.getUserInfoService().list(ids, null, null,
                    UserInfoService.USER_STUDENT, null, page, size);
            Map userInfoMap = new HashMap();
            for (UserInfo userInfo : userInfoList) {
                userInfoMap.put(userInfo.getId(), userInfo);
            }
            List<StudentVO> studentVOList = new ArrayList<>();
            for (Student student : studentList) {
                StudentVO studentVO = new StudentVO(student, (UserInfo) userInfoMap.get(student.getId()));
                studentVOList.add(studentVO);
            }
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
            UserInfo userInfo = Constant.ServiceFacade.getUserInfoService().select(id);
            StudentVO studentVO = new StudentVO(student, userInfo);
            //认证图片
            List<AuthPhoto> authPhotoList = Constant.ServiceFacade.getAuthPhotoService().list(student.getId(), "id desc", 1, 100);
            studentVO.setAuthPhotoList(authPhotoList);
            //选题,只有一条
            List<PaperStudent> paperStudentList = Constant.ServiceFacade.getPaperStudentService().list(null, activityId, 0, self.getId(), 0, 0,
                    0, 0, id, 0, null, 1, 100);
            if (paperStudentList != null && paperStudentList.size() > 0) {
                Paper paper = Constant.ServiceFacade.getPaperService().select(paperStudentList.get(0).getPaperId());
                studentVO.setPaper(paper);
            }
            //学生的项目,只有一条
            List<Project> projectList = Constant.ServiceFacade.getProjectService().list(null, activityId, 0, 0, 0, 0,
                    0, 0, id, null, null, 1, 100);
            if (projectList != null || projectList.size() > 0) {
                Project project = projectList.get(0);
                studentVO.setProject(project);
            }
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
        } catch (Exception e) {
            logger.error("删除学生异常", e);
            return result;
        }

    }

}
