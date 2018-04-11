package com.ican.interceptor;

import com.ican.config.Constant;
import com.ican.domain.School;
import com.ican.domain.UserInfo;
import com.ican.service.UserInfoService;
import com.ican.util.IcanUtil;
import com.ican.util.JedisAdapter;
import com.ican.vo.SchoolVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SchoolInterceptor extends HandlerInterceptorAdapter {
    private final Logger logger = LoggerFactory.getLogger(SchoolInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            Cookie[] cookieList = request.getCookies();
            for (int i=0; i<cookieList.length; i++) {
                if (IcanUtil.COOKIE_NAME.equals(cookieList[i].getName())) {
                    JedisAdapter jedisAdapter = new JedisAdapter();
                    String userId = jedisAdapter.get(cookieList[i].getValue());
                    if (userId != null && userId.length() > 0) {
                        int id = Integer.valueOf(userId);
                        UserInfo userInfo = Constant.ServiceFacade.getUserInfoService().select(id);
                        if (userInfo == null || userInfo.getRole() != UserInfoService.USER_SCHOOL) {
                          //  response.sendRedirect(request.getContextPath()+"/login?role=" + UserInfoService.USER_SCHOOL);
                            return true;
                        }
                        School school = Constant.ServiceFacade.getSchoolService().select(id);
                        request.setAttribute("school", new SchoolVO(school, userInfo));
                        return true;
                    }
                }
            }
            response.sendRedirect(request.getContextPath()+"/login?role=" + UserInfoService.USER_SCHOOL);
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
