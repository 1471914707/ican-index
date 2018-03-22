package com.ican.util;

import com.ican.config.Constant;
import com.ican.domain.User;
import com.ican.domain.UserInfo;
import com.ican.exception.icanServiceException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Ums {

    public static UserInfo getUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (IcanUtil.COOKIE_NAME.equals(cookie.getName())) {
                return getUser(cookie);
            }
        }
        return null;
    }

    public static UserInfo getUser(Cookie cookie) {
        String id = new JedisAdapter().get(cookie.getValue());
        try {
            return Constant.ServiceFacade.getUserInfoService().select(Integer.valueOf(id));
        } catch (icanServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isAdmin(UserInfo userInfo) {
        if (userInfo.getRole() == 1 || userInfo.getRole() == 2) {
            return true;
        }
        return false;
    }

    public static boolean isSuperAdmin(UserInfo userInfo) {
        if (userInfo.getRole() == 1) {
            return true;
        }
        return false;
    }
}
