package com.ican.vo;

import com.ican.domain.*;

public class CollegeVO {
    private int id;
    private int schoolId;
    private String headshot;
    private String name;
    private int role;
    private int status;
    private String phone;
    private String email;
    private String collegeName;
    private String url;
    private String gmtCreate;
    private String gmtModified;

    public CollegeVO(College college, UserInfo userInfo) {
        this.id = userInfo.getId();
        this.schoolId = college.getSchoolId();
        this.headshot = userInfo.getHeadshot();
        this.name = userInfo.getName();
        this.role = userInfo.getRole();
        this.status = userInfo.getStatus();
        this.phone = college.getPhone();
        this.email = college.getEmail();
        this.collegeName = college.getName();
        this.url = college.getUrl();
        this.gmtCreate = college.getGmtCreate();
        this.gmtModified = college.getGmtModified();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schollId) {
        this.schoolId = schollId;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }

    public UserInfo toUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setStatus(this.status);
        userInfo.setRole(this.role);
        userInfo.setName(this.name);
        userInfo.setHeadshot(this.headshot);
        userInfo.setId(this.id);
        return userInfo;
    }

    public College toCollege() {
        College college = new College();
        college.setId(this.id);
        college.setSchoolId(this.schoolId);
        college.setName(this.collegeName);
        college.setPhone(this.phone);
        college.setEmail(this.email);
        college.setUrl(this.url);
        return college;
    }
}
