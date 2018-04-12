package com.ican.controller.teacher;


import com.ican.config.Constant;
import com.ican.controller.college.FileCController;
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

@Api("教师")
@Controller
@RequestMapping("/teacher")
public class IndexTController {
    private final static Logger logger = LoggerFactory.getLogger(IndexTController.class);

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response) {
        return "teacher/activity/list";
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
            List<DepartmentTeacher> departmentTeacherList = Constant.ServiceFacade.getDepartmentTeacherService().list(null, 0, self.getId(), null, 1, 1000);
            Set<String> departmentSet = new HashSet<>();
            for (DepartmentTeacher departmentTeacher : departmentTeacherList) {
                departmentSet.add(departmentTeacher.getDepartmentId() + "");
            }
            String departmentIds = String.join(",", departmentSet);
            List<Department> departmentList = Constant.ServiceFacade.getDepartmentService().list(departmentIds, 0, 0, null, 1, 1000);
            Set<String> collegeSet = new HashSet<>();
            for (Department department : departmentList) {
                collegeSet.add(department.getCollegeId() + "");
            }
            String collegeIds = String.join(",", collegeSet);
            List<Activity> activityList = new ArrayList<>();
            int total = 0;
            if (!StringUtils.isEmpty(collegeIds)) {
                activityList = Constant.ServiceFacade.getActivityService().list(collegeIds, "id desc", page, size);
                total = Constant.ServiceFacade.getActivityService().count(collegeIds);
            }
            Set<String> userSet = new HashSet<>();
            for (Activity activity : activityList) {
                userSet.add(activity.getUserId() + "");
            }
            String userIds = String.join(",", userSet);
            List<UserInfo> userInfoList = new ArrayList<>();
            if (!StringUtils.isEmpty(userIds)) {
                userInfoList = Constant.ServiceFacade.getUserInfoService().list(userIds, null, null, 0, null, 1, 100);
            }

            Map data = new HashMap();
            data.put("list", activityList);
            data.put("total", total);
            data.put("userList", userInfoList);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取活动列表异常", e);
            return result;
        }
    }

}
