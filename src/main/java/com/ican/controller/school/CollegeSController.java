package com.ican.controller.school;

import com.ican.config.Constant;
import com.ican.domain.College;
import com.ican.domain.User;
import com.ican.domain.UserInfo;
import com.ican.service.UserInfoService;
import com.ican.to.CollegeTO;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
import com.ican.vo.CollegeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.bcel.Const;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.logging.Logger;

@Api("学校操作学院")
@Controller
@RequestMapping("/school/college")
public class CollegeSController {
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(CollegeSController.class);

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        return "/school/college/list";
    }

    @ApiOperation("获取二级学院列表Json")
    @RequestMapping(value = "/listJson", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listJson(@RequestParam(value = "page",defaultValue = "1") int page,
                               @RequestParam(value = "size",defaultValue = "20") int size,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo userInfo = Ums.getUser(request);
        try {
            List<College> collegeList = Constant.ServiceFacade.getCollegeService().list(userInfo.getId(), "id desc", page, size);
            int total = Constant.ServiceFacade.getCollegeService().count(userInfo.getId());
            Set<String> collegeIds = new HashSet<>();
            for (College college : collegeList) {
                collegeIds.add(college.getId() + "");
            }
            String ids = String.join(",", collegeIds);
            List<UserInfo> userInfoList = Constant.ServiceFacade.getUserInfoService().list(ids, null, null, UserInfoService.USER_COLLEGE, null, 1, total);
            Map userInfoMap = new HashMap();
            for (UserInfo userInfo1 : userInfoList) {
                userInfoMap.put(userInfo1.getId(), userInfo1);
            }
            List<CollegeVO> collegeVOList = new ArrayList<>();
            for (College college : collegeList) {
                UserInfo user = (UserInfo) userInfoMap.get(college.getId());
                CollegeVO collegeVO = new CollegeVO(college, user);
                collegeVOList.add(collegeVO);
            }

            Map data = new HashMap();
            data.put("list", collegeVOList);
            data.put("total", total);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取二级学院列表Json异常", e);
            return result;
        }
    }

    @ApiOperation("获取二级学院信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult info(@RequestParam(value = "id") String id,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo userInfo = Ums.getUser(request);
        int collegeId = Integer.valueOf(id);
        if (collegeId <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            College college = Constant.ServiceFacade.getCollegeService().select(collegeId);
            //不可查看他人学院
            if (college == null || (college != null && college.getSchoolId() != userInfo.getId())) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            UserInfo collegeUserInfo = Constant.ServiceFacade.getUserInfoService().select(collegeId);
            CollegeVO collegeVO = new CollegeVO(college, collegeUserInfo);
            BaseResultUtil.setSuccess(result, collegeVO);
            return result;
        } catch (Exception e) {
            logger.error("获取二级学院列表Json异常", e);
            return result;
        }
    }

    @ApiOperation("保存二级学院")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult save(CollegeTO collegeTO,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (collegeTO == null) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        UserInfo school = Ums.getUser(request);
        if (collegeTO.getSchoolId() > 0) {
            if (collegeTO.getSchoolId() != school.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
        }
        collegeTO.setSchoolId(school.getId());
        try {
            if (collegeTO.getId() > 0) {
                UserInfo userInfo = Constant.ServiceFacade.getUserInfoService().select(collegeTO.getId());
                //不能保存不存在的
                if (userInfo == null) {
                    result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                    return result;
                }
            }
            //防止保存重复手机号
            if (collegeTO.getId() <= 0 && !StringUtils.isEmpty(collegeTO.getPhone())) {
                List<UserInfo> userInfoList = Constant.ServiceFacade.getUserInfoService().list(null, collegeTO.getPhone(), null, UserInfoService.USER_COLLEGE, null, 1, 10);
                if (userInfoList != null && userInfoList.size() > 0) {
                    result.setMsg("该手机已被注册");
                    return result;
                }
            }
            if (collegeTO.getId() <= 0 && !StringUtils.isEmpty(collegeTO.getEmail())) {
                List<UserInfo> userInfoList = Constant.ServiceFacade.getUserInfoService().list(null, null, collegeTO.getEmail(), UserInfoService.USER_COLLEGE, null, 1, 10);
                if (userInfoList != null && userInfoList.size() > 0) {
                    result.setMsg("该邮箱已被注册");
                    return result;
                }
            }
            int id = Constant.ServiceFacade.getCollegeService().save(collegeTO);
            BaseResultUtil.setSuccess(result, id);
            return result;
        } catch (Exception e) {
            logger.error("保存二级学院异常", e);
            return result;
        }
    }

    @ApiOperation("删除二级学院")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult delete(@RequestParam(value = "id") String id,
                             HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo userInfo = Ums.getUser(request);
        int collegeId = Integer.valueOf(id);
        if (collegeId <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            College college = Constant.ServiceFacade.getCollegeService().select(collegeId);
            //不可查看他人学院
            if (college == null || (college != null && college.getSchoolId() != userInfo.getId())) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Constant.ServiceFacade.getCollegeService().delete(collegeId);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("删除二级学院异常", e);
            return result;
        }
    }
}
