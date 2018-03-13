package com.ican.service;

import com.ican.domain.Activity;
import com.ican.domain.Follow;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface ActivityService {
    //基础方法
    public int insert(Activity activity) throws icanServiceException;

    public Activity select(int id) throws icanServiceException;

    public int update(Activity activity) throws icanServiceException;

    public int delete(int id) throws icanServiceException;

    public int save(Activity activity) throws icanServiceException;

    public List<Activity> list(String ids, int userId, String order, int page, int size) throws icanServiceException;

    public int count(String ids, int userId) throws icanServiceException;
}
