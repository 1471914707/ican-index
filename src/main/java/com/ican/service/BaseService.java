package com.ican.service;

import com.ican.exception.icanServiceException;

import java.util.List;

public interface BaseService {

    int delete(int id) throws icanServiceException;

    Object select(int id) throws icanServiceException;

    int insert(Object object) throws icanServiceException;

    int update(Object object) throws icanServiceException;

    int save(Object object) throws icanServiceException;

    List<Object> list(String order, int page, int size) throws icanServiceException;

    int count() throws icanServiceException;
}
