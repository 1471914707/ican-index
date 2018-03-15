package com.ican.controller.api;

import com.ican.domain.UserInfo;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.IcanUtil;
import com.ican.util.JedisAdapter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api("登陆Controller")
//@RestController
@RequestMapping("/api")
public class LoginIController {

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
        if (("18813960106").equals(account) && ("123456").equals(password)) {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(10000001);
            //登录成功就添加cookie
            String cookieValue = IcanUtil.getNewCookieValue();
            Cookie cookie = new Cookie(IcanUtil.COOKIE_NAME, cookieValue);
            cookie.setPath("/");
            cookie.setMaxAge(3600 * 24 * 30);
            response.addCookie(cookie);
            //设置到redis中
            JedisAdapter jedisAdapter = new JedisAdapter();
            //先清除旧的，避免无用数据越来越多
            String oldCookieValue = jedisAdapter.get(String.valueOf(userInfo.getId()));
            if (oldCookieValue != null && oldCookieValue.length() > 0) {
                jedisAdapter.delete(oldCookieValue);
                jedisAdapter.delete(String.valueOf(userInfo.getId()));
            }
            //再添加新的
            jedisAdapter.set(cookieValue, String.valueOf(userInfo.getId()),3600 * 24 * 30);
            jedisAdapter.set(String.valueOf(userInfo.getId()),cookieValue,3600 * 24 * 30);
            BaseResultUtil.setSuccess(result, null);
            return result;
        }
        return result;
    }
}
