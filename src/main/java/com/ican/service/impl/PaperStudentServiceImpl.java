package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.domain.PaperStudent;
import com.ican.exception.icanServiceException;
import com.ican.service.PaperStudentService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class PaperStudentServiceImpl implements PaperStudentService {
    @Override
    public int insert(PaperStudent paperStudent) throws icanServiceException {
        return Constant.DaoFacade.getPaperStudentDao().insert(paperStudent);
    }

    @Override
    public PaperStudent select(int id) throws icanServiceException {
        return (PaperStudent) Constant.DaoFacade.getPaperStudentDao().select(id);
    }

    @Override
    public int update(PaperStudent paperStudent) throws icanServiceException {
        return Constant.DaoFacade.getPaperStudentDao().update(paperStudent);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getPaperStudentDao().delete(id);
    }

    @Override
    public int save(PaperStudent paperStudent) throws icanServiceException {
        if (paperStudent == null) {
            return 0;
        }
        if (paperStudent.getId() > 0) {
            update(paperStudent);
        } else {
            insert(paperStudent);
        }
        return paperStudent.getId();
    }

    @Override
    public List<PaperStudent> list(String ids, int activityId, int current, int schoolId, int collegeId, int departmentId, int clazzId, int teacherId,
                                    int studentId, int paperId, String order, int page, int size) throws icanServiceException {
        HashMap param = new HashMap();
        param.put("ids", ids);
        param.put("activityId", activityId);
        param.put("current", current);
        param.put("schoolId", schoolId);
        param.put("collegeId", collegeId);
        param.put("departmentId", departmentId);
        param.put("clazzId", clazzId);
        param.put("teacherId", teacherId);
        param.put("studentId", studentId);
        param.put("paperId", paperId);
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getPaperStudentDao().list(param);
    }

    @Override
    public int count(String ids, int activityId, int current, int schoolId, int collegeId, int departmentId, int clazzId, int teacherId,
                      int studentId, int paperId) throws icanServiceException {
        HashMap param = new HashMap();
        param.put("ids", ids);
        param.put("activityId", activityId);
        param.put("current", current);
        param.put("schoolId", schoolId);
        param.put("collegeId", collegeId);
        param.put("departmentId", departmentId);
        param.put("clazzId", clazzId);
        param.put("teacherId", teacherId);
        param.put("studentId", studentId);
        param.put("paperId", paperId);
        return Constant.DaoFacade.getPaperStudentDao().count(param);
    }
}
