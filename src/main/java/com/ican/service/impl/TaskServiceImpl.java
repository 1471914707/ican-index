package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.domain.Task;
import com.ican.exception.icanServiceException;
import com.ican.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskServiceImpl implements TaskService {
    @Override
    public int insert(Task task) throws icanServiceException {
        return Constant.DaoFacade.getTaskDao().insert(task);
    }

    @Override
    public Task select(int id) throws icanServiceException {
        return (Task) Constant.DaoFacade.getTaskDao().select(id);
    }

    @Override
    public int update(Task task) throws icanServiceException {
        return Constant.DaoFacade.getTaskDao().update(task);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getTaskDao().delete(id);
    }

    @Override
    public int save(Task task) throws icanServiceException {
        if (task == null) {
            return -1;
        }
        if (task.getId() > 0) {
            update(task);
        } else {
            if (StringUtils.isEmpty(task.getTitle())) {
                task.setTitle("");
            }
            if (StringUtils.isEmpty(task.getContent())) {
                task.setContent("");
            }
            if (StringUtils.isEmpty(task.getStartTime())) {
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = sd.format(new Date());
                task.setStartTime(date);
            }
            if (StringUtils.isEmpty(task.getEndTime())) {
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = sd.format(new Date());
                task.setEndTime(date);
            }
            insert(task);
        }
        return task.getId();
    }

    @Override
    public List<Task> list(String ids, int activityId, int ownerId, int executorId,
                           int projectId, int status, String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("activityId", activityId);
        param.put("ownerId", ownerId);
        param.put("executorId", executorId);
        param.put("status", status);
        param.put("projectId", projectId);
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getTaskDao().list(param);
    }

    @Override
    public int count(String ids, int activityId, int ownerId, int executorId, int projectId, int status) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("activityId", activityId);
        param.put("ownerId", ownerId);
        param.put("executorId", executorId);
        param.put("projectId", projectId);
        param.put("status", status);
        return Constant.DaoFacade.getTaskDao().count(param);
    }
}
