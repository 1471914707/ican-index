package com.ican.controller.college;


import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.service.UserInfoService;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
import com.ican.vo.ProjectVO;
import com.ican.vo.RatingVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.bcel.Const;
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
import java.util.concurrent.locks.Condition;
import java.util.regex.Pattern;

@Api("评价")
@Controller
@RequestMapping("/college/rating")
public class RatingController {
    private final static Logger logger = LoggerFactory.getLogger(RatingController.class);

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public String index(@RequestParam("activityId") int activityId,
                        @RequestParam("answerId") int answerId,
                        HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("answerId", answerId);
        request.setAttribute("activityId", activityId);
        return "college/rating/list";
    }

    @ApiOperation("获取评分列表")
    @RequestMapping(value = "/listJson", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listJson(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "20") int size,
                               @RequestParam(value = "activityId", defaultValue = "0") int activityId,
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
            if (activity == null || answerArrange == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            List<Groups> groupsList = Constant.ServiceFacade.getGroupsService().list(null, answerId, 0, null, page, size);
            List<Rating> ratingList = new ArrayList<>();
            List<ProjectVO> projectVOList = new ArrayList<>();
            Set<String> groupsSet = new HashSet<>();
            Set<String> projectSet = new HashSet<>();
            for (Groups groups : groupsList) {
                groupsSet.add(groups.getId() + "");
                String[] projectArray = groups.getProjectIds().split(",");
                for (String string : projectArray) {
                    if (!("").equals(string.trim())) {
                        projectSet.add(string + "");
                    }
                }
            }
            String groupsIds = String.join(",", groupsSet);
            if (!StringUtils.isEmpty(groupsIds)) {
                ratingList = Constant.ServiceFacade.getRatingService().list(groupsIds);
            }
            String projectIds = String.join(",", projectSet);
            if (!StringUtils.isEmpty(projectIds)) {
                List<Project> projectList = Constant.ServiceFacade.getProjectService().list(projectIds, 0, 0, 0, 0, 0,
                        0, 0, 0, null, 0, null, 1, projectSet.size());
                projectVOList = Constant.ServiceFacade.getProjectWebService().projectVOList(projectList);
            }
            int total = Constant.ServiceFacade.getProjectService().count(null, activityId, 0, 0, 0, 0, 0, 0,
                    0, null, 0);

            Map data = new HashMap();
            data.put("activity", activity);
            data.put("answerArrange", answerArrange);
            data.put("ratingList", ratingList);
            data.put("projectList", projectVOList);
            data.put("total", total);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取评分列表异常", e);
            return result;
        }
    }


  /*  @ApiOperation("获取评分列表")
    @RequestMapping(value = "/listJson", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listJson(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "20") int size,
                               @RequestParam(value = "activityId", defaultValue = "0") int activityId,
                               @RequestParam(value = "teacherId", defaultValue = "0",required = false) int teacherId,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            List<Rating> ratingList = new ArrayList<>();
            int total = 0;

            List<RatingVO> ratingVOList = new ArrayList<>();
            if (teacherId <= 0) {
                ratingList = Constant.ServiceFacade.getRatingService().list(null, activityId, 0, 0, 0, null, page, size);
                Set<String> projectSet = new HashSet<>();
                Set<String> teacherSet = new HashSet<>();
                for (Rating rating : ratingList) {
                    projectSet.add(rating.getProjectId() + "");
                    teacherSet.add(rating.getTeacherId() + "");
                }
                String projectIds = String.join(",", projectSet);
                List<ProjectVO> projectVOList = new ArrayList<>();
                if (!StringUtils.isEmpty(projectIds)) {
                    List<Project> projectList = Constant.ServiceFacade.getProjectService().list(projectIds, activityId, 0, 0, 0, 0, 0, 0,
                            0, null, 0, null, 1, 100);
                    projectVOList = Constant.ServiceFacade.getProjectWebService().projectVOList(projectList);
                }

                total = Constant.ServiceFacade.getRatingService().count(null, activityId, 0, 0, 0);
            } else {


                List<GroupsTeacher> groupsTeacherList = Constant.ServiceFacade.getGroupsTeacherService().list(null, activityId, teacherId, 0, null, page, size);
                Set<String> groupsSet = new HashSet<>();
                for (GroupsTeacher groupsTeacher : groupsTeacherList) {
                 //   groupsSet.add(groupsTeacher.getGroupId() + "");
                }
                String groupsIds = String.join(",", groupsSet);
                if (!StringUtils.isEmpty(groupsIds)) {
                 //   groupsList = Constant.ServiceFacade.getGroupsService().list(groupsIds, activityId, 0, null, "id desc", page, size);
                   // total = Constant.ServiceFacade.getGroupsService().count(groupsIds, activityId, 0, null, 0);
                }
            }

            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            Map data = new HashMap();
            data.put("activity", activity);
            data.put("list", ratingList);
            data.put("total", total);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取选题列表异常", e);
            return result;
        }
    }

    @ApiOperation("获取评分组")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult info(@RequestParam(value = "id", defaultValue = "0") int id,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            Groups groups = Constant.ServiceFacade.getGroupsService().select(id);
            BaseResultUtil.setSuccess(result, groups);
            return result;
        } catch (Exception e) {
            logger.error("获取评分组异常", e);
            return result;
        }
    }

    @ApiOperation("保存评分组")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult save(@RequestParam(value = "id", defaultValue = "0") int id,
                           @RequestParam(value = "activityId", defaultValue = "0") int activityId,
                           @RequestParam(value = "userId", defaultValue = "0") int userId,
                           @RequestParam(value = "name", defaultValue = "") String name,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            Groups groups = new Groups();
            groups.setId(id);
           // groups.setActivityId(activityId);
            groups.setUserId(userId);
            groups.setName(name);
            Constant.ServiceFacade.getGroupsService().save(groups);
            BaseResultUtil.setSuccess(result, groups);
            return result;
        } catch (Exception e) {
            logger.error("保存评分组异常", e);
            return result;
        }
    }

    @ApiOperation("评分组增加教师")
    @RequestMapping(value = "/addTeacher", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult addTeacher(@RequestParam(value = "id", defaultValue = "0") int id,
                                 @RequestParam(value = "teacherId", defaultValue = "0") int teacherId,
                                 HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (id <= 0 || teacherId <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        UserInfo self = Ums.getUser(request);
        try {
            Groups groups = Constant.ServiceFacade.getGroupsService().select(id);
            if (groups == null || self.getRole() != UserInfoService.USER_COLLEGE) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
           *//* List<GroupsTeacher> groupsTeacherList = Constant.ServiceFacade.getGroupsTeacherService().list(null, groups.getActivityId(), teacherId, id, null, 1, 10);
            if (groupsTeacherList != null && groupsTeacherList.size() > 0) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }*//*
            GroupsTeacher groupsTeacher = new GroupsTeacher();
            *//*groupsTeacher.setActivityIds(groups.getActivityId());
            groupsTeacher.setGroupId(id);*//*
            groupsTeacher.setTeacherId(teacherId);
            Constant.ServiceFacade.getGroupsTeacherService().save(groupsTeacher);
            BaseResultUtil.setSuccess(result, groups);
            return result;
        } catch (Exception e) {
            logger.error("评分组增加教师异常", e);
            return result;
        }
    }

    @ApiOperation("评分组增加项目")
    @RequestMapping(value = "/addProject", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult addProject(@RequestParam(value = "id", defaultValue = "0") int id,
                                 @RequestParam(value = "projectId", defaultValue = "0") int projectId,
                                 HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (id <= 0 || projectId <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        UserInfo self = Ums.getUser(request);
        try {
            Groups groups = Constant.ServiceFacade.getGroupsService().select(id);
            if (groups == null || self.getRole() != UserInfoService.USER_COLLEGE || groups.getProjectIds().contains(projectId + "")) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            if (groups.getProjectIds().length() <= 0 || ("0".equals(groups.getProjectIds()))) {
                groups.setProjectIds("" + projectId);
            } else {
                groups.setProjectIds("," + projectId);
            }
            Constant.ServiceFacade.getGroupsService().save(groups);
            BaseResultUtil.setSuccess(result, groups);
            return result;
        } catch (Exception e) {
            logger.error("评分组增加教师异常", e);
            return result;
        }
    }*/

    public static void mainx(String[] args) {
        String[] strs = {"a11", "a12", "b10", "b001", "b004", "A00006","009","10","100003", "A00001", "A00005", "A00002", "B11"};
        List list = Arrays.asList(strs);
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Pattern pattern = Pattern.compile("[0-9]*");
                if (pattern.matcher(o1).matches() && pattern.matcher(o2).matches()) {
                    if (o1.length() != o2.length()) {
                        return o1.length() - o2.length();
                    }
                    int i1 = Integer.valueOf(o1);
                    int i2 = Integer.valueOf(o2);
                    return i1 - i2;
                }
                if (o1.substring(0, 1).toLowerCase().toCharArray()[0] != o2.substring(0, 1).toLowerCase().toCharArray()[0]) {
                    return o1.substring(0, 1).toLowerCase().toCharArray()[0] - o2.substring(0, 1).toLowerCase().toCharArray()[0];
                } else if (o1.length() == o2.length()){
                    int i1 = Integer.valueOf(o1.substring(1,o1.length()));
                    int i2 = Integer.valueOf(o2.substring(1,o2.length()));
                    return i1 - i2;
                } else {
                    return o2.length() - o2.length();
                }
            }
        });
        for (int i=0; i<list.size(); i++) {
            System.out.println(list.get(i) + "  ");
        }
    }
}
