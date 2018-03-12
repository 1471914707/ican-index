package com.ican.to;

import com.ican.domain.User;
import com.ican.domain.UserInfo;
import com.ican.util.IcanUtil;
import org.springframework.util.StringUtils;

import java.util.UUID;

public class AdminTO {
    private int id;
    private String headshot;
    private String name;
    private int sex;
    private String password;
    private int role;
    private int status;
    private String phone;
    private String email;
    private String gmtCreate;
    private String gmtModified;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public UserInfo toUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(this.id);
        if (StringUtils.isEmpty())
        userInfo.setHeadshot(this.headshot);
        userInfo.setName(this.name);
        //处理密码
        String salt = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
        this.password = IcanUtil.MD5(this.password + salt);
        userInfo.setPassword(this.password);
        userInfo.setSalt(salt);
        userInfo.setRole(this.role);
        userInfo.setSex(this.sex);
        userInfo.setStatus(this.status);


    }
}
