package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.domain.SchoolAppeal;
import com.ican.exception.icanServiceException;
import com.ican.service.SchoolAppealService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SchoolAppealServiceImpl implements SchoolAppealService{
    @Override
    public int insert(SchoolAppeal schoolAppeal) throws icanServiceException {
        return Constant.DaoFacade.getSchoolAppealDao().insert(schoolAppeal);
    }

    @Override
    public SchoolAppeal select(int id) throws icanServiceException {
        return (SchoolAppeal) Constant.DaoFacade.getSchoolAppealDao().select(id);
    }

    @Override
    public int update(SchoolAppeal schoolAppeal) throws icanServiceException {
        return Constant.DaoFacade.getSchoolAppealDao().update(schoolAppeal);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getSchoolAppealDao().delete(id);
    }

    @Override
    public int save(SchoolAppeal schoolAppeal) throws icanServiceException {
        if (schoolAppeal == null) {
            return 0;
        }
        if (schoolAppeal.getId() > 0) {
            update(schoolAppeal);
        } else {
            if (StringUtils.isEmpty(schoolAppeal.getEmail())) {
                schoolAppeal.setEmail("");
            }
            if (StringUtils.isEmpty(schoolAppeal.getName())) {
                schoolAppeal.setName("");
            }
            if (StringUtils.isEmpty(schoolAppeal.getContent())) {
                schoolAppeal.setContent("");
            }
            if (StringUtils.isEmpty(schoolAppeal.getPhone())) {
                schoolAppeal.setPhone("");
            }
            if (StringUtils.isEmpty(schoolAppeal.getSchoolName())) {
                schoolAppeal.setSchoolName("");
            }
            insert(schoolAppeal);
        }
        return schoolAppeal.getId();
    }

    @Override
    public List<SchoolAppeal> list(String ids, String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getSchoolAppealDao().list(param);
    }

    @Override
    public int count(String ids) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        return Constant.DaoFacade.getSchoolAppealDao().count(param);
    }
}
