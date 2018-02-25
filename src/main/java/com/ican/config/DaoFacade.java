package com.ican.config;

import com.ican.dao.CityDao;
import com.ican.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("DaoFacade")
public class DaoFacade {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CityDao cityDao;

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
}
