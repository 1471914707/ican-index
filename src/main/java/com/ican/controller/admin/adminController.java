package com.ican.controller.admin;

import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.to.AdminTO;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.IcanUtil;
import com.ican.util.Ums;
import com.ican.vo.AdminVO;
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

@Api("管理员控制器")
@RequestMapping("/admin")
@Controller
public class adminController {

    private final static Logger logger = LoggerFactory.getLogger(adminController.class);


    @RequestMapping(value = {"/", "/adminList"}, method = RequestMethod.GET)
    public String index() {
        return "/admin/super/admin_index";
    }

    @ApiOperation("个人资料的修改")
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult superAdminList(AdminTO adminTO,
                                      HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo userInfo = Ums.getUser(request);
        if (userInfo == null) {
            result.setMsg("请先登录");
            return result;
        }
        try {
            if (adminTO.getId() > 0) {
                Admin oldAdmin = Constant.ServiceFacade.getAdminService().select(adminTO.getId());
                if (oldAdmin == null) {
                    return result;
                }
            }
            int id = Constant.ServiceFacade.getUserInfoService().save(adminTO.toUserInfo());
            adminTO.setId(id);
            Constant.ServiceFacade.getAdminService().save(adminTO.toAdmin());
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("保存单个管理员信息异常", e);
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
            List<UserInfo> userInfoList = Constant.ServiceFacade.getUserInfoService().list(ids, 0, null, page, size);
            Map map = new HashMap();
            for (UserInfo userInfo : userInfoList) {
                map.put(userInfo.getId(), userInfo);
            }
            List<SchoolVO> schoolVOList = new ArrayList<>();
            for (School school : schoolList) {
                SchoolVO schoolVO = new SchoolVO(school, (UserInfo) map.get(school.getId()));
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
    @RequestMapping(value = "/schoolAuth", method = RequestMethod.GET)
    public BaseResult schoolAuth(@RequestParam(value = "schoolId", required = false, defaultValue = "0") int schoolId,
                                 HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            if (schoolId < 0) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            UserInfo userInfo = Constant.ServiceFacade.getUserInfoService().select(schoolId);
            if (userInfo == null || userInfo.getRole() != IcanUtil.USER_SCHOOL) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            userInfo.setStatus(IcanUtil.USER_STATUS_VALID);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("获取学校列表异常", e);
            return result;
        }
    }


    @ApiOperation("获取学校申议列表")
    @ResponseBody
    @RequestMapping(value = "/schoolAppealList", method = RequestMethod.GET)
    public BaseResult schoolAppealList(@RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "size", defaultValue = "20") int size,
                                        HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            List<SchoolAppeal> schoolAppealList = Constant.ServiceFacade.getSchoolAppealService().list(null, "id desc", page, size);
            int total = Constant.ServiceFacade.getSchoolAppealService().count(null);
            Map param = new HashMap();
            param.put("list", schoolAppealList);
            param.put("total", total);
            BaseResultUtil.setSuccess(result, param);
            return result;
        } catch (Exception e) {
            logger.error("获取学校申议列表异常", e);
            return result;
        }
    }

    @ApiOperation("修改学校申议状态")
    @ResponseBody
    @RequestMapping(value = "/schoolAppealStatus", method = RequestMethod.GET)
    public BaseResult schoolAppealStatus(@RequestParam(value = "id") int id,
                                          @RequestParam(value = "status") int status,
                                          HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            if (id < 0 || status < 0) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            SchoolAppeal schoolAppeal = Constant.ServiceFacade.getSchoolAppealService().select(id);
            if (schoolAppeal == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            schoolAppeal.setStatus(status);
            Constant.ServiceFacade.getSchoolAppealService().save(schoolAppeal);
            BaseResultUtil.setSuccess(result, schoolAppeal);
            return result;
        } catch (Exception e) {
            logger.error("修改学校申议状态异常", e);
            return result;
        }
    }

}
