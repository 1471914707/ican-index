package com.ican.service;

import com.ican.domain.Task;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface TaskService {
    //基础方法
    int insert(Task task) throws icanServiceException;

    Task select(int id) throws icanServiceException;

    int update(Task task) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(Task task) throws icanServiceException;

    List<Task> list(String ids, int activityId, int ownerId, int executorId, int projectId, int status, String order, int page, int size) throws icanServiceException;

    int count(String ids, int activityId, int ownerId, int executorId, int projectId, int status) throws icanServiceException;

    int count(int executorId, String nowDay) throws icanServiceException;
}
