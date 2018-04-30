package com.ican.service;

import com.ican.domain.Counts;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface CountsService {
    int TYPE_COMPLETE = 1;
    //基础方法
    int insert(Counts counts) throws icanServiceException;

    Counts select(int id) throws icanServiceException;

    int update(Counts counts) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(Counts counts) throws icanServiceException;

    List<Counts> list(String ids, int activityId, int type, String order, int page, int size) throws icanServiceException;

    int count(String ids, int activityId, int type) throws icanServiceException;
}
