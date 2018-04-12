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

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
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
                    classId, teacherId, 0, title, 0,"id desc", page, size);
            List<ProjectVO> projectVOList = Constant.ServiceFacade.getProjectWebService().projectVOList(projectList);
            int total = Constant.ServiceFacade.getProjectService().count(null, activityId, current, self.getId(), collegeId, departmentId,
                    classId, teacherId, 0, title,0);
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
    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public BaseResult info(@RequestParam(value = "id",defaultValue = "0") int id,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            Project project = Constant.ServiceFacade.getProjectService().select(id);
            if (project == null || project.getSchoolId() != self.getId()) {
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
