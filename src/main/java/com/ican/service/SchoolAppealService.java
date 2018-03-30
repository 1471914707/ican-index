package com.ican.service;

import com.ican.domain.Follow;
import com.ican.domain.SchoolAppeal;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface SchoolAppealService {
    //基础方法
    int insert(SchoolAppeal schoolAppeal) throws icanServiceException;

    SchoolAppeal select(int id) throws icanServiceException;

    int update(SchoolAppeal schoolAppeal) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(SchoolAppeal schoolAppeal) throws icanServiceException;

    List<SchoolAppeal> list(String ids, String order, int page, int size) throws icanServiceException;

    int count(String ids) throws icanServiceException;
}
