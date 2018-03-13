package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.domain.Follow;
import com.ican.exception.icanServiceException;
import com.ican.service.FollowService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FollowServiceImpl implements FollowService {
    @Override
    public int insert(Follow follow) throws icanServiceException {
        return Constant.DaoFacade.getFollowDao().insert(follow);
    }

    @Override
    public Follow select(int id) throws icanServiceException {
        return (Follow) Constant.DaoFacade.getFollowDao().select(id);
    }

    @Override
    public int update(Follow follow) throws icanServiceException {
        return Constant.DaoFacade.getFollowDao().update(follow);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getFollowDao().delete(id);
    }

    @Override
    public int save(Follow follow) throws icanServiceException {
        if (follow == null) {
            return 0;
        }
        if (follow.getId() > 0) {
            update(follow);
        } else {
            if (StringUtils.isEmpty(follow.getContent())) {
                follow.setContent("");
            }
            if (StringUtils.isEmpty(follow.getFollowUserName())) {
                follow.setFollowUserName("账号" + follow.getFollowUserId());
            }
            insert(follow);
        }
        return follow.getId();
    }

    @Override
    public List<Follow> list(String ids, int followUserId, int followId, int followType, String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("followUserId", followUserId);
        param.put("followId", followId);
        param.put("followType", followType);
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getFollowDao().list(param);
    }

    @Override
    public int count(String ids, int followUserId, int followId, int followType) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("followUserId", followUserId);
        param.put("followId", followId);
        param.put("followType", followType);
        return Constant.DaoFacade.getFollowDao().count(param);
    }
}
