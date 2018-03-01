package com.ican.config;

import com.ican.dao.*;
import com.ican.service.SchoolService;
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

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private SchoolDao schoolDao;

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

    public AdminDao getAdminDao() {
        return adminDao;
    }

    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    public SchoolDao getSchoolDao() {
        return schoolDao;
    }

    public void setSchoolDao(SchoolDao schoolDao) {
        this.schoolDao = schoolDao;
    }

}
