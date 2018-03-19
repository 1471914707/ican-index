package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.exception.icanServiceException;
import com.ican.domain.UserInfo;
import com.ican.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Override
    public int insert(UserInfo userInfo) throws icanServiceException {
        return Constant.DaoFacade.getUserInfoDao().insert(userInfo);
    }

    @Override
    public UserInfo select(int id) throws icanServiceException {
        return (UserInfo) Constant.DaoFacade.getUserInfoDao().select(id);
    }

    @Override
    public int update(UserInfo userInfo) throws icanServiceException {
        return Constant.DaoFacade.getUserInfoDao().update(userInfo);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getUserInfoDao().delete(id);
    }

    @Override
    public int save(UserInfo userInfo) throws icanServiceException {
        if (userInfo == null) {
            return 0;
        }
        if (userInfo.getId() > 0) {
            update(userInfo);
        } else {
            if (StringUtils.isEmpty(userInfo.getHeadshot())) {
                userInfo.setHeadshot("");
            }
            if (StringUtils.isEmpty(userInfo.getName())) {
                userInfo.setName("");
            }
            //默认密码123456，salt-123456
            if (StringUtils.isEmpty(userInfo.getPassword())) {
                userInfo.setPassword("EA48576F30BE1669971699C09AD05C94");
            }
            if (StringUtils.isEmpty(userInfo.getSalt())) {
                userInfo.setSalt("123456");
            }
            insert(userInfo);
        }
        return userInfo.getId();
    }

    @Override
    public List<UserInfo> list(String ids, int role, String order, int page, int size) throws icanServiceException {
        HashMap param = new HashMap();
        param.put("ids", ids);
        param.put("role", role);
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getUserInfoDao().list(param);
    }

    @Override
    public int count(String ids, int role) throws icanServiceException {
        HashMap param = new HashMap();
        param.put("ids", ids);
        param.put("role", role);
        return Constant.DaoFacade.getUserInfoDao().count(param);
    }
}
