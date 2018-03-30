package com.ican.controller.school;

import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.service.AuthPhotoService;
import com.ican.to.SchoolTO;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.vo.SchoolVO;
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

@Api("学校的操作")
@Controller
@RequestMapping("/school")
public class SchoolController {
    private final static Logger logger = LoggerFactory.getLogger(SchoolController.class);

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String index() {
        return "/school/activity/list";
    }

    @ApiOperation("获取学校信息")
    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public BaseResult save(@RequestParam("id") int id,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (id <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            UserInfo userInfo = Constant.ServiceFacade.getUserInfoService().select(id);
            if (userInfo == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            School school = Constant.ServiceFacade.getSchoolService().select(id);
            SchoolVO schoolVO = new SchoolVO(school, userInfo);
            BaseResultUtil.setSuccess(result, schoolVO);
            return result;
        } catch (Exception e) {
            logger.error("获取学校信息异常", e);
            return result;
        }
    }

    @ApiOperation("保存学校")
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult save(SchoolTO schoolTO,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (schoolTO == null) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            if (schoolTO.getId() > 0) {
                UserInfo userInfo = Constant.ServiceFacade.getUserInfoService().select(schoolTO.getId());
                //不能保存不存在的
                if (userInfo == null) {
                    result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                    return result;
                }
            }
            int id = Constant.ServiceFacade.getSchoolService().save(schoolTO);
            //保存认证照片
            if (!StringUtils.isEmpty(schoolTO.getAuth1())) {
                AuthPhoto authPhoto = new AuthPhoto();
                authPhoto.setUrl(schoolTO.getAuth1());
                authPhoto.setUserId(id);
                authPhoto.setType(AuthPhotoService.TYPE_SCHOOL);
                Constant.ServiceFacade.getAuthPhotoService().save(authPhoto);
            }
            if (!StringUtils.isEmpty(schoolTO.getAuth2())) {
                AuthPhoto authPhoto = new AuthPhoto();
                authPhoto.setUrl(schoolTO.getAuth2());
                authPhoto.setUserId(id);
                authPhoto.setType(AuthPhotoService.TYPE_SCHOOL);
                Constant.ServiceFacade.getAuthPhotoService().save(authPhoto);
            }
            BaseResultUtil.setSuccess(result, id);
            return result;
        } catch (Exception e) {
            logger.error("保存学校异常", e);
            return result;
        }
    }
}
