package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.domain.City;
import com.ican.service.CityService;
import com.ican.exception.icanServiceException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CityServiceImpl implements CityService {

    public static final int TYPE_COUNTRY = 1;
    public static final int TYPE_PROVINCE = 2;
    public static final int TYPE_CITY = 3;

    @Override
    public int insert(City city) throws icanServiceException{
        if (city == null) {
            return 0;
        }
        return Constant.DaoFacade.getCityDao().insert(city);
    }

    @Override
    public City select(int id) throws icanServiceException {
        return (City) Constant.DaoFacade.getCityDao().select(id);
    }

    @Override
    public int update(City city) throws icanServiceException {
        if (city == null) {
            return 0;
        }
        return Constant.DaoFacade.getCityDao().update(city);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getCityDao().delete(id);
    }

    @Override
    public int save(City city) throws icanServiceException {
        if (city.getId() > 0) {
            update(city);
        } else {
            if (StringUtils.isEmpty(city.getName())) {
                city.setName("");
            }
            if (StringUtils.isEmpty(city.getNameEn())) {
                city.setNameEn("");
            }
            if (StringUtils.isEmpty(city.getFirst())) {
                city.setFirst("");
            }
            Constant.DaoFacade.getCityDao().insert(city);
        }
        return city.getId();
    }

    @Override
    public List<City> list(int parentId, int type, String order, int page, int size) throws icanServiceException {
        Map params = new HashMap();
        params.put("parentId", parentId);
        params.put("type", type);
        params.put("order", order);
        params.put("start", (page - 1) * size);
        params.put("size", size);
        return Constant.DaoFacade.getCityDao().list(params);
    }

    @Override
    public int count(int parentId, int type) throws icanServiceException {
        Map params = new HashMap();
        params.put("parentId", parentId);
        params.put("type", type);
        return Constant.DaoFacade.getCityDao().count(params);
    }
}
