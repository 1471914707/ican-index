package com.ican.config;

import com.ican.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("IcanFacade")
public class IcanFacade {

   /* @Autowired
    private UserDao userDao;*/

    @Autowired
    private UserService userService;
/*
    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }*/

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
