package com.ican.controller.student;

import com.ican.config.Constant;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("学生操作项目")
@Controller
@RequestMapping("/student/project")
public class ProjectSTController {
    private final static Logger logger = LoggerFactory.getLogger(ProjectSTController.class);

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public String index(@RequestParam("activityId") int activityId,
                        HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("activityId", activityId);
        UserInfo self = Ums.getUser(request);
        try {
            List<PaperStudent> list = Constant.ServiceFacade.getPaperStudentService().list(null, activityId, 0, 0, 0, 0, 0,
                    0, 0, self.getId(), 0, null, 1, 1);
            if (list == null || list.size() <= 0) {
                request.setAttribute("code", 1);
                request.setAttribute("msg", "你尚未选择课题");
                return "/student/project/list";
            } else {
                //有了课题，但没有项目
                List<Project> projectList = Constant.ServiceFacade.getProjectService().list(null, activityId, 0, 0, 0, 0, 0, 0,
                        self.getId(), null, 0, null, 1, 1);
                if (projectList == null || projectList.size() <= 0) {
                    request.setAttribute("code", 2);
                    return "/student/project/list";
                }
                //有了项目，还没有通过
                if (projectList.get(0).getStatus() != 2) {
                    request.setAttribute("editFlag", 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("code", "1");
            request.setAttribute("msg","访问出错");
        }
        request.setAttribute("code", 3);
        return "/student/project/list";
    }

    @ApiOperation("获取个人项目")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult info(@RequestParam(value = "activityId",required = true) int activityId,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            if (activity == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            List<Project> projectList = Constant.ServiceFacade.getProjectService().list(null, activityId,
                    0, 0, 0, 0, 0, 0, self.getId(), null, 0, null, 1, 10);
            ProjectVO project = null;
            if (projectList != null || projectList.size() > 0) {
                project = Constant.ServiceFacade.getProjectWebService().getProjectVO(projectList.get(0));
            }
            List<Follow> followList = Constant.ServiceFacade.getFollowService().list(null, 0, project.getId(),
                    FollowService.FOLLOW_TYPE_PROJECT, "id desc", 1, 100);
            Map data = new HashMap();
            data.put("project", project);
            data.put("followList", followList);
            data.put("activity", activity);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取个人项目异常", e);
            return result;
        }
    }


    @ApiOperation("保存个人项目")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult save(Project project,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            System.out.println("=-=========="+project.getId());
            Student student = Constant.ServiceFacade.getStudentService().select(self.getId());
            project.setClazzId(student.getClazzId());
            project.setTeacherId(student.getTeacherId());
            project.setDepartmentId(student.getDepartmentId());
            project.setCollegeId(student.getCollegeId());
            project.setCurrent(student.getCurrent());
            project.setMajorId(student.getMajorId());
            Constant.ServiceFacade.getProjectService().save(project);
            BaseResultUtil.setSuccess(result, project);
            return result;
        } catch (Exception e) {
            logger.error("获取个人项目异常", e);
            return result;
        }
    }


}
