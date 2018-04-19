package com.ican.interceptor;

import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.service.UserInfoService;
import com.ican.util.IcanUtil;
import com.ican.util.JedisAdapter;
import com.ican.vo.TeacherVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StudentInterceptor extends HandlerInterceptorAdapter {
    private final Logger logger = LoggerFactory.getLogger(TeacherInterceptor.class);

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
                        UserInfo student = Constant.ServiceFacade.getUserInfoService().select(id);
                        if (student == null || student.getRole() != UserInfoService.USER_STUDENT) {
                            response.sendRedirect(request.getContextPath()+"/login?role=" + UserInfoService.USER_STUDENT);
                            return false;
                        }
                        Student student1 = Constant.ServiceFacade.getStudentService().select(student.getId());
                        UserInfo school = Constant.ServiceFacade.getUserInfoService().select(student1.getSchoolId());
                        School school1 = Constant.ServiceFacade.getSchoolService().select(student1.getSchoolId());
                        UserInfo college = Constant.ServiceFacade.getUserInfoService().select(student1.getCollegeId());
                        student.setSalt(school1.getBanner());
                        student.setEmail(college.getHeadshot());
                        student.setPhone(school.getHeadshot());
                        student.setStatus(school.getId());
                        student.setRole(college.getId());
                        request.setAttribute("student", student);
                        return true;
                    }
                }
            }
            response.sendRedirect(request.getContextPath()+"/login?role=" + UserInfoService.USER_STUDENT);
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
