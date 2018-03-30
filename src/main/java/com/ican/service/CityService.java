package com.ican.service;

import com.ican.exception.icanServiceException;
import com.ican.domain.City;

import java.util.List;

public interface CityService {
    //基础方法
    int insert(City city) throws icanServiceException;

    City select(int id) throws icanServiceException;

    int update(City city) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(City city) throws icanServiceException;

    List<City> list(int parentId, int type, String order, int page, int size) throws icanServiceException;

    int count(int parentId, int type) throws icanServiceException;
}
