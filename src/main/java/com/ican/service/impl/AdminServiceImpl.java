package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.exception.icanServiceException;
import com.ican.model.Admin;
import com.ican.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService{
    @Override
    public int insert(Admin admin) throws icanServiceException {
        if (admin == null) {
            return -1;
        }
        return Constant.DaoFacade.getAdminDao().insert(admin);
    }

    @Override
    public Admin select(int id) throws icanServiceException {
        return (Admin) Constant.DaoFacade.getAdminDao().select(id);
    }

    @Override
    public int update(Admin admin) throws icanServiceException {
        if (admin == null) {
            return -1;
        }
        return Constant.DaoFacade.getAdminDao().update(admin);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getAdminDao().delete(id);
    }

    @Override
    public int save(Admin admin) throws icanServiceException {
        if (admin == null) {
            return -1;
        }
        if (admin.getId() > 0) {
            update(admin);
        } else {
            if (StringUtils.isEmpty(admin.getEmail())) {
                admin.setEmail("");
            }
            if (StringUtils.isEmpty(admin.getPhone())) {
                admin.setPhone("");
            }
            insert(admin);
        }
        return admin.getId();
    }

    @Override
    public List<Admin> list(String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getAdminDao().list(param);
    }

    @Override
    public int count() throws icanServiceException {
        Map param = new HashMap();
        return Constant.DaoFacade.getAdminDao().count(param);
    }
}
