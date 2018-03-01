package com.ican.service;

import com.ican.exception.icanServiceException;
import com.ican.model.School;

import java.util.List;

public interface SchoolService {
    //基础方法
    public int insert(School school) throws icanServiceException;

    public School select(int id) throws icanServiceException;

    public int update(School school) throws icanServiceException;

    public int delete(int id) throws icanServiceException;

    public int save(School school) throws icanServiceException;

    public List<School> list(String order, int page, int size) throws icanServiceException;

    public int count() throws icanServiceException;
}
