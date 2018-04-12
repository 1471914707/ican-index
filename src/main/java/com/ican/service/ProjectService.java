package com.ican.service;

import com.ican.domain.Project;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface ProjectService {
    //基础方法
    int insert(Project project) throws icanServiceException;

    Project select(int id) throws icanServiceException;

    int update(Project project) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(Project project) throws icanServiceException;

    List<Project> list(String ids, int activityId,int current, int schoolId, int collegeId, int departmentId,
                       int clazzId, int teacherId, int studentId, String title, int status, String order, int page, int size) throws icanServiceException;

    int count(String ids, int activityId, int current, int schoolId, int collegeId, int departmentId,
              int clazzId, int teacherId, int studentId, String title, int status) throws icanServiceException;

    List<Project> list(String majorIds, int activityId, int collegeId, int teacherId, int status, String order, int page, int size) throws icanServiceException;

    int count(String majorIds, int activityId, int collegeId, int teacherId, int status) throws icanServiceException;
}
