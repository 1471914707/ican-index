package com.ican.config;

import com.ican.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ServiceFacade")
public class ServiceFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthPhotoService authPhotoService;

    @Autowired
    private CollegeService collegeService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public CityService getCityService() {
        return cityService;
    }

    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    public UserInfoService getUserInfoService() {
        return userInfoService;
    }

    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    public AuthPhotoService getAuthPhotoService() {
        return authPhotoService;
    }

    public void setAuthPhotoService(AuthPhotoService authPhotoService) {
        this.authPhotoService = authPhotoService;
    }

    public CollegeService getCollegeService() {
        return collegeService;
    }

    public void setCollegeService(CollegeService collegeService) {
        this.collegeService = collegeService;
    }
}
