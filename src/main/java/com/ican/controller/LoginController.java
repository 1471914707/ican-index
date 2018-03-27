package com.ican.controller;

import com.ican.domain.UserInfo;
import com.ican.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api("登陆Controller")
@Controller
public class LoginController {

    @Value("${ican.url.admin}")
    private String adminUrl;

    @Value("${ican.url.school}")
    private String schoolUrl;

    @Value("${ican.url.college}")
    private String collegeUrl;

    @Value("${ican.url.teacher}")
    private String teacherUrl;

    @Value("${ican.url.student}")
    private String studentUrl;

    @Value("${ican.url.bk}")
    private String bkUrl;

    @ApiOperation("登陆接口")
    @ResponseBody
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
            userInfo.setId(100002);
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
            jedisAdapter.set(cookieValue, String.valueOf(userInfo.getId()), 3600 * 24 * 30);
            jedisAdapter.set(String.valueOf(userInfo.getId()), cookieValue, 3600 * 24 * 30);

            switch (role) {
                case 1:
                case 2:
                    result.setData("/admin");
                    break;
                case 3:
                    result.setData("/school");
                    break;
                case 4:
                    result.setData("/college");
                    break;
                case 5:
                    result.setData("/teacher");
                    break;
                case 6:
                    result.setData("/student");
                    break;
            }
            result.setCode(0);
            return result;
        }
        return result;
    }

    @RequestMapping(value = "/loginFail", method = RequestMethod.GET)
    public String loginFail(@RequestParam("role") int role,
                            HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("role",role);
        request.setAttribute("loginMsg","登录信息过期，请重新登录");
        return "/login";
    }
}
