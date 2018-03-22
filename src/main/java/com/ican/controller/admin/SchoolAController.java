package com.ican.controller.admin;

import com.ican.config.Constant;
import com.ican.config.ServiceFacade;
import com.ican.domain.Follow;
import com.ican.domain.School;
import com.ican.domain.UserInfo;
import com.ican.service.FollowService;
import com.ican.service.UserInfoService;
import com.ican.to.SchoolTO;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.vo.SchoolVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Api("管理员下学校的操作")
@Controller
@RequestMapping("/admin/school")
public class SchoolAController {

    private final static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(@RequestParam(value = "schoolId") String schoolId,
                          HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("schoolId", schoolId);
        return "/admin/school/detail";
    }

    @ApiOperation("获取学校详情")
    @ResponseBody
    @RequestMapping(value = "/detailJson", method = RequestMethod.GET)
    public BaseResult detailJson(@RequestParam(value = "schoolId") int schoolId,
                                  HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (schoolId <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            UserInfo userInfo = Constant.ServiceFacade.getUserInfoService().select(schoolId);
            if (userInfo == null || userInfo.getRole() != UserInfoService.USER_SCHOOL) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            School school = Constant.ServiceFacade.getSchoolService().select(schoolId);
            SchoolVO schoolVO = new SchoolVO(school, userInfo);
            BaseResultUtil.setSuccess(result, schoolVO);
            return result;
        } catch (Exception e) {
            logger.error("获取学校详情异常",e);
            return result;
        }
    }

    @ApiOperation("学校列表获取")
    @ResponseBody
    @RequestMapping(value = "/schoolList", method = RequestMethod.GET)
    public BaseResult schoolList(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "country", required = false, defaultValue = "0") int country,
                                  @RequestParam(value = "province", required = false, defaultValue = "0") int province,
                                  @RequestParam(value = "city", required = false, defaultValue = "0") int city,
                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "size", defaultValue = "20") int size,
                                  HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            List<School> schoolList = Constant.ServiceFacade.getSchoolService().list(null, country, province, city, name, null, null, "id desc", page, size);
            Set<String> schoolSet = new HashSet<>();
            for (School school : schoolList) {
                schoolSet.add("" + school.getId());
            }
            String ids = String.join(",", schoolSet);
            List<UserInfo> userInfoList = Constant.ServiceFacade.getUserInfoService().list(ids, 0, null, 1, size);
            Map map = new HashMap();
            for (UserInfo userInfo : userInfoList) {
                map.put(userInfo.getId(), userInfo);
            }
            List<SchoolVO> schoolVOList = new ArrayList<>();
            for (School school : schoolList) {
                SchoolVO schoolVO = new SchoolVO(school, (UserInfo) map.get(school.getId()));
                //最后跟进人
                List<Follow> followList = Constant.ServiceFacade.getFollowService().list(null, 0, school.getId(),
                        FollowService.FOLLOW_TYPE_SCHOOL, "id desc", 1, 1);
                if (followList != null && followList.size() > 0) {
                    schoolVO.setFollow(followList.get(0));
                }
                schoolVOList.add(schoolVO);
            }
            int total = Constant.ServiceFacade.getSchoolService().count(null, country, province, city, name, null, null);
            Map param = new HashMap();
            param.put("list", schoolVOList);
            param.put("total", total);
            BaseResultUtil.setSuccess(result, param);
            return result;
        } catch (Exception e) {
            logger.error("获取学校列表异常", e);
            return result;
        }
    }

    @ApiOperation("认证学校通过")
    @ResponseBody
    @RequestMapping(value = "/schoolAuth", method = RequestMethod.POST)
    public BaseResult schoolAuth(@RequestParam(value = "schoolId", required = false, defaultValue = "0") int schoolId,
                                  @RequestParam(value = "status") int status,
                                  HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            if (schoolId <= 0) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            UserInfo userInfo = Constant.ServiceFacade.getUserInfoService().select(schoolId);
            if (userInfo == null || userInfo.getRole() != UserInfoService.USER_SCHOOL) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            userInfo.setStatus(status);
            Constant.ServiceFacade.getUserInfoService().update(userInfo);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("改变学校状态异常", e);
            return result;
        }
    }

    @ApiOperation("保存学校")
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult schoolAuth(SchoolTO schoolTO,
                                  HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            Constant.ServiceFacade.getUserInfoService().save(schoolTO.toUserInfo());
            int id = Constant.ServiceFacade.getSchoolService().save(schoolTO.toSchool());
            BaseResultUtil.setSuccess(result, id);
            return result;
        } catch (Exception e) {
            logger.error("保存学校异常", e);
            return result;
        }
    }
}
