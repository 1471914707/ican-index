package com.ican.controller.teacher;

import com.ican.async.EventModel;
import com.ican.async.EventProducer;
import com.ican.async.EventType;
import com.ican.config.Constant;
import com.ican.controller.college.ProjectCController;
import com.ican.domain.*;
import com.ican.service.FollowService;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
import com.ican.vo.ProjectVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import javax.swing.*;
import java.io.FileOutputStream;
import java.util.*;

@Api("教师对项目的操作")
@Controller
@RequestMapping("/teacher/project")
public class ProjectTController {
    private final static Logger logger = LoggerFactory.getLogger(ProjectTController.class);

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public String index(@RequestParam(value = "activityId") int activityId,
                        @RequestParam(value = "collegeId", defaultValue = "0") int collegeId,
                        HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("activityId", activityId);
        request.setAttribute("collegeId", collegeId);
        return "teacher/project/list";
    }

    @ApiOperation("获取项目列表")
    @RequestMapping(value = "/listJson", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listJson(@RequestParam(value = "collegeId", defaultValue = "0") int collegeId,
                               @RequestParam(value = "activityId", defaultValue = "0") int activityId,
                               @RequestParam(value = "type", defaultValue = "0",required = false) int type,
                               @RequestParam(value = "status", defaultValue = "0",required = false) int status,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "20") int size,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            List<Project> projectList = new ArrayList<>();
            int total = 0;
            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            if (activity == null || activity.getUserId() != collegeId) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            if (type == 1) {
                List<Major> majorList = Constant.ServiceFacade.getMajorService().list(null, 0, collegeId, 0, self.getId(), null, 1, 10);
                Set<String> majorSet = new HashSet<>();
                for (Major major : majorList) {
                    majorSet.add(major.getId() + "");
                }
                String majorIds = String.join(",", majorSet);
                if (!StringUtils.isEmpty(majorIds)) {
                    projectList = Constant.ServiceFacade.getProjectService().list(majorIds, activityId, collegeId, self.getId(), status, "id desc", page, size);
                    total = Constant.ServiceFacade.getProjectService().count(majorIds, activityId, collegeId, self.getId(), status);
                }
            } else {
                projectList = Constant.ServiceFacade.getProjectService().list(null, activityId, 0, 0, collegeId, 0,
                        0, self.getId(), 0, null, status, "id desc", page, size);
                total = Constant.ServiceFacade.getProjectService().count(null, activityId, 0, 0, 0, 0,
                        0, self.getId(), 0, null,status);
            }

            List<ProjectVO> projectVOList = Constant.ServiceFacade.getProjectWebService().projectVOList(projectList);

            Map data = new HashMap();
            data.put("list", projectVOList);
            data.put("total", total);
            data.put("activity", activity);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取项目列表异常", e);
            return result;
        }
    }

    @ApiOperation("改变项目状态")
    @ResponseBody
    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public BaseResult status(@RequestParam(value = "id", defaultValue = "0") int id,
                             HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (id <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        UserInfo self = Ums.getUser(request);
        try {
            Project project = Constant.ServiceFacade.getProjectService().select(id);
            if (project == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            if (project.getStatus() == 1) {
                project.setStatus(2);
            } else {
                project.setStatus(1);
            }
            Constant.ServiceFacade.getProjectService().save(project);
            BaseResultUtil.setSuccess(result, project.getStatus());
            return result;
        } catch (Exception e) {
            logger.error("改变项目状态异常", e);
            return result;
        }
    }

    @ApiOperation("获取项目详情")
    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public BaseResult info(@RequestParam(value = "id",defaultValue = "0") int id,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            Project project = Constant.ServiceFacade.getProjectService().select(id);
            if (project == null || project.getCollegeId() != self.getId()) {
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

    @ApiOperation("获取项目审核详情")
    @ResponseBody
    @RequestMapping(value = "/followList", method = RequestMethod.GET)
    public BaseResult followList(@RequestParam(value = "id",defaultValue = "0") int id,
                                HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            List<Follow> followList = Constant.ServiceFacade.getFollowService().list(null, 0, id, FollowService.FOLLOW_TYPE_PROJECT, null, 1, 100);
            BaseResultUtil.setSuccess(result, followList);
            return result;
        } catch (Exception e) {
            logger.error("获取项目审核详情异常", e);
            return result;
        }
    }

    @ApiOperation("保存项目审核")
    @ResponseBody
    @RequestMapping(value = "/followSave", method = RequestMethod.GET)
    public BaseResult followSave(@RequestParam(value = "id",defaultValue = "0") int id,
                                 @RequestParam(value = "content",defaultValue = "") String content,
                                 @RequestParam(value = "mode",defaultValue = "0") int mode,
                                 @RequestParam(value = "type",defaultValue = "0") int type,
                                 HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            Project project = Constant.ServiceFacade.getProjectService().select(id);
            if (project == null || project.getTeacherId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            if (type == 2) {
                //专业审核
                if (mode == 2) {
                    //专业审核之下通过即为通过
                    project.setStatus(2);
                    Constant.ServiceFacade.getProjectService().save(project);
                }
            }
            Follow follow = new Follow();
            follow.setFollowType(FollowService.FOLLOW_TYPE_PROJECT);
            follow.setFollowId(id);
            follow.setContent(content);
            follow.setFollowUserName(self.getName());
            follow.setMode(mode);
            follow.setFollowUserId(self.getId());
            int newId = Constant.ServiceFacade.getFollowService().save(follow);
            if (newId > 0) {
                new EventProducer().fireEvent(new EventModel(EventType.PROJECT_FOLLOW)
                .setActorId(self.getId()).setEntityId(id).setEntityOwnerId(project.getStudentId()));
            }
            BaseResultUtil.setSuccess(result, follow);
            return result;
        } catch (Exception e) {
            logger.error("保存项目审核异常", e);
            return result;
        }
    }
}
