package com.ican.config;

import com.ican.dao.CityDao;
import com.ican.dao.UserDao;
import com.ican.dao.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("DaoFacade")
public class DaoFacade {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private UserInfoDao userInfoDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public CityDao getCityDao() {
        return cityDao;
    }

    public void setCityDao(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }

    public void setUserInfoDao(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }
}
