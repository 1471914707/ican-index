package com.ican.controller.student;

import com.ican.config.Constant;
import com.ican.controller.teacher.IndexTController;
import com.ican.domain.*;
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

@Api("学生")
@Controller
@RequestMapping("/student")
public class IndexSTController {
    private final static Logger logger = LoggerFactory.getLogger(IndexSTController.class);

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response) {
        return "student/activity/list";
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
            //找出该学生所在二级学院的活动
            Student student = Constant.ServiceFacade.getStudentService().select(self.getId());
            UserInfo college = Constant.ServiceFacade.getUserInfoService().select(student.getCollegeId());
            List<Activity> activityList = Constant.ServiceFacade.getActivityService().list(null, student.getCollegeId(), "id desc", page, size);
            int total = Constant.ServiceFacade.getActivityService().count(null, student.getCollegeId());

            Map data = new HashMap();
            data.put("list", activityList);
            data.put("total", total);
            data.put("college", college);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取活动列表异常", e);
            return result;
        }
    }
}
