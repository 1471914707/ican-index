package com.ican.service;

import com.ican.domain.Activity;
import com.ican.domain.Follow;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface ActivityService {
    //基础方法
    int insert(Activity activity) throws icanServiceException;

    Activity select(int id) throws icanServiceException;

    int update(Activity activity) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(Activity activity) throws icanServiceException;

    List<Activity> list(String ids, int userId, String order, int page, int size) throws icanServiceException;

    int count(String ids, int userId) throws icanServiceException;

    List<Activity> list(String userIds, String order, int page, int size) throws icanServiceException;

    int count(String userIds) throws icanServiceException;
}
