package com.ican.service;

import com.ican.exception.icanServiceException;
import com.ican.model.Clazz;

import java.util.List;

public interface ClazzService {
    //基础方法
    public Clazz select(int id) throws icanServiceException;

    public int delete(int id) throws icanServiceException;

    public int insert(Clazz clazz) throws icanServiceException;

    public int update(Clazz clazz) throws icanServiceException;

    public int save(Clazz clazz) throws icanServiceException;

    public List<Clazz> list(int schoolId, int collegeId, int departmentId, int current, String order, int page, int size) throws icanServiceException;

    public int count(int schoolId, int collegeId, int departmentId, int current) throws icanServiceException;
}
