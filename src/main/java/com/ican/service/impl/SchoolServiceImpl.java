package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.exception.icanServiceException;
import com.ican.domain.School;
import com.ican.service.SchoolService;
import com.ican.to.SchoolTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Override
    public int insert(School school) throws icanServiceException {
        if (school == null) {
            return -1;
        }
        return Constant.DaoFacade.getSchoolDao().insert(school);
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
            if (StringUtils.isEmpty(school.getAddress())) {
                school.setAddress("");
            }
            if (StringUtils.isEmpty(school.getUrl())) {
                school.setUrl("");
            }
            insert(school);
        }
        return school.getId();
    }

    @Transactional
    @Override
    public int save(SchoolTO schoolTO) throws icanServiceException {
        int id = Constant.ServiceFacade.getUserInfoService().save(schoolTO.toUserInfo());
        if (schoolTO.getId() <= 0) {
            schoolTO.setId(id);
            Constant.ServiceFacade.getSchoolService().insert(schoolTO.toSchool());
        } else {
            Constant.ServiceFacade.getSchoolService().update(schoolTO.toSchool());
        }
        return id;
    }

    @Override
    public List<School> list(String ids, int country, int province, int city, String name, String phone, String email, String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("country", country);
        param.put("province", province);
        param.put("city", city);
        param.put("name", name);
        param.put("phone", phone);
        param.put("email", email);
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getSchoolDao().list(param);
    }

    @Override
    public int count(String ids, int country, int province, int city, String name, String phone, String email) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("country", country);
        param.put("province", province);
        param.put("city", city);
        param.put("name", name);
        param.put("phone", phone);
        param.put("email", email);
        return Constant.DaoFacade.getSchoolDao().count(param);
    }
}
