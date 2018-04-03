package com.ican.service;

import com.ican.exception.icanServiceException;
import com.ican.domain.DepartmentTeacher;

import java.util.List;

public interface DepartmentTeacherService {
    //基础方法
    int insert(DepartmentTeacher departmentTeacher) throws icanServiceException;

    DepartmentTeacher select(int id) throws icanServiceException;

    int update(DepartmentTeacher departmentTeacher) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(DepartmentTeacher departmentTeacher) throws icanServiceException;

    List<DepartmentTeacher> list(String departmentIds, int departmentId, int teacherId, String order, int page, int size) throws icanServiceException;

    int count(String departmentIds, int departmentId, int teacherId) throws icanServiceException;
}
