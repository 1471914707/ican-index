package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.domain.School;
import com.ican.exception.icanServiceException;
import com.ican.domain.Teacher;
import com.ican.service.TeacherService;
import com.ican.to.TeacherTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
            insert(teacher);
        }
        return teacher.getId();
    }

    @Transactional
    @Override
    public int save(TeacherTO teacherTO) throws icanServiceException {
        int id = Constant.ServiceFacade.getUserInfoService().save(teacherTO.toUserInfo());
        if (teacherTO.getId() <= 0) {
            Teacher teacher = teacherTO.toTeacher();
            teacher.setId(id);
            if (StringUtils.isEmpty(teacher.getJobId())) {
                teacher.setJobId("");
            }
            if (StringUtils.isEmpty(teacher.getDegreeName())) {
                teacher.setDegreeName("");
            }
            Constant.ServiceFacade.getTeacherService().insert(teacher);
        } else {
            Constant.ServiceFacade.getTeacherService().update(teacherTO.toTeacher());
        }
        return id;
    }

    @Override
    public List<Teacher> list(String ids, String jobId, int degree,
                              String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("jobId", jobId);
        param.put("degree", degree);
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getTeacherDao().list(param);
    }

    @Override
    public int count(String ids, String jobId, int degree) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("jobId", jobId);
        param.put("degree", degree);
        return Constant.DaoFacade.getTeacherDao().count(param);
    }
}
