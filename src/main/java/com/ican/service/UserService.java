package com.ican.service;


import com.ican.model.User;
import com.ican.exception.icanServiceException;

public interface UserService {

    void insert(User user) throws icanServiceException;

    void delete(int id) throws icanServiceException;

    User select(int id) throws icanServiceException;

    void update(User user) throws icanServiceException;
}
