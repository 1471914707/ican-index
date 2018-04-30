package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.domain.Counts;
import com.ican.exception.icanServiceException;
import com.ican.service.CountsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CountsServiceImpl implements CountsService {
    @Override
    public int insert(Counts counts) throws icanServiceException {
        return Constant.DaoFacade.getCountsDao().insert(counts);
    }

    @Override
    public Counts select(int id) throws icanServiceException {
        return (Counts) Constant.DaoFacade.getCountsDao().select(id);
    }

    @Override
    public int update(Counts counts) throws icanServiceException {
        return Constant.DaoFacade.getCountsDao().update(counts);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getCountsDao().delete(id);
    }

    @Override
    public int save(Counts counts) throws icanServiceException {
        if (counts == null) {
            return 0;
        } else {
            if (counts.getId() > 0) {
                update(counts);
            } else {
                if (StringUtils.isEmpty(counts.getContent())) {
                    counts.setContent("[]");
                }
                insert(counts);
            }
        }
        return counts.getId();
    }

    @Override
    public List<Counts> list(String ids, int activityId, int type, String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("activityId", activityId);
        param.put("type", type);
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getCountsDao().list(param);
    }

    @Override
    public int count(String ids, int activityId, int type) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("activityId", activityId);
        param.put("type", type);
        return Constant.DaoFacade.getCountsDao().count(param);
    }
}
