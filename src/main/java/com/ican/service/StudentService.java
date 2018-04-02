package com.ican.service;

import com.ican.exception.icanServiceException;
import com.ican.domain.Student;

import java.util.List;

public interface StudentService {
    //基础方法
    Student select(int id) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int insert(Student student) throws icanServiceException;

    int update(Student student) throws icanServiceException;

    int save(Student student) throws icanServiceException;

    List<Student> list(String ids, int schoolId, int collegeId, int departmentId,
                       int clazzId, int teacherId, int current, String jobId,
                       String order, int page, int size) throws icanServiceException;

    int count(String ids, int schoolId, int collegeId, int departmentId,
              int clazzId, int teacherId, int current, String jobId) throws icanServiceException;
}
