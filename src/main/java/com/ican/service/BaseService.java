package com.ican.service;

import com.ican.exception.icanServiceException;

public interface BaseService {

    void insert(Object object) throws icanServiceException;

    void delete(int id) throws icanServiceException;

    Object select(int id) throws icanServiceException;

    void update(Object object) throws icanServiceException;
}
