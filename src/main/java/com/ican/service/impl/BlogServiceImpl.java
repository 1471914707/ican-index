package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.domain.Blog;
import com.ican.exception.icanServiceException;
import com.ican.service.BlogService;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImpl implements BlogService {
    @Override
    public int insert(Blog blog) throws icanServiceException {
        return Constant.DaoFacade.getBlogDao().insert(blog);
    }

    @Override
    public Blog select(int id) throws icanServiceException {
        return (Blog) Constant.DaoFacade.getBlogDao().select(id);
    }

    @Override
    public int update(Blog blog) throws icanServiceException {
        return Constant.DaoFacade.getBlogDao().update(blog);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getBlogDao().delete(id);
    }

    @Override
    public int save(Blog blog) throws icanServiceException {
        if (blog == null) {
            return 0;
        }
        if (blog.getId() > 0) {
            update(blog);
        } else {
            if (StringUtils.isEmpty(blog.getContent())) {
                blog.setContent("");
            }
            if (StringUtils.isEmpty(blog.getImage())) {
                blog.setImage("[]");
            }
            insert(blog);
        }
        return blog.getId();
    }

    @Override
    public List<Blog> list(String ids, int userId, int schoolId, String order, int page, int size) throws icanServiceException {
        Map params = new HashMap();
        params.put("ids", ids);
        params.put("userId", userId);
        params.put("schoolId", schoolId);
        params.put("order", order);
        params.put("start", (page - 1) * size);
        params.put("size", size);
        return Constant.DaoFacade.getBlogDao().list(params);
    }

    @Override
    public int count(String ids, int userId, int schoolId) throws icanServiceException {
        Map params = new HashMap();
        params.put("ids", ids);
        params.put("schoolId", schoolId);
        params.put("userId", userId);
        return Constant.DaoFacade.getBlogDao().count(params);
    }
}
