package com.ican.service;

import com.ican.exception.icanServiceException;
import com.ican.domain.College;
import com.ican.to.CollegeTO;

import java.util.List;

public interface CollegeService {

    int delete(int id) throws icanServiceException;

    College select(int id) throws icanServiceException;

    int insert(College college) throws icanServiceException;

    int update(College college) throws icanServiceException;

    int save(College college) throws icanServiceException;

    int save(CollegeTO collegeTO) throws icanServiceException;

    List<College> list(String ids, int schoolId, String order, int page, int size) throws icanServiceException;

    int count(String ids, int schoolId) throws icanServiceException;
}
