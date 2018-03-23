package com.ican.controller;

import com.ican.config.Constant;
import com.ican.domain.SchoolAppeal;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api("学校申议通道")
@RequestMapping("/schoolAppeal")
@Controller
public class SchoolAppealController {
    private final static Logger logger = LoggerFactory.getLogger(SchoolAppealController.class);

    //挑转页面
    @RequestMapping(value = "")
    public String schoolAppeal() {
        return "/ican_index";
    }

    @ApiOperation("保存申议信息")
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult save(SchoolAppeal schoolAppeal,
                            HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (schoolAppeal == null) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            //只能新增加
            schoolAppeal.setId(0);
            Constant.ServiceFacade.getSchoolAppealService().save(schoolAppeal);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("保存申议信息异常", e);
            return result;
        }
    }
}
