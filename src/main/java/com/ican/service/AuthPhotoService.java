package com.ican.service;

import com.ican.exception.icanServiceException;
import com.ican.domain.AuthPhoto;

import java.util.List;

public interface AuthPhotoService {

    int TYPE_SUPER = 1;
    int TYPE_ADMIN = 2;
    int TYPE_SCHOOL = 3;
    int TYPE_COLLEGE = 4;
    int TYPE_TEACHER = 5;
    int TYPE_STUDENT = 6;

    int delete(int id) throws icanServiceException;

    AuthPhoto select(int id) throws icanServiceException;

    int insert(AuthPhoto authPhoto) throws icanServiceException;

    int update(AuthPhoto authPhoto) throws icanServiceException;

    int save(AuthPhoto authPhoto) throws icanServiceException;

    List<AuthPhoto> list(int userId, String order, int page, int size) throws icanServiceException;

    int count(int userId) throws icanServiceException;
}
