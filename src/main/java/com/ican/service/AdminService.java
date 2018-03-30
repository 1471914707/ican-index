package com.ican.service;

import com.ican.domain.Admin;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface AdminService {
    //基础方法
    int insert(Admin admin) throws icanServiceException;

    Admin select(int id) throws icanServiceException;

    int update(Admin admin) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(Admin admin) throws icanServiceException;

    List<Admin> list(String ids, String phone, String email, String order, int page, int size) throws icanServiceException;

    int count(String ids, String phone, String email) throws icanServiceException;

}
