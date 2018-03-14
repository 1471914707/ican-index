package com.ican.service;

import com.ican.domain.Paper;
import com.ican.domain.Paper;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface PaperService {
    //基础方法
    public int insert(Paper paper) throws icanServiceException;

    public Paper select(int id) throws icanServiceException;

    public int update(Paper paper) throws icanServiceException;

    public int delete(int id) throws icanServiceException;

    public int save(Paper paper) throws icanServiceException;

    public List<Paper> list(String ids, int current, int schoolId, int collegeId, int departmentId,
            int clazzId, int teacherId, String name, String order, int page, int size) throws icanServiceException;

    public int count(String ids, int current, int schoolId, int collegeId, int departmentId,
                     int clazzId, int teacherId, String name) throws icanServiceException;
}
