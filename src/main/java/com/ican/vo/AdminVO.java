package com.ican.vo;

import com.ican.domain.Admin;
import com.ican.domain.UserInfo;

public class AdminVO {
    private int id;
    private String headshot;
    private String name;
    private int sex;
    private int role;
    private int status;
    private String phone;
    private String email;
    private String gmtCreate;
    private String gmtModified;

    public AdminVO(Admin admin, UserInfo userInfo) {
        this.id = userInfo.getId();
        this.headshot = userInfo.getHeadshot();
        this.name = userInfo.getName();
        this.sex = userInfo.getSex();
        this.role = userInfo.getRole();
        this.status = userInfo.getStatus();
        this.phone = admin.getPhone();
        this.email = admin.getEmail();
        this.gmtCreate = admin.getGmtCreate();
        this.gmtModified = admin.getGmtModified();
    }

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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
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
}
