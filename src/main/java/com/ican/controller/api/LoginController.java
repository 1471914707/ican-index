package com.ican.controller.api;

import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api("登陆Controller")
@RestController
@RequestMapping("/api")
public class LoginController {

    @ApiOperation("登陆接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResult login(@RequestParam("account") String account,
                              @RequestParam("password") String password,
                              @RequestParam("role") int role,
                              HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password) || (role < 0 || role > 6)) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        BaseResultUtil.setSuccess(result, null);
        return result;
    }
}
