package com.ican.controller.college;

import com.ican.config.Constant;
import com.ican.domain.Activity;
import com.ican.domain.AnswerArrange;
import com.ican.domain.Arrange;
import com.ican.domain.UserInfo;
import com.ican.service.UserInfoService;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
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

@Api("二级学院发布答辫安排")
@Controller
@RequestMapping("/answerArrange")
public class AnswerArrangeCController {
    private final static Logger logger = LoggerFactory.getLogger(ArrangeCController.class);

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public String list(@RequestParam("activityId") String activityId,
                       HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("activityId", activityId);
        return "answerArrange/list";
    }


    @ApiOperation("学生打开答辩安排见面看到的数据")
    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public String studentRating(@RequestParam(value = "activityId") int activityId,
                                HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("activityId", activityId);
        return "answerArrange/student_list";
    }

    @ApiOperation("获取答辩安排列表")
    @RequestMapping(value = "/listJson", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listJson(@RequestParam(value = "activityId", required = true) int activityId,
                               HttpServletRequest request, HttpServletResponse response){
        UserInfo self = Ums.getUser(request);
        BaseResult result = BaseResultUtil.initResult();
        try {
            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            if (self == null || activityId <= 0 || activity == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            int total = Constant.ServiceFacade.getAnswerArrangeService().count(null, activityId, 0);
            List<AnswerArrange> answerArrangeList = Constant.ServiceFacade.getAnswerArrangeService().list(null, activityId, 0, "id desc", 1, total);

            Map data = new HashMap();
            data.put("role", self.getRole());
            data.put("list", answerArrangeList);
            data.put("activity", activity);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取答辩安排列表", e);
            return result;
        }
    }

    @ApiOperation("保存答辩安排")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult save(AnswerArrange answerArrange,
                           HttpServletRequest request, HttpServletResponse response){
        UserInfo self = Ums.getUser(request);
        BaseResult result = BaseResultUtil.initResult();
        if (self == null || self.getRole() != UserInfoService.USER_COLLEGE || answerArrange.getActivityId() <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            Activity activity = Constant.ServiceFacade.getActivityService().select(answerArrange.getActivityId());
            if (activity == null || self.getId() != activity.getUserId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Constant.ServiceFacade.getAnswerArrangeService().save(answerArrange);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("保存答辩安排异常", e);
            return result;
        }
    }

    @ApiOperation("删除答辩安排")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult delete(@RequestParam(value = "id", required = true) int id,
                             HttpServletRequest request, HttpServletResponse response){
        UserInfo self = Ums.getUser(request);
        BaseResult result = BaseResultUtil.initResult();
        if (self == null || id <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            AnswerArrange answerArrange = Constant.ServiceFacade.getAnswerArrangeService().select(id);
            if (answerArrange == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Activity activity = Constant.ServiceFacade.getActivityService().select(answerArrange.getActivityId());
            if (activity == null || activity.getUserId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Constant.ServiceFacade.getAnswerArrangeService().delete(id);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("删除答辩安排异常", e);
            return result;
        }
    }
}
