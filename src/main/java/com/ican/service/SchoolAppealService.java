package com.ican.service;

import com.ican.domain.Follow;
import com.ican.domain.SchoolAppeal;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface SchoolAppealService {
    //基础方法
    public int insert(SchoolAppeal schoolAppeal) throws icanServiceException;

    public SchoolAppeal select(int id) throws icanServiceException;

    public int update(SchoolAppeal schoolAppeal) throws icanServiceException;

    public int delete(int id) throws icanServiceException;

    public int save(SchoolAppeal schoolAppeal) throws icanServiceException;

    public List<SchoolAppeal> list(String ids, String order, int page, int size) throws icanServiceException;

    public int count(String ids) throws icanServiceException;
}
