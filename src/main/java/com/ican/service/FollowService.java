package com.ican.service;

import com.ican.domain.Follow;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface FollowService {

    //跟进类型定义
    int FOLLOW_TYPE_SCHOOL = 1;
    int FOLLOW_TYPE_APPEAL = 2;
    int FOLLOW_TYPE_PROJECT = 3;

    //基础方法
    int insert(Follow follow) throws icanServiceException;

    Follow select(int id) throws icanServiceException;

    int update(Follow follow) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(Follow follow) throws icanServiceException;

    List<Follow> list(String ids, int followUserId, int followId, int followType, String order, int page, int size) throws icanServiceException;

    int count(String ids, int followUserId, int followId, int followType) throws icanServiceException;

}
