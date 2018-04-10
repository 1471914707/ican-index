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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            College college = Constant.ServiceFacade.getCollegeService().select(self.getId());
            List<Activity> activityList = Constant.ServiceFacade.getActivityService().list(null, college.getSchoolId(), "id desc", page, size);
            int total = Constant.ServiceFacade.getActivityService().count(null, college.getSchoolId());
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
}
