package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.exception.icanServiceException;
import com.ican.model.School;
import com.ican.service.SchoolService;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchoolServiceImpl implements SchoolService {

    @Override
    public int insert(School school) throws icanServiceException {
        if (school == null) {
            return -1;
        }
        return Constant.DaoFacade.getAdminDao().insert(school);
    }

    @Override
    public School select(int id) throws icanServiceException {
        return (School) Constant.DaoFacade.getSchoolDao().select(id);
    }

    @Override
    public int update(School school) throws icanServiceException {
        if (school == null) {
            return -1;
        }
        return Constant.DaoFacade.getSchoolDao().update(school);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getSchoolDao().delete(id);
    }

    @Override
    public int save(School school) throws icanServiceException {
        if (school == null) {
            return -1;
        }
        if (school.getId() > 0) {
            update(school);
        } else {
            if (StringUtils.isEmpty(school.getName())) {
                school.setName("");
            }
            if (StringUtils.isEmpty(school.getPhone())) {
                school.setPhone("");
            }
            if (StringUtils.isEmpty(school.getEmail())) {
                school.setEmail("");
            }
            insert(school);
        }
        return school.getId();
    }

    @Override
    public List<School> list(String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getSchoolDao().list(param);
    }

    @Override
    public int count() throws icanServiceException {
        Map param = new HashMap();
        return Constant.DaoFacade.getSchoolDao().count(param);
    }
}
