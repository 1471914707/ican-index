package com.ican.service;

import com.ican.exception.icanServiceException;
import com.ican.domain.Clazz;

import java.util.List;

public interface ClazzService {
    //基础方法
    Clazz select(int id) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int insert(Clazz clazz) throws icanServiceException;

    int update(Clazz clazz) throws icanServiceException;

    int save(Clazz clazz) throws icanServiceException;

    List<Clazz> list(String ids, int schoolId, int collegeId, int departmentId, int current, String order, int page, int size) throws icanServiceException;

    int count(String ids, int schoolId, int collegeId, int departmentId, int current) throws icanServiceException;
}
