package com.ican.service;

import com.ican.domain.PaperStudent;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface PaperStudentService {
    //基础方法
    int insert(PaperStudent paperStudent) throws icanServiceException;

    PaperStudent select(int id) throws icanServiceException;

    int update(PaperStudent paperStudent) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(PaperStudent paperStudent) throws icanServiceException;

    List<PaperStudent> list(String ids, int activityId, int current, int schoolId, int collegeId, int departmentId,
                                   int clazzId, int teacherId, int studentId, int paperId, String order, int page, int size) throws icanServiceException;

    int count(String ids, int activityId, int current, int schoolId, int collegeId, int departmentId,
                     int clazzId, int teacherId, int studentId, int paperId) throws icanServiceException;
}
