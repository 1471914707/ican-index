package com.ican.service.impl;

import com.ican.exception.icanServiceException;
import com.ican.model.College;
import com.ican.service.CollegeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollegeServiceImpl implements CollegeService {
    @Override
    public int delete(int id) throws icanServiceException {
        return 0;
    }

    @Override
    public College select(int id) throws icanServiceException {
        return null;
    }

    @Override
    public int insert(College college) throws icanServiceException {
        return 0;
    }

    @Override
    public int update(College college) throws icanServiceException {
        return 0;
    }

    @Override
    public int save(College college) throws icanServiceException {
        return 0;
    }

    @Override
    public List<College> list(String order, int page, int size) throws icanServiceException {
        return null;
    }

    @Override
    public int count() throws icanServiceException {
        return 0;
    }
}
