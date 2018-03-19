package com.ican.service;

import com.ican.domain.Follow;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface FollowService {

    //跟进类型定义
    public static int FOLLOW_TYPE_SCHOOL = 1;
    public static int FOLLOW_TYPE_APPEAL = 2;

    //基础方法
    public int insert(Follow follow) throws icanServiceException;

    public Follow select(int id) throws icanServiceException;

    public int update(Follow follow) throws icanServiceException;

    public int delete(int id) throws icanServiceException;

    public int save(Follow follow) throws icanServiceException;

    public List<Follow> list(String ids, int followUserId, int followId, int followType, String order, int page, int size) throws icanServiceException;

    public int count(String ids, int followUserId, int followId, int followType) throws icanServiceException;

}
