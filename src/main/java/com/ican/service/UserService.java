package com.ican.service;


import com.ican.exception.icanServiceException;
import com.ican.model.User;

public interface UserService {

    void insert(User user) throws icanServiceException;

    void delete(int id) throws icanServiceException;

    User select(int id) throws icanServiceException;

    void update(User user) throws icanServiceException;
}
