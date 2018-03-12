package com.ican.controller.admin;

import com.ican.config.Constant;
import com.ican.domain.Admin;
import com.ican.domain.UserInfo;
import com.ican.to.AdminTO;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.vo.AdminVO;
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
import java.util.*;

@Api("管理员管理")
@Controller
@RequestMapping("/admin/super")
public class superAdminController {

    private final static Logger logger = LoggerFactory.getLogger(superAdminController.class);

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index() {
        return "/admin/super/admin_list";
    }


    @ApiOperation("获取管理员列表Json")
    @ResponseBody
    @RequestMapping(value = "/adminList",method = RequestMethod.GET)
    public BaseResult adminList(@RequestParam(value = "page",defaultValue = "1") int page,
                                @RequestParam(value = "size",defaultValue = "20") int size,
                                HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            List<UserInfo> userInfoList = Constant.ServiceFacade.getUserInfoService().list(null,2, "id desc", page, size);
            Set<String> userInfoSet = new HashSet<>();
            for (UserInfo userInfo : userInfoList) {
                userInfoSet.add("" + userInfo.getId());
            }
            String ids = String.join(",", userInfoSet);
            List<Admin> adminList = new ArrayList<>();
            adminList = Constant.ServiceFacade.getAdminService().list(ids, null,null,"id desc", page, size);
            Map map = new HashMap();
            for (Admin admin : adminList) {
                map.put(admin.getId(), admin);
            }
            List<AdminVO> adminVOList = new ArrayList<>();
            for (UserInfo userInfo : userInfoList) {
                AdminVO adminVO = new AdminVO((Admin) map.get(userInfo.getId()), userInfo);
                adminVOList.add(adminVO);
            }
            int total = Constant.ServiceFacade.getUserInfoService().count(null,2);
            Map data = new HashMap();
            data.put("list", adminVOList);
            data.put("total", total);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取普通管理员列表异常", e);
            return result;
        }
    }

    @ApiOperation("获取超级管理员列表Json")
    @ResponseBody
    @RequestMapping(value = "/superAdminList",method = RequestMethod.GET)
    public BaseResult superAdminList(@RequestParam(value = "page",defaultValue = "1") int page,
                                @RequestParam(value = "size",defaultValue = "20") int size,
                                HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            List<UserInfo> userInfoList = Constant.ServiceFacade.getUserInfoService().list(null,1, "id desc", page, size);
            Set<String> userInfoSet = new HashSet<>();
            for (UserInfo userInfo : userInfoList) {
                userInfoSet.add("" + userInfo.getId());
            }
            String ids = String.join(",", userInfoSet);
            List<Admin> adminList = new ArrayList<>();
            adminList = Constant.ServiceFacade.getAdminService().list(ids, null,null,"id desc", page, size);
            Map map = new HashMap();
            for (Admin admin : adminList) {
                map.put(admin.getId(), admin);
            }
            List<AdminVO> adminVOList = new ArrayList<>();
            for (UserInfo userInfo : userInfoList) {
                AdminVO adminVO = new AdminVO((Admin) map.get(userInfo.getId()), userInfo);
                adminVOList.add(adminVO);
            }
            int total = Constant.ServiceFacade.getUserInfoService().count(null,2);
            Map data = new HashMap();
            data.put("list", adminVOList);
            data.put("total", total);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取普通管理员列表异常", e);
            return result;
        }
    }

    @ApiOperation("获取单个管理员信息Json")
    @ResponseBody
    @RequestMapping(value = "/adminInfo",method = RequestMethod.GET)
    public BaseResult superAdminList(@RequestParam(value = "id") int id,
                                     HttpServletRequest request, HttpServletResponse response){
        BaseResult result = BaseResultUtil.initResult();
        if (id <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            UserInfo userInfo = Constant.ServiceFacade.getUserInfoService().select(id);
            Admin admin = Constant.ServiceFacade.getAdminService().select(id);
            BaseResultUtil.setSuccess(result, new AdminVO(admin, userInfo));
            return result;
        } catch (Exception e) {
            logger.error("获取单个管理员信息异常", e);
            return result;
        }
    }

    @ApiOperation("保存单个管理员信息")
    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public BaseResult superAdminList(AdminTO adminTO,
                                     HttpServletRequest request, HttpServletResponse response){
        BaseResult result = BaseResultUtil.initResult();
        if (adminTO == null) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
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
}
