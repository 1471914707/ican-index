package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.exception.icanServiceException;
import com.ican.domain.Clazz;
import com.ican.service.ClazzService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClazzServiceImpl implements ClazzService{
    @Override
    public int insert(Clazz clazz) throws icanServiceException {
        return Constant.DaoFacade.getClazzDao().insert(clazz);
    }

    @Override
    public Clazz select(int id) throws icanServiceException {
        return (Clazz) Constant.DaoFacade.getClazzDao().select(id);
    }

    @Override
    public int update(Clazz clazz) throws icanServiceException {
        return Constant.DaoFacade.getClazzDao().update(clazz);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getClazzDao().delete(id);
    }

    @Override
    public int save(Clazz clazz) throws icanServiceException {
        if (clazz == null) {
            return -1;
        }
        if (clazz.getId() > 0) {
            update(clazz);
        } else {
            if (StringUtils.isEmpty(clazz.getName())) {
                clazz.setName("");
            }
            insert(clazz);
        }
        return clazz.getId();
    }

    @Override
    public List<Clazz> list(String ids, int schoolId, int collegeId, int departmentId, int current, String order, int page, int size) throws icanServiceException {
        Map params = new HashMap();
        params.put("ids", ids);
        params.put("schoolId", schoolId);
        params.put("collegeId", collegeId);
        params.put("departmentId", departmentId);
        params.put("current", current);
        params.put("order", order);
        params.put("start", (page - 1) * size);
        params.put("size", size);
        return Constant.DaoFacade.getClazzDao().list(params);
    }

    @Override
    public int count(String ids, int schoolId, int collegeId, int departmentId, int current) throws icanServiceException {
        Map params = new HashMap();
        params.put("ids", ids);
        params.put("schoolId", schoolId);
        params.put("collegeId", collegeId);
        params.put("departmentId", departmentId);
        params.put("current", current);
        return Constant.DaoFacade.getClazzDao().count(params);
    }

}
