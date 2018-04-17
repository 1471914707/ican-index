package com.ican.controller.college;


import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.service.UserInfoService;
import com.ican.service.UserService;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
import com.ican.vo.PaperVO;
import com.ican.vo.StudentVO;
import com.ican.vo.TeacherVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.apache.bcel.classfile.ConstantDouble;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Api("操作评分组")
@Controller
@RequestMapping("/answerArrange/groups")
public class GroupsController {
    private final static Logger logger = LoggerFactory.getLogger(GroupsController.class);

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public String index(@RequestParam("answerId") int answerId,
                        @RequestParam("activityId") int activityId,
                        HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("answerId", answerId);
        request.setAttribute("activityId", activityId);
        UserInfo userInfo = Ums.getUser(request);
        request.setAttribute("role", userInfo.getRole());
        return "/answerArrange/groups/list";
    }

    @ApiOperation("获取评分分组列表")
    @RequestMapping(value = "/listJson", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listJson(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "20") int size,
                               @RequestParam(value = "answerId", defaultValue = "0") int answerId,
                               @RequestParam(value = "my", defaultValue = "0",required = false) int my,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (self == null || answerId <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            AnswerArrange answerArrange = Constant.ServiceFacade.getAnswerArrangeService().select(answerId);
            if (answerArrange == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            List<Groups> groupsList = Constant.ServiceFacade.getGroupsService().list(null, answerId, 0, "id desc", page, size);
            Set<String> groupsSet = new HashSet<>();
            Map groupsMap = new HashMap();
            for (int i=0; i<groupsList.size(); i++) {
                groupsSet.add(groupsList.get(i).getId() + "");
                groupsMap.put(groupsList.get(i).getId(), i);
            }
            String groupsIds = String.join(",", groupsSet);
            if (!StringUtils.isEmpty(groupsIds)) {
                List<GroupsTeacher> groupsTeacherList = Constant.ServiceFacade.getGroupsTeacherService().listByGroupsIds(groupsIds);
                for (GroupsTeacher groupsTeacher : groupsTeacherList) {
                    groupsList.get((Integer) groupsMap.get(groupsTeacher.getGroupsId())).getTeacherSet().add(groupsTeacher.getTeacherId() + "");
                }
                for (Groups groups : groupsList) {
                    groups.setTeacherIds(String.join(",", groups.getTeacherSet()));
                    groups.setTeacherSet(null);
                }
            }

            int total = Constant.ServiceFacade.getGroupsService().count(null, answerId, 0);
            //需要找出已经被选择的项目Id
            List<Groups> groupsProjectList = Constant.ServiceFacade.getGroupsService().list(null, answerId, 0, null, 1, total);
            StringBuilder projectIds = new StringBuilder();
            for (Groups groups : groupsProjectList) {
                projectIds.append(groups.getProjectIds() + ",");
            }
            Activity activity = Constant.ServiceFacade.getActivityService().select(answerArrange.getActivityId());
            Map data = new HashMap();
            data.put("activity", activity);
            data.put("answerArrange", answerArrange);
            data.put("list", groupsList);
            data.put("total", total);
            data.put("projectIds", projectIds.toString());
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取评分分组列表异常", e);
            return result;
        }
    }

    @ApiOperation("获取评分组")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult info(@RequestParam(value = "id", defaultValue = "0") int id,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            Groups groups = Constant.ServiceFacade.getGroupsService().select(id);
            BaseResultUtil.setSuccess(result, groups);
            return result;
        } catch (Exception e) {
            logger.error("获取评分组异常", e);
            return result;
        }
    }

    @ApiOperation("保存评分组")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult save(Groups groups,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (groups.getAnswerId() <= 0 || self.getRole() != UserInfoService.USER_COLLEGE) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            AnswerArrange answerArrange = Constant.ServiceFacade.getAnswerArrangeService().select(groups.getAnswerId());
            if (answerArrange == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Activity activity = Constant.ServiceFacade.getActivityService().select(answerArrange.getActivityId());
            if (activity == null || activity.getUserId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Constant.ServiceFacade.getGroupsService().save(groups);
            BaseResultUtil.setSuccess(result, groups);
            return result;
        } catch (Exception e) {
            logger.error("保存评分组异常", e);
            return result;
        }
    }

    @ApiOperation("评分组增加教师")
    @RequestMapping(value = "/addTeacher", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult addTeacher(@RequestParam(value = "groupsId", defaultValue = "0") int groupsId,
                                 @RequestParam(value = "teacherId", defaultValue = "0") int teacherId,
                                 HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (groupsId <= 0 || teacherId <= 0 || self == null) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            Groups groups = Constant.ServiceFacade.getGroupsService().select(groupsId);
            AnswerArrange answerArrange = Constant.ServiceFacade.getAnswerArrangeService().select(groups.getAnswerId());
            Activity activity = Constant.ServiceFacade.getActivityService().select(answerArrange.getActivityId());
            Teacher teacher = Constant.ServiceFacade.getTeacherService().select(teacherId);
            if (groups == null || self.getRole() != UserInfoService.USER_COLLEGE || teacher == null || activity.getUserId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            List<GroupsTeacher> groupsTeacherList = Constant.ServiceFacade.getGroupsTeacherService().list(null, groups.getAnswerId(), teacherId, groupsId, null, 1, 1);
            if (groupsTeacherList != null && groupsTeacherList.size() > 0) {
                result.setMsg("本小组已经关联了该教师");
                return result;
            }
            GroupsTeacher groupsTeacher = new GroupsTeacher();
            groupsTeacher.setTeacherId(teacherId);
            groupsTeacher.setGroupsId(groupsId);
            groupsTeacher.setAnswerId(groups.getAnswerId());
            Constant.ServiceFacade.getGroupsTeacherService().save(groupsTeacher);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("评分组增加教师异常", e);
            return result;
        }
    }

    @ApiOperation("评分组删除教师")
    @RequestMapping(value = "/deleteTeacher", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult deleteTeacher(@RequestParam(value = "groupsId", defaultValue = "0") int groupsId,
                                    @RequestParam(value = "teacherId", defaultValue = "0") int teacherId,
                                    HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (groupsId <= 0 || teacherId <= 0 || self == null) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            Groups groups = Constant.ServiceFacade.getGroupsService().select(groupsId);
            AnswerArrange answerArrange = Constant.ServiceFacade.getAnswerArrangeService().select(groups.getAnswerId());
            Activity activity = Constant.ServiceFacade.getActivityService().select(answerArrange.getActivityId());
            Teacher teacher = Constant.ServiceFacade.getTeacherService().select(teacherId);
            if (groups == null || self.getRole() != UserInfoService.USER_COLLEGE || teacher == null || activity.getUserId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            List<GroupsTeacher> groupsTeacherList = Constant.ServiceFacade.getGroupsTeacherService().list(null, groups.getAnswerId(), teacherId, groupsId, null, 1, 5);
            for (GroupsTeacher groupsTeacher : groupsTeacherList) {
                Constant.ServiceFacade.getGroupsTeacherService().delete(groupsTeacher.getId());
            }
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("评分组删除教师异常", e);
            return result;
        }
    }

    @ApiOperation("评分组增加项目")
    @RequestMapping(value = "/addProject", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult addProject(@RequestParam(value = "groupsId", defaultValue = "0") int groupsId,
                                 @RequestParam(value = "projectId", defaultValue = "0") int projectId,
                                 HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (groupsId <= 0 || projectId <= 0 || self == null) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            Groups groups = Constant.ServiceFacade.getGroupsService().select(groupsId);
            AnswerArrange answerArrange = Constant.ServiceFacade.getAnswerArrangeService().select(groups.getAnswerId());
            Activity activity = Constant.ServiceFacade.getActivityService().select(answerArrange.getActivityId());
            Project project = Constant.ServiceFacade.getProjectService().select(projectId);
            if (groups == null || self.getRole() != UserInfoService.USER_COLLEGE || project.getActivityId() != activity.getId() || activity.getUserId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            if (groups.getProjectIds().contains(projectId + "")) {
                result.setMsg("该项目已被关联到评分组上");
                return result;
            }
            if (("").equals(groups.getProjectIds())) {
                groups.setProjectIds(projectId + "");
            } else {
                groups.setProjectIds("," + projectId);
            }
            Constant.ServiceFacade.getGroupsService().save(groups);
            BaseResultUtil.setSuccess(result, groups);
            return result;
        } catch (Exception e) {
            logger.error("评分组增加项目异常", e);
            return result;
        }
    }

    @ApiOperation("评分组删除项目")
    @RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult deleteProject(@RequestParam(value = "groupsId", defaultValue = "0") int groupsId,
                                    @RequestParam(value = "projectId", defaultValue = "0") int projectId,
                                    HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (groupsId <= 0 || projectId <= 0 || self == null) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            Groups groups = Constant.ServiceFacade.getGroupsService().select(groupsId);
            AnswerArrange answerArrange = Constant.ServiceFacade.getAnswerArrangeService().select(groups.getAnswerId());
            Activity activity = Constant.ServiceFacade.getActivityService().select(answerArrange.getActivityId());
            Project project = Constant.ServiceFacade.getProjectService().select(projectId);
            if (groups == null || self.getRole() != UserInfoService.USER_COLLEGE || project.getActivityId() != activity.getId() || activity.getUserId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            if (groups.getProjectIds().length() > 0 && groups.getProjectIds().contains(projectId + "")) {
                String[] projectIds = groups.getProjectIds().split(",");
                Set<String> projectIdSet = new HashSet<>();
                for (String id : projectIds) {
                    if (id.equals(projectId + "")) {
                        continue;
                    } else {
                        projectIdSet.add(id);
                    }
                }
                groups.setProjectIds(String.join(",", projectIdSet));
                Constant.ServiceFacade.getGroupsService().save(groups);
            } else {
                result.setMsg("该项目不在评分组上");
                return result;
            }
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("评分组删除项目异常", e);
            return result;
        }
    }

    @ApiOperation("获取活动特定数据")
    @ResponseBody
    @RequestMapping(value = "/infoListJson",method = RequestMethod.GET)
    public BaseResult infoListJson(@RequestParam(value = "activityId") int activityId,
                                   HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (self == null || self.getRole() != UserInfoService.USER_COLLEGE) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            if (activity == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            //教师数据
            HashMap teacherData = Constant.ServiceFacade.getTeacherWebService().listVO(0, activity.getUserId(), 0, null);
            List<TeacherVO> teacherVOList = (List<TeacherVO>) teacherData.get("list");
            //学生数据
            List<Student> studentList = Constant.ServiceFacade.getStudentService().list(null, 0, activity.getUserId(), 0, 0, 0, 0, null, null, 1, 2500);
            List<StudentVO> studentVOList = Constant.ServiceFacade.getStudentWebService().listVO(studentList);
            //系部数据
            List<Department> departmentList = Constant.ServiceFacade.getDepartmentService().list(null, 0, activity.getUserId(), null, 1, 1000);
            //专业数据
            List<Major> majorList = Constant.ServiceFacade.getMajorService().list(null, 0, activity.getUserId(), 0, 0, null, 1, 1000);
            //班级数据
            List<Clazz> clazzList = Constant.ServiceFacade.getClazzService().list(null, 0, activity.getUserId(), 0,
                    0, activity.getCurrent(), null, 1, 1000);
            //项目数据
            List<Project> projectList = Constant.ServiceFacade.getProjectService().list(null, activityId, 0, 0, 0, 0, 0, 0,
                    0, null, 0, null, 1, 2000);

            Map data = new HashMap();
            data.put("teacherList", teacherVOList);
            data.put("projectList", projectList);
            data.put("studentList", studentVOList);
            data.put("clazzList", clazzList);
            data.put("majorList", majorList);
            data.put("departmentList", departmentList);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取活动特定数据异常", e);
            return result;
        }
    }

}
