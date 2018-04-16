package com.ican.service;


import com.ican.domain.Follow;
import com.ican.domain.Groups;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface GroupsService {

    //基础方法
    int insert(Groups groups) throws icanServiceException;

    Groups select(int id) throws icanServiceException;

    int update(Groups groups) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(Groups groups) throws icanServiceException;

    List<Groups> list(String ids, int activityId, int userId, String projectIds, int type, String order, int page, int size) throws icanServiceException;

    int count(String ids, int activityId, int userId, String projectIds, int type) throws icanServiceException;

}
