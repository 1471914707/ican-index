package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.exception.icanServiceException;
import com.ican.model.Department;
import com.ican.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Override
    public int insert(Department department) throws icanServiceException {
        return Constant.DaoFacade.getDepartmentDao().insert(department);
    }

    @Override
    public Department select(int id) throws icanServiceException {
        return (Department) Constant.DaoFacade.getDepartmentDao().select(id);
    }

    @Override
    public int update(Department department) throws icanServiceException {
        return Constant.DaoFacade.getDepartmentDao().update(department);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getDepartmentDao().delete(id);
    }

    @Override
    public int save(Department department) throws icanServiceException {
        if (department == null) {
            return -1;
        }
        if (department.getId() > 0) {
            update(department);
        } else {
            if (StringUtils.isEmpty(department.getName())) {
                department.setName("");
            }
            insert(department);
        }
        return department.getId();
    }

    @Override
    public List<Department> list(int schoolId, int collegeId, String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("schoolId", schoolId);
        param.put("collegeId", collegeId);
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getDepartmentDao().list(param);
    }

    @Override
    public int count(int schoolId, int collegeId) throws icanServiceException {
        Map param = new HashMap();
        param.put("schoolId", schoolId);
        param.put("collegeId", collegeId);
        return Constant.DaoFacade.getDepartmentDao().count(param);
    }
}
