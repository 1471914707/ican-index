package com.ican.service;

import com.ican.exception.icanServiceException;
import com.ican.model.College;

import java.util.List;

public interface CollegeService {

    int delete(int id) throws icanServiceException;

    College select(int id) throws icanServiceException;

    int insert(College college) throws icanServiceException;

    int update(College college) throws icanServiceException;

    int save(College college) throws icanServiceException;

    List<College> list(String order, int page, int size) throws icanServiceException;

    int count() throws icanServiceException;
}
