package com.ican.controller.college;

import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.service.CountsService;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Api("查看活动统计情况")
@Controller
@RequestMapping("/counts")
public class CountsCController {
    private final static Logger logger = LoggerFactory.getLogger(CountsCController.class);

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public String list(@RequestParam("activityId") int activityId,
                       HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("activityId", activityId);
        return "/college/counts/list";
    }

    @ApiOperation("获取进度的统计列表")
    @RequestMapping(value = "/task/listJson", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult task(@RequestParam("activityId") int activityId,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            //只限定学校和二级学院查看
            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            if (activity == null || (self.getRole() == UserInfoService.USER_COLLEGE && activity.getUserId() != self.getId())) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            if (self.getRole() == UserInfoService.USER_SCHOOL) {
                College college = Constant.ServiceFacade.getCollegeService().select(activity.getUserId());
                if (self.getId() != college.getSchoolId()) {
                    result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                    return result;
                }
            }
            int total = Constant.ServiceFacade.getCountsService().count(null, activityId, CountsService.TYPE_COMPLETE);
            List<Counts> countsList = Constant.ServiceFacade.getCountsService().list(null, activityId, CountsService.TYPE_COMPLETE, "gmt_create desc", 1, total);
            Map data = new HashMap();
            data.put("total", total);
            data.put("list", countsList);
            data.put("activity", activity);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取进度的统计列表异常", e);
            return result;
        }
    }

    @ApiOperation("获取答辩列表")
    @RequestMapping(value = "/ansewer/listJson", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult answer(@RequestParam("activityId") int activityId,
                             HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            //只限定学校和二级学院查看
            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            if (activity == null || (self.getRole() == UserInfoService.USER_COLLEGE && activity.getUserId() != self.getId())) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            if (self.getRole() == UserInfoService.USER_SCHOOL) {
                College college = Constant.ServiceFacade.getCollegeService().select(activity.getUserId());
                if (self.getId() != college.getSchoolId()) {
                    result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                    return result;
                }
            }
            int total = Constant.ServiceFacade.getCountsService().count(null, activityId, CountsService.TYPE_COMPLETE);
            List<Counts> countsList = Constant.ServiceFacade.getCountsService().list(null, activityId, CountsService.TYPE_COMPLETE, "gmt_create desc", 1, total);
            Map data = new HashMap();
            data.put("total", total);
            data.put("list", countsList);
            data.put("activity", activity);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取进度的统计列表异常", e);
            return result;
        }
    }

    @ApiOperation("获取评分列表")
    @RequestMapping(value = "/rating/listJson", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult rating(@RequestParam(value = "activityId", defaultValue = "0") int activityId,
                             @RequestParam(value = "answerId", defaultValue = "0") int answerId,
                             HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (activityId <= 0 || answerId <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            AnswerArrange answerArrange = Constant.ServiceFacade.getAnswerArrangeService().select(answerId);
            if (activity == null || activity.getUserId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            List<Rating> ratingList = new ArrayList<>();
            int total = Constant.ServiceFacade.getRatingService().count(null, answerId, 0, 0, 0);
            ratingList = Constant.ServiceFacade.getRatingService().list(null, answerId, 0, 0, 0, null, 1, total);
            int projectTotal = Constant.ServiceFacade.getProjectService().count(null, activityId, 0, 0, 0);
            List<Project> projectList = Constant.ServiceFacade.getProjectService().list(null, activityId, 0, 0, 0, 0,
                    0, 0, 0, null, 0, null, 1, projectTotal);
            Map data = new HashMap();
            data.put("ratingList", ratingList);
            data.put("projectList", projectList);
            data.put("answerArrange", answerArrange);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取评分列表异常", e);
            return result;
        }
    }
}
