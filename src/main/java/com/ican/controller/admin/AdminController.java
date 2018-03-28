package com.ican.controller.admin;

import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.service.UserInfoService;
import com.ican.service.UserService;
import com.ican.to.AdminTO;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.IcanUtil;
import com.ican.util.Ums;
import com.ican.vo.SchoolVO;
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

@Api("管理员控制器")
@RequestMapping("/admin")
@Controller
public class AdminController {

    private final static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String index() {
        return "/admin/school/list";
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
