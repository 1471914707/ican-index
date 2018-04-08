package com.ican.controller.school;

import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.service.UserInfoService;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
import com.ican.vo.ProjectVO;
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

@Api("学校对项目的操作")
@Controller
@RequestMapping("/school/project")
public class ProjectSController {
    private final static Logger logger = LoggerFactory.getLogger(ProjectSController.class);

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String index() {
        return "/school/project/list";
    }

    @ApiOperation("获取项目列表")
    @RequestMapping(value = "/listJson", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listJson(@RequestParam(value = "collegeId", defaultValue = "0") int collegeId,
                               @RequestParam(value = "departmentId", defaultValue = "0") int departmentId,
                               @RequestParam(value = "classId", defaultValue = "0") int classId,
                               @RequestParam(value = "teacherId", defaultValue = "0") int teacherId,
                               @RequestParam(value = "current", defaultValue = "0") int current,
                               @RequestParam(value = "activityId", defaultValue = "0") int activityId,
                               @RequestParam(value = "title", defaultValue = "",required = false) String title,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "20") int size,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            List<Project> projectList = Constant.ServiceFacade.getProjectService().list(null, activityId, current, self.getId(), collegeId, departmentId,
                    classId, teacherId, 0, title, "id desc", page, size);
            //关联学院,因为学院不多，所以全拿出来
            List<College> collegeList = Constant.ServiceFacade.getCollegeService().list(null, self.getId(), null, 1, 1000);
            Map collegeMap = new HashMap();
            for (College college : collegeList) {
                collegeMap.put(college.getId(), college);
            }
            //关联教师
            Set<String> teacherSet = new HashSet<>();
            //系
            Set<String> departmentSet = new HashSet<>();
            for (Project project : projectList) {
                teacherSet.add(project.getTeacherId() + "");
                departmentSet.add(project.getDepartmentId() + "");
            }
            String teacherIds = String.join(",", teacherSet);
            String departmentIds = String.join(",", departmentSet);
            List<UserInfo> teacherList = Constant.ServiceFacade.getUserInfoService().list(teacherIds, null, null,
                    UserInfoService.USER_TEACHER, null, page, size);
            List<Department> departmentList = Constant.ServiceFacade.getDepartmentService().list(departmentIds, self.getId(), 0, null, page, size);
            Map teacherMap = new HashMap();
            Map departmentMap = new HashMap();
            for (UserInfo userInfo : teacherList) {
                teacherMap.put(userInfo.getId(), userInfo);
            }
            for (Department department : departmentList) {
                departmentMap.put(department.getId(), department);
            }
            List<ProjectVO> projectVOList = new ArrayList<>();
            for (Project project : projectList) {
                ProjectVO projectVO = new ProjectVO();
                projectVO.setProject(project);
                projectVO.setId(project.getId());
                projectVO.setCollegeId(((College) collegeMap.get(project.getCollegeId())).getId());
                projectVO.setCollegeName(((College) collegeMap.get(project.getCollegeId())).getName());
                projectVO.setDepartmentId(((Department) departmentMap.get(project.getDepartmentId())).getId());
                projectVO.setDepartmentName(((Department) departmentMap.get(project.getDepartmentId())).getName());
                projectVO.setTeacherId(((UserInfo) teacherMap.get(project.getTeacherId())).getId());
                projectVO.setTeacherName(((UserInfo) teacherMap.get(project.getTeacherId())).getName());
                projectVOList.add(projectVO);
            }
            int total = Constant.ServiceFacade.getProjectService().count(null, activityId, current, self.getId(), collegeId, departmentId,
                    classId, teacherId, 0, title);
            Map data = new HashMap();
            data.put("list", projectVOList);
            data.put("total", total);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取项目列表异常", e);
            return result;
        }
    }

    @ApiOperation("获取项目详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public BaseResult info(@RequestParam(value = "id",defaultValue = "0") int id,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            Project project = Constant.ServiceFacade.getProjectService().select(id);
            if (project != null || project.getSchoolId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            ProjectVO projectVO = Constant.ServiceFacade.getProjectWebService().getProjectVO(project);
            BaseResultUtil.setSuccess(result, projectVO);
            return result;
        } catch (Exception e) {
            logger.error("获取项目详情异常", e);
            return result;
        }
    }
}
