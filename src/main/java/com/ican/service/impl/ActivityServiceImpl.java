package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.domain.Activity;
import com.ican.exception.icanServiceException;
import com.ican.service.ActivityService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Override
    public int insert(Activity activity) throws icanServiceException {
        return Constant.DaoFacade.getActivityDao().insert(activity);
    }

    @Override
    public Activity select(int id) throws icanServiceException {
        return (Activity) Constant.DaoFacade.getActivityDao().select(id);
    }

    @Override
    public int update(Activity activity) throws icanServiceException {
        return Constant.DaoFacade.getActivityDao().update(activity);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getActivityDao().delete(id);
    }

    @Override
    public int save(Activity activity) throws icanServiceException {
        if (activity == null) {
            return 0;
        }
        if (activity.getId() > 0) {
            update(activity);
        } else {
            if (StringUtils.isEmpty(activity.getName())) {
                activity.setName("");
            }
            if (StringUtils.isEmpty(activity.getStartTime())) {
                activity.setStartTime("");
            }
            if (StringUtils.isEmpty(activity.getEndTime())) {
                activity.setEndTime("");
            }
            insert(activity);
        }
        return activity.getId();
    }

    @Override
    public List<Activity> list(String ids, int userId, String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("userId", userId);
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getActivityDao().list(param);
    }

    @Override
    public int count(String ids, int userId) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("userId", userId);
        return Constant.DaoFacade.getActivityDao().count(param);
    }
}
