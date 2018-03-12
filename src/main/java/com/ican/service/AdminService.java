package com.ican.service;

import com.ican.domain.Admin;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface AdminService {
    //基础方法
    public int insert(Admin admin) throws icanServiceException;

    public Admin select(int id) throws icanServiceException;

    public int update(Admin admin) throws icanServiceException;

    public int delete(int id) throws icanServiceException;

    public int save(Admin admin) throws icanServiceException;

    public List<Admin> list(String ids, String phone, String email, String order, int page, int size) throws icanServiceException;

    public int count(String ids, String phone, String email) throws icanServiceException;

}
