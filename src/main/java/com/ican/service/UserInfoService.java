package com.ican.service;

import com.ican.model.UserInfo;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface UserInfoService {
    //基础方法
    public int insert(UserInfo userInfo) throws icanServiceException;

    public UserInfo select(int id) throws icanServiceException;

    public int update(UserInfo userInfo) throws icanServiceException;

    public int delete(int id) throws icanServiceException;

    public int save(UserInfo userInfo) throws icanServiceException;

    public List<UserInfo> list(String order, int page, int size) throws icanServiceException;

    public int count() throws icanServiceException;
}