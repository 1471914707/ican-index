package com.ican.service;

import com.ican.domain.Paper;
import com.ican.domain.Paper;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface PaperService {
    //基础方法
    int insert(Paper paper) throws icanServiceException;

    Paper select(int id) throws icanServiceException;

    int update(Paper paper) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(Paper paper) throws icanServiceException;

    List<Paper> list(String ids, int activityId,int current, int schoolId, int collegeId, int departmentId,
                     int clazzId, int teacherId, String title, String order,int page, int size) throws icanServiceException;

    int count(String ids, int activityId,int current, int schoolId, int collegeId, int departmentId,
              int clazzId, int teacherId, String title) throws icanServiceException;
}
