package com.ican.service;

import com.ican.domain.Major;
import com.ican.domain.Message;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface MajorService {
    //基础方法
    int insert(Major major) throws icanServiceException;

    Major select(int id) throws icanServiceException;

    int update(Major major) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(Major major) throws icanServiceException;

    List<Major> list(String ids, int schoolId, int collegeId, int departmentId, String order, int page, int size) throws icanServiceException;

    int count(String ids, int schoolId, int collegeId, int departmentId) throws icanServiceException;

}
