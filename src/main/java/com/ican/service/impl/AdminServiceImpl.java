package com.ican.service.impl;

import com.ican.exception.icanServiceException;
import com.ican.model.Admin;
import com.ican.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{
    @Override
    public int insert(Admin admin) throws icanServiceException {
        return 0;
    }

    @Override
    public Admin select(int id) throws icanServiceException {
        return null;
    }

    @Override
    public int update(Admin admin) throws icanServiceException {
        return 0;
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return 0;
    }

    @Override
    public int save(Admin admin) throws icanServiceException {
        return 0;
    }

    @Override
    public List<Admin> list(String order, int page, int size) throws icanServiceException {
        return null;
    }

    @Override
    public int count() throws icanServiceException {
        return 0;
    }
}
