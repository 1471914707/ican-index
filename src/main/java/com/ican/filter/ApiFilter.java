package com.ican.filter;


import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import freemarker.template.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
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
        /*for (String url : passList) {
            if (StringUtil.isMatcher(url, path)) {
                return true;
            }
        }*/
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
            /*String path = RequestUtil.getPath(request);
            if (canPass(path)) {
                filterChain.doFilter(request, resp);
                return;
            }
            Userinfo user = Ums.getUser(request);
            if (user == null) {
                BaseResult result = BaseResultUtil.initResult();
                result.setCode(BaseResultUtil.CODE_NOLOGIN);
                result.setMsg(BaseResultUtil.MSG_NOLOGIN);

                HttpServletResponse response = (HttpServletResponse) resp;
                response.setHeader("Pragma", "No-cache");
                response.setHeader("Cache-Control", "no-cache");
                response.setContentType("application/json; charset=utf-8");
                response.setDateHeader("Expires", 0);
                response.getWriter().write(JsonUtil.objToStr(result));
                return;
            }
            logger.debug("user->" + user.getUserid());*/
            filterChain.doFilter(request, resp);
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
