package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.domain.Rating;
import com.ican.exception.icanServiceException;
import com.ican.service.RatingService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RatingServiceImpl implements RatingService {
    @Override
    public int insert(Rating rating) throws icanServiceException {
        return Constant.DaoFacade.getRatingDao().insert(rating);
    }

    @Override
    public Rating select(int id) throws icanServiceException {
        return (Rating) Constant.DaoFacade.getRatingDao().select(id);
    }

    @Override
    public int update(Rating rating) throws icanServiceException {
        return Constant.DaoFacade.getRatingDao().update(rating);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getRatingDao().delete(id);
    }

    @Override
    public int save(Rating rating) throws icanServiceException {
        if (rating == null) {
            return 0;
        }
        if (rating.getId() > 0) {
            update(rating);
        } else {
            if (StringUtils.isEmpty(rating.getRemark())) {
                rating.setRemark("");
            }
            insert(rating);
        }
        return rating.getId();
    }

    @Override
    public List<Rating> list(String ids, int activityId, int groupsId, int projectId, int teacherId, String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("activityId", activityId);
        param.put("teacherId", teacherId);
        param.put("groupsId", groupsId);
        param.put("projectId", projectId);
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getRatingDao().list(param);
    }

    @Override
    public int count(String ids, int activityId, int groupsId, int projectId, int teacherId) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("activityId", activityId);
        param.put("teacherId", teacherId);
        param.put("groupsId", groupsId);
        param.put("projectId", projectId);
        return Constant.DaoFacade.getRatingDao().count(param);
    }
}
