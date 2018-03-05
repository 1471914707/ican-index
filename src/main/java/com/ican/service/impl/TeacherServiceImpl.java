package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.exception.icanServiceException;
import com.ican.model.Teacher;
import com.ican.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Override
    public int insert(Teacher teacher) throws icanServiceException {
        return Constant.DaoFacade.getTeacherDao().insert(teacher);
    }

    @Override
    public Teacher select(int id) throws icanServiceException {
        return (Teacher) Constant.DaoFacade.getTeacherDao().select(id);
    }

    @Override
    public int update(Teacher teacher) throws icanServiceException {
        return Constant.DaoFacade.getTeacherDao().update(teacher);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getTeacherDao().delete(id);
    }

    @Override
    public int save(Teacher teacher) throws icanServiceException {
        if (teacher != null) {
            return -1;
        }
        if (teacher.getId() > 0) {
            update(teacher);
        } else {
            if (StringUtils.isEmpty(teacher.getJobId())) {
                teacher.setJobId("");
            }
            if (StringUtils.isEmpty(teacher.getDegreeName())) {
                teacher.setDegreeName("");
            }
            if (StringUtils.isEmpty(teacher.getEmail())) {
                teacher.setEmail("");
            }
            if (StringUtils.isEmpty(teacher.getPhone())) {
                teacher.setPhone("");
            }
            insert(teacher);
        }
        return teacher.getId();
    }

    @Override
    public List<Teacher> list(String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getTeacherDao().list(param);
    }

    @Override
    public int count() throws icanServiceException {
        Map param = new HashMap();
        return Constant.DaoFacade.getTeacherDao().count(param);
    }
}
