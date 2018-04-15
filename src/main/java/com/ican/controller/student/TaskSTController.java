package com.ican.controller.student;

import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.service.UserInfoService;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
import com.ican.vo.PaperVO;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("操作任务")
@Controller
@RequestMapping("/task")
public class TaskSTController {

    private final static Logger logger = LoggerFactory.getLogger(TaskSTController.class);

    @RequestMapping(value = {"/student/list"}, method = RequestMethod.GET)
    public String index(@RequestParam("activityId") int activityId,
                        HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("activityId", activityId);
        UserInfo self = Ums.getUser(request);
        int warn = 1;
        Project project = new Project();
        try {
            if (self.getRole() == UserInfoService.USER_STUDENT) {
                List<Project> projectList = Constant.ServiceFacade.getProjectService().list(null, activityId, 0, 0,
                        0, 0, 0, 0, self.getId(), null, 0, "id desc", 1, 1);
                if (projectList != null && projectList.size() > 0) {
                    warn = projectList.get(0).getWarn();
                    project = projectList.get(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "/task/list";
        }
        request.setAttribute("warn", warn);
        request.setAttribute("project", project);
        return "/task/list";
    }

    @ApiOperation("学生获取任务列表")
    @RequestMapping(value = "/student/listJson", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listJson(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "20") int size,
                               @RequestParam(value = "activityId",required = true) int activityId,
                               @RequestParam(value = "status",required = false) int status,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            List<Task> taskList = new ArrayList<>();
            int total = 0;
            if (self.getRole() == UserInfoService.USER_STUDENT) {
                List<Project> projectList = Constant.ServiceFacade.getProjectService().list(null, activityId, 0, 0,
                        0, 0, 0, 0, self.getId(), null, 0, "id desc", 1, 1);
                if (projectList != null && projectList.size() > 0) {
                    taskList = Constant.ServiceFacade.getTaskService().list(null, activityId, 0, self.getId(),
                            projectList.get(0).getId(), status,"start_time desc", page, size);
                    total = Constant.ServiceFacade.getTaskService().count(null, activityId, 0, self.getId(), projectList.get(0).getId(),status);
                }
            }
            Map data = new HashMap();
            data.put("list", taskList);
            data.put("total", total);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("学生获取任务列表", e);
            return result;
        }
    }

    @ApiOperation("改变任务提醒")
    @RequestMapping(value = "/student/warn", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult warn(@RequestParam(value = "activityId",required = true) int activityId,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            int warn = 0;
            if (self.getRole() == UserInfoService.USER_STUDENT) {
                List<Project> projectList = Constant.ServiceFacade.getProjectService().list(null, activityId, 0, 0,
                        0, 0, 0, 0, self.getId(), null, 0, "id desc", 1, 1);
                if (projectList != null && projectList.size() > 0) {
                    warn = projectList.get(0).getWarn();
                    warn = warn == 2 ? 1 : 2;
                    Project project = new Project();
                    project.setId(projectList.get(0).getId());
                    project.setWarn(warn);
                    Constant.ServiceFacade.getProjectService().save(project);
                }
            }
            BaseResultUtil.setSuccess(result, warn);
            return result;
        } catch (Exception e) {
            logger.error("改变任务提醒异常", e);
            return result;
        }
    }

    @ApiOperation("学生保存任务")
    @RequestMapping(value = "/student/save", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult save(Task task,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (task.getActivityId() <= 0 || StringUtils.isEmpty(task.getTitle())) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        UserInfo self = Ums.getUser(request);
        try {
            Activity activity = Constant.ServiceFacade.getActivityService().select(task.getActivityId());
            if (activity == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            List<Project> projectList = Constant.ServiceFacade.getProjectService().list(null, task.getActivityId(), 0, 0,
                    0, 0, 0, 0, self.getId(), null, 0, "id desc", 1, 1);
            if (projectList == null || projectList.size() <= 0) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            task.setExecutorId(self.getId());
            task.setOwnerId(self.getId());
            Constant.ServiceFacade.getTaskService().save(task);
            Project project = projectList.get(0);
            double complete = 0;
            if (task.getId() <= 0) {
                int taskTotal = Constant.ServiceFacade.getTaskService().count(null, project.getActivityId(), 0, 0, project.getId(), 0);
                List<Task> taskList = Constant.ServiceFacade.getTaskService().list(null, project.getActivityId(), 0, 0, project.getId(), 3, null, 1, taskTotal);
                if (taskList != null) {
                    complete = (taskList.size() / (taskTotal * 1.0)) * 100;
                    System.out.println(complete);
                    Project newProject = new Project();
                    newProject.setId(project.getId());
                    newProject.setComplete((int)complete);
                    Constant.ServiceFacade.getProjectService().save(newProject);
                }
            }
            if (complete == 0) {
                complete = project.getComplete();
            }
            BaseResultUtil.setSuccess(result, (int) complete);
            return result;
        } catch (Exception e) {
            logger.error("学生保存任务异常", e);
            return result;
        }
    }


    /*@ApiOperation("学生保存任务")
    @RequestMapping(value = "/student/save", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult save(@RequestParam(value = "activityId",required = true) int activityId,
                           @RequestParam(value = "title",required = true,defaultValue = "") String title,
                           @RequestParam(value = "content",required = false,defaultValue = "") String content,
                           @RequestParam(value = "startTime",required = true,defaultValue = "") String startTime,
                           @RequestParam(value = "startTime",required = true,defaultValue = "") String endTime,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (activityId <= 0 || StringUtils.isEmpty(title)) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        UserInfo self = Ums.getUser(request);
        try {
            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            if (activity == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            List<Project> projectList = Constant.ServiceFacade.getProjectService().list(null, activityId, 0, 0,
                    0, 0, 0, 0, self.getId(), null, 0, "id desc", 1, 1);
            if (projectList == null || projectList.size() <= 0) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }

            Task task = new Task();
            task.setTitle(title);
            task.setContent(content);
            task.setStartTime(startTime);
            task.setEndTime(endTime);
            task.setActivityId(activityId);
            task.setProjectId(projectList.get(0).getId());
            task.setExecutorId(self.getId());
            task.setOwnerId(self.getId());
            Constant.ServiceFacade.getTaskService().save(task);
            BaseResultUtil.setSuccess(result, task);
            return result;
        } catch (Exception e) {
            logger.error("学生保存任务异常", e);
            return result;
        }
    }
*/
    @ApiOperation("学生更改任务状态")
    @RequestMapping(value = "/student/status", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult save(@RequestParam(value = "taskId",required = true) int taskId,
                           @RequestParam(value = "status",required = true) int status,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (taskId <= 0 || (status < 0 || status > 3)) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        UserInfo self = Ums.getUser(request);
        try {
            Task task = Constant.ServiceFacade.getTaskService().select(taskId);
            if (task == null || task.getExecutorId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            task.setStatus(status);
            Constant.ServiceFacade.getTaskService().save(task);
            //更新进度
            Project project = Constant.ServiceFacade.getProjectService().select(task.getProjectId());
            int taskTotal = Constant.ServiceFacade.getTaskService().count(null, project.getActivityId(), 0, 0, project.getId(), 0);
            List<Task> taskList = Constant.ServiceFacade.getTaskService().list(null, project.getActivityId(), 0, 0, project.getId(), 3, null, 1, taskTotal);
            double complete = 0;
            if (taskList != null) {
                System.out.println(taskList.size() + "+=======" + taskTotal);
                complete = (taskList.size() / (taskTotal * 1.0)) * 100;
                System.out.println(complete);
                Project newProject = new Project();
                newProject.setId(project.getId());
                newProject.setComplete((int)complete);
                Constant.ServiceFacade.getProjectService().save(newProject);
            }
            BaseResultUtil.setSuccess(result, (int)complete);
            return result;
        } catch (Exception e) {
            logger.error("学生更改任务状态异常", e);
            return result;
        }
    }

    @ApiOperation("删除任务状态")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult delete(@RequestParam(value = "taskId",required = true) int taskId,
                             HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (taskId <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        UserInfo self = Ums.getUser(request);
        try {
            Task task = Constant.ServiceFacade.getTaskService().select(taskId);
            if (task.getOwnerId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Constant.ServiceFacade.getTaskService().delete(taskId);
            int taskTotal = Constant.ServiceFacade.getTaskService().count(null, task.getActivityId(), 0, 0, task.getProjectId(), 0);
            List<Task> taskList = Constant.ServiceFacade.getTaskService().list(null, task.getActivityId(), 0, 0, task.getProjectId(), 3, null, 1, taskTotal);
            double complete = 0;
            if (taskList != null) {
                complete = (taskList.size() / (taskTotal * 1.0)) * 100;
                System.out.println(complete);
                Project newProject = new Project();
                newProject.setId(task.getProjectId());
                newProject.setComplete((int)complete);
                Constant.ServiceFacade.getProjectService().save(newProject);
            }
            BaseResultUtil.setSuccess(result, (int)complete);
            return result;
        } catch (Exception e) {
            logger.error("删除任务状态异常", e);
            return result;
        }
    }

}
