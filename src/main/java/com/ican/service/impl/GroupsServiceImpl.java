package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.domain.Groups;
import com.ican.exception.icanServiceException;
import com.ican.service.GroupsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupsServiceImpl implements GroupsService {
    @Override
    public int insert(Groups groups) throws icanServiceException {
        return Constant.DaoFacade.getGroupsDao().insert(groups);
    }

    @Override
    public Groups select(int id) throws icanServiceException {
        return (Groups) Constant.DaoFacade.getGroupsDao().select(id);
    }

    @Override
    public int update(Groups groups) throws icanServiceException {
        return Constant.DaoFacade.getGroupsDao().update(groups);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getGroupsDao().delete(id);
    }

    @Override
    public int save(Groups groups) throws icanServiceException {
        if (groups == null) {
            return 0;
        }
        if (groups.getId() > 0) {
            update(groups);
        } else {
            if (StringUtils.isEmpty(groups.getProjectIds())) {
                groups.setProjectIds("0");
            }
            if (StringUtils.isEmpty(groups.getName())) {
                groups.setName("");
            }
            insert(groups);
        }
        return groups.getId();
    }

    @Override
    public List<Groups> list(String ids, int answerId, int userId, String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("answerId", answerId);
        param.put("userId", userId);
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getGroupsDao().list(param);
    }

    @Override
    public int count(String ids, int answerId, int userId) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("answerId", answerId);
        param.put("userId", userId);
        return Constant.DaoFacade.getGroupsDao().count(param);
    }

}
