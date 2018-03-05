package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.exception.icanServiceException;
import com.ican.model.College;
import com.ican.service.CollegeService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CollegeServiceImpl implements CollegeService {
    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getCollegeDao().delete(id);
    }

    @Override
    public College select(int id) throws icanServiceException {
        return (College) Constant.DaoFacade.getCollegeDao().select(id);
    }

    @Override
    public int insert(College college) throws icanServiceException {
        return Constant.DaoFacade.getCollegeDao().insert(college);
    }

    @Override
    public int update(College college) throws icanServiceException {
        return Constant.DaoFacade.getCollegeDao().update(college);
    }

    @Override
    public int save(College college) throws icanServiceException {
        if (college == null) {
            return -1;
        }
        if (college.getId() > 0) {
            if (StringUtils.isEmpty(college.getEmail())) {
                college.setEmail("");
            }
            if (StringUtils.isEmpty(college.getName())) {
                college.setName("");
            }
            if (StringUtils.isEmpty(college.getPhone())) {
                college.setPhone("");
            }
            insert(college);
        }
        return college.getId();
    }

    @Override
    public List<College> list(String order, int page, int size) throws icanServiceException {
        Map params = new HashMap();
        params.put("order", order);
        params.put("start", (page - 1) * size);
        params.put("size", size);
        return Constant.DaoFacade.getCollegeDao().list(params);
    }

    @Override
    public int count() throws icanServiceException {
        Map params = new HashMap();
        return Constant.DaoFacade.getCollegeDao().count(params);
    }
}
