package com.ican.service;

import com.ican.exception.icanServiceException;
import com.ican.model.Department;

import java.util.List;

public interface DepartmentService {
    //基础方法
    public int insert(Department department) throws icanServiceException;

    public Department select(int id) throws icanServiceException;

    public int update(Department department) throws icanServiceException;

    public int delete(int id) throws icanServiceException;

    public int save(Department department) throws icanServiceException;

    public List<Department> list(String order, int page, int size) throws icanServiceException;

    public int count() throws icanServiceException;
}
