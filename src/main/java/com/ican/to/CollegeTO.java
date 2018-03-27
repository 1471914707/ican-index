package com.ican.to;

import com.ican.domain.College;
import com.ican.domain.UserInfo;
import com.ican.service.UserInfoService;

public class CollegeTO {
    private int id;
    private String headshot;
    private String name;
    private String phone;
    private String email;
    private String collegeName;
    private String url;
    private int schoolId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeadshot() {
        return headshot;
    }

    public void setHeadshot(String headshot) {
        this.headshot = headshot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public UserInfo toUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setHeadshot(this.headshot);
        userInfo.setName(this.name);
        userInfo.setRole(UserInfoService.USER_COLLEGE);
        userInfo.setStatus(UserInfoService.USER_STATUS_VALID);
        return userInfo;
    }

    public College toCollege() {
        College college = new College();
        college.setId(this.id);
        college.setUrl(this.url);
        college.setEmail(this.email);
        college.setPhone(this.phone);
        college.setName(this.collegeName);
        college.setSchoolId(this.schoolId);
        return college;
    }
}
