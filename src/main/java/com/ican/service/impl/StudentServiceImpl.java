package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.exception.icanServiceException;
import com.ican.domain.Student;
import com.ican.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    @Override
    public Student select(int id) throws icanServiceException {
        return (Student) Constant.DaoFacade.getStudentDao().select(id);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getStudentDao().delete(id);
    }

    @Override
    public int insert(Student student) throws icanServiceException {
        return Constant.DaoFacade.getStudentDao().insert(student);
    }

    @Override
    public int update(Student student) throws icanServiceException {
        return Constant.DaoFacade.getStudentDao().update(student);
    }

    @Override
    public int save(Student student) throws icanServiceException {
        if (student == null) {
            return -1;
        }
        if (student.getId() > 0) {
            update(student);
        } else {
            if (StringUtils.isEmpty(student.getJobId())){
                student.setJobId("");
            }
            insert(student);
        }
        return student.getId();
    }

    @Override
    public List<Student> list(String ids, int schoolId, int collegeId, int departmentId,
                              int clazzId, int teacherId, int current, String jobId,
                              String order, int page, int size) throws icanServiceException {
        Map params = new HashMap();
        params.put("ids", ids);
        params.put("schoolId", schoolId);
        params.put("collegeId", collegeId);
        params.put("departmentId", departmentId);
        params.put("clazzId", clazzId);
        params.put("teacherId", teacherId);
        params.put("current", current);
        params.put("jobId", jobId);
        params.put("order", order);
        params.put("start", (page - 1) * size);
        params.put("size", size);
        return Constant.DaoFacade.getStudentDao().list(params);
    }

    @Override
    public int count(String ids, int schoolId, int collegeId, int departmentId,
                     int clazzId, int teacherId, int current, String jobId) throws icanServiceException {
        Map params = new HashMap();
        params.put("ids", ids);
        params.put("schoolId", schoolId);
        params.put("collegeId", collegeId);
        params.put("departmentId", departmentId);
        params.put("clazzId", clazzId);
        params.put("teacherId", teacherId);
        params.put("current", current);
        params.put("jobId", jobId);
        return Constant.DaoFacade.getStudentDao().count(params);
    }
}
