package com.ican.service;

import com.ican.domain.UserInfo;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface UserInfoService {

    //角色状态定义
    public static int USER_STATUS_INIT = 0;
    public static int USER_STATUS_VALID = 1;
    public static int USER_STATUS_INVALID = 2;

    //角色定义
    public static int ADMIN_SUPER = 1;
    public static int ADMIN_SYSTEM = 2;
    public static int USER_SCHOOL = 3;
    public static int USER_COLLEGE = 4;
    public static int USER_TEACHER = 5;
    public static int USER_STUDENT = 6;

    //角色数量定义
    public static int USER_MIN = 1;
    public static int USER_MAX = 6;

    //基础方法
    public int insert(UserInfo userInfo) throws icanServiceException;

    public UserInfo select(int id) throws icanServiceException;

    public int update(UserInfo userInfo) throws icanServiceException;

    public int delete(int id) throws icanServiceException;

    public int save(UserInfo userInfo) throws icanServiceException;

    public List<UserInfo> list(String ids, int role, String order, int page, int size) throws icanServiceException;

    public int count(String ids, int role) throws icanServiceException;
}
