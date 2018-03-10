package com.ican.service;

import com.ican.exception.icanServiceException;
import com.ican.model.Student;

import java.util.List;

public interface StudentService {
    //基础方法
    public Student select(int id) throws icanServiceException;

    public int delete(int id) throws icanServiceException;

    public int insert(Student student) throws icanServiceException;

    public int update(Student student) throws icanServiceException;

    public int save(Student student) throws icanServiceException;

    public List<Student> list(int userId, int schoolId, int collegeId, int departmentId,
                              int clazzId, int teacherId, int current, String jobId,
                              String order, int page, int size) throws icanServiceException;

    public int count(int userId, int schoolId, int collegeId, int departmentId,
                     int clazzId, int teacherId, int current, String jobId) throws icanServiceException;
}
