package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.domain.Arrange;
import com.ican.exception.icanServiceException;
import com.ican.service.ArrangeService;
import freemarker.template.SimpleDate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArrangeServiceImpl implements ArrangeService {
    @Override
    public int insert(Arrange arrange) throws icanServiceException {
        return Constant.DaoFacade.getArrangeDao().insert(arrange);
    }

    @Override
    public Arrange select(int id) throws icanServiceException {
        return (Arrange) Constant.DaoFacade.getArrangeDao().select(id);
    }

    @Override
    public int update(Arrange arrange) throws icanServiceException {
        return Constant.DaoFacade.getArrangeDao().update(arrange);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getArrangeDao().delete(id);
    }

    @Override
    public int save(Arrange arrange) throws icanServiceException {
        if (arrange == null) {
            return 0;
        }
        if (arrange.getId() > 0) {
            update(arrange);
        } else {
            if (StringUtils.isEmpty(arrange.getName())) {
                arrange.setName("");
            }
            if (StringUtils.isEmpty(arrange.getStartTime())) {
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                arrange.setStartTime(sd.format(new Date()));
            }
            if (StringUtils.isEmpty(arrange.getEndTime())) {
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                arrange.setEndTime(sd.format(new Date()));
            }
            insert(arrange);
        }
        return arrange.getId();
    }

    @Override
    public List<Arrange> list(String ids, int userId, int activityId, String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("userId", userId);
        param.put("activityId", activityId);
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getArrangeDao().list(param);
    }

    @Override
    public int count(String ids, int userId, int activityId) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("userId", userId);
        param.put("activityId", activityId);
        return Constant.DaoFacade.getArrangeDao().count(param);
    }
}
