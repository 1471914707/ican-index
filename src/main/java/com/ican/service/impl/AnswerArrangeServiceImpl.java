package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.domain.AnswerArrange;
import com.ican.exception.icanServiceException;
import com.ican.service.AnswerArrangeService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnswerArrangeServiceImpl implements AnswerArrangeService{
    @Override
    public int insert(AnswerArrange answerArrange) throws icanServiceException {
        return Constant.DaoFacade.getAnswerArrangeDao().insert(answerArrange);
    }

    @Override
    public AnswerArrange select(int id) throws icanServiceException {
        return (AnswerArrange) Constant.DaoFacade.getAnswerArrangeDao().select(id);
    }

    @Override
    public int update(AnswerArrange answerArrange) throws icanServiceException {
        return Constant.DaoFacade.getAnswerArrangeDao().update(answerArrange);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getAnswerArrangeDao().delete(id);
    }

    @Override
    public int save(AnswerArrange answerArrange) throws icanServiceException {
        if (answerArrange == null) {
            return 0;
        }
        if (answerArrange.getId() > 0) {
            update(answerArrange);
        } else {
            if (StringUtils.isEmpty(answerArrange.getName())) {
                answerArrange.setName("");
            }
            if (StringUtils.isEmpty(answerArrange.getStartTime())) {
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
                String date = sd.format(new Date());
                answerArrange.setStartTime(date);
            }
            if (StringUtils.isEmpty(answerArrange.getEndTime())) {
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
                String date = sd.format(new Date());
                answerArrange.setEndTime(date);
            }
            insert(answerArrange);
        }
        return answerArrange.getId();
    }

    @Override
    public List<AnswerArrange> list(String ids, int activityId, int type, String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("activityId", activityId);
        param.put("type", type);
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getAnswerArrangeDao().list(param);
    }

    @Override
    public int count(String ids, int activityId, int type) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("activityId", activityId);
        param.put("type", type);
        return Constant.DaoFacade.getAnswerArrangeDao().count(param);
    }
}
