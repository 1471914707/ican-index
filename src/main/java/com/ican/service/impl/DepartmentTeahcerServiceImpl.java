package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.exception.icanServiceException;
import com.ican.domain.DepartmentTeacher;
import com.ican.service.DepartmentTeacherService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentTeahcerServiceImpl implements DepartmentTeacherService {
    @Override
    public int insert(DepartmentTeacher departmentTeacher) throws icanServiceException {
        return Constant.DaoFacade.getDepartmentTeacherDao().insert(departmentTeacher);
    }

    @Override
    public DepartmentTeacher select(int id) throws icanServiceException {
        return (DepartmentTeacher) Constant.DaoFacade.getDepartmentTeacherDao().select(id);
    }

    @Override
    public int update(DepartmentTeacher departmentTeacher) throws icanServiceException {
        return Constant.DaoFacade.getDepartmentTeacherDao().update(departmentTeacher);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getDepartmentTeacherDao().delete(id);
    }

    @Override
    public int save(DepartmentTeacher departmentTeacher) throws icanServiceException {
        if (departmentTeacher == null) {
            return -1;
        }
        if (departmentTeacher.getId() > 0) {
            update(departmentTeacher);
        } else {
            insert(departmentTeacher);
        }
        return departmentTeacher.getId();
    }

    @Override
    public List<DepartmentTeacher> list(int departmentId, int teacherId, String order, int page, int size) throws icanServiceException {
        Map params = new HashMap();
        params.put("departmentId", departmentId);
        params.put("teacherId", teacherId);
        params.put("order", order);
        params.put("start", (page - 1) * size);
        params.put("size", size);
        return Constant.DaoFacade.getDepartmentTeacherDao().list(params);
    }

    @Override
    public int count(int departmentId, int teacherId) throws icanServiceException {
        Map params = new HashMap();
        params.put("departmentId", departmentId);
        params.put("teacherId", teacherId);
        return Constant.DaoFacade.getDepartmentTeacherDao().count(params);
    }
}
