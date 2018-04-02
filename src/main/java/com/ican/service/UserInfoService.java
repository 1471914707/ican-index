package com.ican.service;

import com.ican.domain.UserInfo;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface UserInfoService {

    //性别定义
    int SEX_MAN = 1;
    int SEX_WOMAN = 2;

    //角色状态定义
    int USER_STATUS_INIT = 0;
    int USER_STATUS_VALID = 1;
    int USER_STATUS_INVALID = 2;

    //角色定义
    int USER_SUPER = 1;
    int USER_ADMIN = 2;
    int USER_SCHOOL = 3;
    int USER_COLLEGE = 4;
    int USER_TEACHER = 5;
    int USER_STUDENT = 6;

    //角色数量定义
    int USER_MIN = 1;
    int USER_MAX = 6;

    //基础方法
    int insert(UserInfo userInfo) throws icanServiceException;

    UserInfo select(int id) throws icanServiceException;

    int update(UserInfo userInfo) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(UserInfo userInfo) throws icanServiceException;

    List<UserInfo> list(String ids, String phone, String email, int role, String order, int page, int size) throws icanServiceException;

    int count(String ids, String phone, String eamil, int role) throws icanServiceException;

    List<UserInfo> listByAccount(String account, int role) throws icanServiceException;
}
