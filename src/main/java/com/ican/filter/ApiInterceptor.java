package com.ican.filter;


import com.ican.util.*;
import freemarker.template.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@WebFilter(filterName = "apiFilter", urlPatterns = "/api/*")
public class ApiInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            Cookie[] cookieList = request.getCookies();
            for (int i=0; i<cookieList.length; i++) {
                if (IcanUtil.COOKIE_NAME.equals(cookieList[i].getName())) {
                    System.out.println(cookieList[i].getValue());
                    JedisAdapter jedisAdapter = new JedisAdapter();
                    String userId = jedisAdapter.get(cookieList[i].getValue());
                    if (userId != null && userId.length() > 0) {
                        //通过验证
                        return true;
                    }
                }
            }
            response.sendRedirect(request.getContextPath()+"/loginFail?role=5");
        } catch (IOException iox) {
            logger.error("iox" + iox);
            return false;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

}
