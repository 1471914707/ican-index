package com.ican.service;

import com.ican.exception.icanServiceException;
import com.ican.domain.Department;

import java.util.List;

public interface DepartmentService {
    //基础方法
    int insert(Department department) throws icanServiceException;

    Department select(int id) throws icanServiceException;

    int update(Department department) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(Department department) throws icanServiceException;

    List<Department> list(int schoolId, int collegeId, String order, int page, int size) throws icanServiceException;

    int count(int schoolId, int collegeId) throws icanServiceException;
}
