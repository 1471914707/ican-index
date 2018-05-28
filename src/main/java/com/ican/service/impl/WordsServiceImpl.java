package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.domain.Words;
import com.ican.exception.icanServiceException;
import com.ican.service.WordsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WordsServiceImpl implements WordsService {
    @Override
    public void insert(Words words) throws icanServiceException {
        Constant.DaoFacade.getWordsDao().insert(words);
    }

    @Override
    public void delete(int id) throws icanServiceException {
        Constant.DaoFacade.getWordsDao().delete(id);
    }

    @Override
    public Words select(int id) throws icanServiceException {
        return (Words) Constant.DaoFacade.getWordsDao().select(id);
    }

    @Override
    public void update(Words words) throws icanServiceException {
        Constant.DaoFacade.getWordsDao().update(words);
    }

    @Override
    public List<Words> list(String ids, String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getWordsDao().list(param);
    }

    @Override
    public int count(String ids) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        return Constant.DaoFacade.getWordsDao().count(param);
    }


}
