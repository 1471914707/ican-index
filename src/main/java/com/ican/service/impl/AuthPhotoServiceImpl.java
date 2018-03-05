package com.ican.service.impl;

import com.ican.model.AuthPhoto;
import com.ican.config.Constant;
import com.ican.exception.icanServiceException;
import com.ican.service.AuthPhotoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthPhotoServiceImpl implements AuthPhotoService {

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getAuthPhotoDao().delete(id);
    }

    @Override
    public AuthPhoto select(int id) throws icanServiceException {
        return (AuthPhoto)Constant.DaoFacade.getAuthPhotoDao().select(id);
    }

    @Override
    public int insert(AuthPhoto authPhoto) throws icanServiceException {
        return Constant.DaoFacade.getAuthPhotoDao().insert(authPhoto);
    }

    @Override
    public int update(AuthPhoto authPhoto) throws icanServiceException {
        return Constant.DaoFacade.getAuthPhotoDao().update(authPhoto);
    }

    @Override
    public int save(AuthPhoto authPhoto) throws icanServiceException {
        if (authPhoto == null) {
            return -1;
        }
        if (authPhoto.getId() > 0) {
            update(authPhoto);
        } else {
            if (StringUtils.isEmpty(authPhoto.getUrl())) {
                authPhoto.setUrl("");
            }
            if (StringUtils.isEmpty(authPhoto.getRemark())) {
                authPhoto.setRemark("");
            }
            insert(authPhoto);
        }
        return authPhoto.getId();
    }

    @Override
    public List<AuthPhoto> list(String order, int page, int size) throws icanServiceException {
        Map params = new HashMap();
        params.put("order", order);
        params.put("start", (page - 1) * size);
        params.put("size", size);
        return Constant.DaoFacade.getAuthPhotoDao().list(params);
    }

    @Override
    public int count() throws icanServiceException {
        Map params = new HashMap();
        return Constant.DaoFacade.getAuthPhotoDao().count(params);
    }
}
