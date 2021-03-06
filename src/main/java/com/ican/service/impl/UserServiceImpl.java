package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.exception.icanServiceException;
import com.ican.domain.User;
import com.ican.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public void insert(User user) throws icanServiceException {
        Constant.DaoFacade.getUserDao().insert(user);
    }

    @Override
    public void delete(int id) throws icanServiceException {
        Constant.DaoFacade.getUserDao().delete(id);
    }

    @Override
    public User select(int id)  throws icanServiceException{
        return (User) Constant.DaoFacade.getUserDao().select(id);
    }

    @Override
    public void update(User user) throws icanServiceException {
        Constant.DaoFacade.getUserDao().update(user);
    }
}
