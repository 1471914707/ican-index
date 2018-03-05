package com.ican.service;

import com.ican.exception.icanServiceException;
import com.ican.model.City;

import java.util.List;

public interface CityService {
    //基础方法
    public int insert(City city) throws icanServiceException;

    public City select(int id) throws icanServiceException;

    public int update(City city) throws icanServiceException;

    public int delete(int id) throws icanServiceException;

    public int save(City city) throws icanServiceException;

    public List<City> list(int parentId, int type, String order, int page, int size) throws icanServiceException;

    public int count(int parentId, int type) throws icanServiceException;
}
