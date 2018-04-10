package com.ican.controller;

import com.ican.config.Constant;
import com.ican.domain.UserInfo;
import com.ican.service.UserInfoService;
import com.ican.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Api("登陆Controller")
@Controller
public class LoginController {
    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);


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
        try {
            List<UserInfo> userInfoList = new ArrayList<>();
            if (role == 1 || role == 2) {
                List<UserInfo> superList = Constant.ServiceFacade.getUserInfoService().listByAccount(account, UserInfoService.USER_SUPER);
                List<UserInfo> adminList = Constant.ServiceFacade.getUserInfoService().listByAccount(account,UserInfoService.USER_ADMIN);
                userInfoList = superList.size() > 0 ? superList : adminList;
                role = superList.size() > 0 ? UserInfoService.USER_SUPER : UserInfoService.USER_ADMIN;
            } else {
               userInfoList = Constant.ServiceFacade.getUserInfoService().listByAccount(account,role);
            }
            if (userInfoList == null || userInfoList.size() == 0) {
                result.setMsg("手机或邮箱不存在");
                return result;
            }
            UserInfo userInfo = userInfoList.get(0);
            password = IcanUtil.MD5(password + userInfo.getSalt());
            if (userInfo.getPassword().equals(password)) {
                //登录成功
                /*UserInfo userInfo = new UserInfo();
                userInfo.setId(100002);*/
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
                    case UserInfoService.USER_SUPER:
                        result.setData("/admin/super");
                        break;
                    case UserInfoService.USER_ADMIN:
                        result.setData("/admin");
                        break;
                    case UserInfoService.USER_SCHOOL:
                        result.setData("/school");
                        break;
                    case UserInfoService.USER_COLLEGE:
                        result.setData("/college");
                        break;
                    case UserInfoService.USER_TEACHER:
                        result.setData("/teacher");
                        break;
                    case UserInfoService.USER_STUDENT:
                        result.setData("/student");
                        break;
                    default:
                        result.setData("/student");
                        break;
                }
                result.setCode(0);
                return result;
            } else {
                result.setMsg("密码错误");
                return result;
            }
        } catch (Exception e) {
            logger.error("登陆接口异常", e);
            return result;
        }
    }
/*
    private List<UserInfo> getUserInfoByAccount(String account, int role) {
        try {
            int id = 0;
            switch (role) {
                case UserInfoService.USER_SUPER:
                case UserInfoService.USER_ADMIN:
                    List<Admin> adminList = Constant.ServiceFacade.getAdminService().list(null, null, account, null, 1, 1);
                    if (adminList != null && adminList.size() > 0) {
                        id = adminList.get(0).getId();
                    }
                    break;
                break;
                case 3:
                    *//*List<Admin> schoolList = Constant.ServiceFacade.getAdminService().list(null, null, account, null, 1, 1);
                    if (adminList != null && adminList.size() > 0) {
                        id = adminList.get(0).getId();
                    }*//*
                    break;
                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:

                    break;
                default:
                    return null;
            }
        } catch (Exception e) {
            logger.error("登录查找异常");
            return null;
        }

    }*/

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        UserInfo userInfo = Ums.getUser(request);
        if (userInfo == null) {
            return "/login";
        }
        int role = userInfo.getRole();
        JedisAdapter jedisAdapter = new JedisAdapter();
        String cookie = jedisAdapter.get(userInfo.getId() + "");
        jedisAdapter.delete(cookie);
        jedisAdapter.delete(userInfo.getId() + "");

        request.setAttribute("role", role);
        return "/login";
    }

    @RequestMapping(value = "/loginFail", method = RequestMethod.GET)
    public String loginFail(@RequestParam("role") int role,
                            HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("role",role);
        request.setAttribute("loginMsg","登录信息过期，请重新登录");
        return "/login";
    }
}
