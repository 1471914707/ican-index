package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.domain.Project;
import com.ican.exception.icanServiceException;
import com.ican.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Override
    public int insert(Project project) throws icanServiceException {
        return Constant.DaoFacade.getProjectDao().insert(project);
    }

    @Override
    public Project select(int id) throws icanServiceException {
        return (Project) Constant.DaoFacade.getProjectDao().select(id);
    }

    @Override
    public int update(Project project) throws icanServiceException {
        return Constant.DaoFacade.getProjectDao().update(project);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getProjectDao().delete(id);
    }

    @Override
    public int save(Project project) throws icanServiceException {
        if (project == null) {
            return 0;
        }
        if (project.getId() > 0) {
            update(project);
        } else {
            if (StringUtils.isEmpty(project.getContent())) {
                project.setContent("");
            }
            if (StringUtils.isEmpty(project.getTitle())) {
                project.setTitle("");
            }
            if (StringUtils.isEmpty(project.getStartTime())) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
                String date = simpleDateFormat.format(new Date());
                project.setStartTime(date);
            }
            if (StringUtils.isEmpty(project.getEndTime())) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
                String date = simpleDateFormat.format(new Date());
                project.setEndTime(date);
            }
            insert(project);
        }
        return project.getId();
    }

    @Override
    public List<Project> list(String ids, int activityId, int current, int schoolId, int collegeId, int departmentId,
                              int clazzId, int teacherId, int studentId, String title, int status, String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("activityId", activityId);
        param.put("current", current);
        param.put("schoolId", schoolId);
        param.put("collegeId", collegeId);
        param.put("departmentId", departmentId);
        param.put("clazzId", clazzId);
        param.put("teacherId", teacherId);
        param.put("studentId", studentId);
        param.put("title", title);
        param.put("status", status);
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getProjectDao().list(param);
    }

    @Override
    public int count(String ids, int activityId, int current, int schoolId, int collegeId, int departmentId, int clazzId, int teacherId,
                     int studentId, String title, int status) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("activityId", activityId);
        param.put("current", current);
        param.put("schoolId", schoolId);
        param.put("collegeId", collegeId);
        param.put("departmentId", departmentId);
        param.put("clazzId", clazzId);
        param.put("teacherId", teacherId);
        param.put("studentId", studentId);
        param.put("title", title);
        param.put("status", status);
        return Constant.DaoFacade.getProjectDao().count(param);
    }

    @Override
    public List<Project> list(String majorIds, int activityId, int collegeId, int teacherId, int status, String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("majorIds", majorIds);
        param.put("activityId", activityId);
        param.put("status", status);
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getProjectDao().list(param);
    }

    @Override
    public int count(String majorIds, int activityId, int collegeId, int teacherId, int status) throws icanServiceException {
        Map param = new HashMap();
        param.put("majorIds", majorIds);
        param.put("activityId", activityId);
        param.put("status", status);
        return Constant.DaoFacade.getProjectDao().count(param);
    }
}
