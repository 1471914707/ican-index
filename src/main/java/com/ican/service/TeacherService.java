package com.ican.service;

import com.ican.exception.icanServiceException;
import com.ican.domain.Teacher;

import java.util.List;

public interface TeacherService {
    //基础方法
    public int insert(Teacher teacher) throws icanServiceException;

    public Teacher select(int id) throws icanServiceException;

    public int update(Teacher teacher) throws icanServiceException;

    public int delete(int id) throws icanServiceException;

    public int save(Teacher teacher) throws icanServiceException;

    public List<Teacher> list(String ids, String phone, String email, String jobId, int degree,
                              String order, int page, int size) throws icanServiceException;

    public int count(String ids, String phone, String email, String jobId, int degree) throws icanServiceException;
}
