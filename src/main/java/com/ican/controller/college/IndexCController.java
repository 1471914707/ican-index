package com.ican.controller.college;

import com.ican.config.Constant;
import com.ican.domain.Activity;
import com.ican.domain.College;
import com.ican.domain.UserInfo;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
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

@Api("二级学院主页")
@Controller
@RequestMapping("/college")
public class IndexCController {
    private final Logger logger = LoggerFactory.getLogger(IndexCController.class);

    @RequestMapping(value = {"/", "", "/list"}, method = RequestMethod.GET)
    public String index() {
        return "college/activity/list";
    }

    @ApiOperation("获取活动列表")
    @RequestMapping(value = "/activity/listJson", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult activityListJson(@RequestParam(value = "page",defaultValue = "1") int page,
                                   @RequestParam(value = "size",defaultValue = "20") int size,
                                   HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            List<Activity> activityList = Constant.ServiceFacade.getActivityService().list(null, self.getId(), "id desc", page, size);
            int total = Constant.ServiceFacade.getActivityService().count(null, self.getId());

            Map data = new HashMap();
            data.put("list", activityList);
            data.put("total", total);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取活动列表异常", e);
            return result;
        }
    }

    @ApiOperation("保存活动")
    @RequestMapping(value = "/activity/save",method = RequestMethod.POST)
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

    @ApiOperation("开启选题")
    @RequestMapping(value = "/activity/paper",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult paper(@RequestParam("id") int id,
                            HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (id <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            Activity activity = Constant.ServiceFacade.getActivityService().select(id);
            if (activity == null || activity.getUserId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            if (activity.getPaper() == 1) {
                activity.setPaper(2);
            } else {
                activity.setPaper(1);
            }
            Constant.ServiceFacade.getActivityService().save(activity);
            BaseResultUtil.setSuccess(result, activity.getPaper());
            return result;
        } catch (Exception e) {
            logger.error("开启选题异常", e);
            return result;
        }
    }

    @ApiOperation("删除活动")
    @RequestMapping(value = "/activity/delete",method = RequestMethod.POST)
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
