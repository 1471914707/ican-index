package com.ican.controller.school;

import com.ican.config.Constant;
import com.ican.domain.Activity;
import com.ican.domain.UserInfo;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Api("学校发起的活动")
@Controller
@RequestMapping("/school/activity")
public class ActivityController {
    private final static Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response) {
        return "/school/activity/list";
    }

    @ApiOperation("获取活动列表")
    @RequestMapping(value = "/listJson",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listJson(@RequestParam(value = "page",defaultValue = "1") int page,
                               @RequestParam(value = "size",defaultValue = "20") int size,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo userInfo = Ums.getUser(request);
        try {
            List<Activity> activityList = Constant.ServiceFacade.getActivityService().list(null, userInfo.getId(), "id desc", page, size);
            int total = Constant.ServiceFacade.getActivityService().count(null, userInfo.getId());
            HashMap data = new HashMap();
            data.put("list", activityList);
            data.put("total", total);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取活动列表异常", e);
            return result;
        }
    }

    @ApiOperation("获取单个活动信息")
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult info(@RequestParam(value = "id") int id,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (id <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        UserInfo userInfo = Ums.getUser(request);
        try {
            Activity activity = Constant.ServiceFacade.getActivityService().select(id);
            //不可以查看别人的活动
            if (activity == null || (activity != null && activity.getUserId() != userInfo.getId())) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            BaseResultUtil.setSuccess(result, activity);
            return result;
        } catch (Exception e) {
            logger.error("获取单个活动信息异常", e);
            return result;
        }
    }

    @ApiOperation("保存活动")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult listJson(Activity activity,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo userInfo = Ums.getUser(request);
        if (activity.getUserId() > 0) {
            if (activity.getUserId() != userInfo.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
        }
        try {
            activity.setUserId(userInfo.getId());
            int id = Constant.ServiceFacade.getActivityService().save(activity);
            BaseResultUtil.setSuccess(result, id);
            return result;
        } catch (Exception e) {
            logger.error("保存活动异常", e);
            return result;
        }
    }

    @ApiOperation("删除活动")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult listJson(@RequestParam("id") int id,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (id <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            Activity activity = Constant.ServiceFacade.getActivityService().select(id);
            if (activity == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Constant.ServiceFacade.getActivityService().delete(id);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("删除活动异常", e);
            return result;
        }
    }
}
