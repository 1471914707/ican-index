package com.ican.service;

import com.ican.domain.Blog;
import com.ican.domain.City;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface BlogService {
    //基础方法
    int insert(Blog blog) throws icanServiceException;

    Blog select(int id) throws icanServiceException;

    int update(Blog blog) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(Blog blog) throws icanServiceException;

    List<Blog> list(String ids, int userId, int schoolId, String order, int page, int size) throws icanServiceException;

    int count(String ids, int userId, int schoolId) throws icanServiceException;
}
