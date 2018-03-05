package com.ican.filter;


import com.ican.util.*;
import freemarker.template.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class ApiFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(ApiFilter.class);

    private FilterConfig filterConfig;

    private List<String> passList = new ArrayList<>(Arrays.asList(new String[]{"/api/data/*", "/data/*", "/swagger*", "/webjars/*", "/v2/api*","/act/api/exhibitor/*","/act/api/sponsor/*"}));

    private boolean canPass(String path) {
        for (String url : passList) {
            if (path.contains(url)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) {
        try {
            HttpServletRequest request = (HttpServletRequest) req;
            Cookie[] cookieList = ((HttpServletRequest) req).getCookies();
            for (int i=0; i<cookieList.length; i++) {
                if (IcanUtil.COOKIE_NAME.equals(cookieList[i].getName())) {
                    JedisAdapter jedisAdapter = new JedisAdapter();
                    String userId = jedisAdapter.get(cookieList[i].getValue());
                    if (userId != null && userId.length() > 0) {
                        //通过验证
                        filterChain.doFilter(request, resp);
                    }
                }
            }
            req.setAttribute("loginMsg","登录信息过期，请重新登录");
            req.getRequestDispatcher("/login").forward(request,resp);
        } catch (ServletException sx) {
            logger.error("sx" + sx);
        } catch (IOException iox) {
            logger.error("iox" + iox);
        }
        return;
    }

    @Override
    public void destroy() {
    }
}
