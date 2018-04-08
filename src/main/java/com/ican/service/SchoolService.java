package com.ican.service;

import com.ican.domain.School;
import com.ican.exception.icanServiceException;
import com.ican.to.SchoolTO;
import com.ican.vo.SchoolVO;

import java.util.List;

public interface SchoolService {
    //基础方法
    int insert(School school) throws icanServiceException;

    School select(int id) throws icanServiceException;

    SchoolVO selectVO(int id) throws icanServiceException;

    int update(School school) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(School school) throws icanServiceException;

    int save(SchoolTO schoolTO) throws icanServiceException;

    List<School> list(String ids, int country, int province, int city, String name, String order, int page, int size) throws icanServiceException;

    int count(String ids, int country, int province, int city, String name) throws icanServiceException;
}
