package com.ican.service;

import com.ican.domain.PaperStudent;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface PaperStudentService {
    //基础方法
    public int insert(PaperStudent paperStudent) throws icanServiceException;

    public PaperStudent select(int id) throws icanServiceException;

    public int update(PaperStudent paperStudent) throws icanServiceException;

    public int delete(int id) throws icanServiceException;

    public int save(PaperStudent paperStudent) throws icanServiceException;

    public List<PaperStudent> list(String ids, int activityId, int current, int schoolId, int collegeId, int departmentId,
                                   int clazzId, int teacherId, int studentId, int paperId, String order, int page, int size) throws icanServiceException;

    public int count(String ids, int activityId, int current, int schoolId, int collegeId, int departmentId,
                     int clazzId, int teacherId, int studentId, int paperId) throws icanServiceException;
}
