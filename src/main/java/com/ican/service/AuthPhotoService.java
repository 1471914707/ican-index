package com.ican.service;

import com.ican.exception.icanServiceException;
import com.ican.model.AuthPhoto;

import java.util.List;

public interface AuthPhotoService {

    int delete(int id) throws icanServiceException;

    AuthPhoto select(int id) throws icanServiceException;

    int insert(AuthPhoto authPhoto) throws icanServiceException;

    int update(AuthPhoto authPhoto) throws icanServiceException;

    int save(AuthPhoto authPhoto) throws icanServiceException;

    List<AuthPhoto> list(String order, int page, int size) throws icanServiceException;

    int count() throws icanServiceException;
}
