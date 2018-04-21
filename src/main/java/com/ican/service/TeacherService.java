package com.ican.service;

import com.ican.exception.icanServiceException;
import com.ican.domain.Teacher;
import com.ican.to.SchoolTO;
import com.ican.to.TeacherTO;

import java.util.List;

public interface TeacherService {
    //基础方法
    int insert(Teacher teacher) throws icanServiceException;

    Teacher select(int id) throws icanServiceException;

    int update(Teacher teacher) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(Teacher teacher) throws icanServiceException;

    int save(TeacherTO teacherTO) throws icanServiceException;

    List<Teacher> list(String ids, int schoolId, String jobId, int degree,
                       String order, int page, int size) throws icanServiceException;

    int count(String ids, int schoolId, String jobId, int degree) throws icanServiceException;
}
