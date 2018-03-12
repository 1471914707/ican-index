package com.ican.service;

import com.ican.exception.icanServiceException;
import com.ican.domain.DepartmentTeacher;

import java.util.List;

public interface DepartmentTeacherService {
    //基础方法
    public int insert(DepartmentTeacher departmentTeacher) throws icanServiceException;

    public DepartmentTeacher select(int id) throws icanServiceException;

    public int update(DepartmentTeacher departmentTeacher) throws icanServiceException;

    public int delete(int id) throws icanServiceException;

    public int save(DepartmentTeacher departmentTeacher) throws icanServiceException;

    public List<DepartmentTeacher> list(int departmentId, int teacherId, String order, int page, int size) throws icanServiceException;

    public int count(int departmentId, int teacherId) throws icanServiceException;
}
