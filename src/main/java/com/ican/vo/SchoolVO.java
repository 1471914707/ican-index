package com.ican.vo;

import com.ican.domain.Admin;
import com.ican.domain.Follow;
import com.ican.domain.School;
import com.ican.domain.UserInfo;

public class SchoolVO {
    private int id;
    private String headshot;
    private String name;
    private int sex;
    private int role;
    private int status;
    private String phone;
    private String email;
    private String schoolName;
    private String url;
    private String banner;
    private int country;
    private int province;
    private int city;
    private String address;
    private Follow follow;
    private String gmtCreate;
    private String gmtModified;

    public SchoolVO(School school, UserInfo userInfo) {
        this.id = userInfo.getId();
        this.headshot = userInfo.getHeadshot();
        this.name = userInfo.getName();
        this.sex = userInfo.getSex();
        this.role = userInfo.getRole();
        this.status = userInfo.getStatus();
        this.phone = userInfo.getPhone();
        this.email = userInfo.getEmail();
        this.gmtCreate = school.getGmtCreate();
        this.gmtModified = school.getGmtModified();
        this.schoolName = school.getName();
        this.url = school.getUrl();
        this.banner = school.getBanner();
        this.country = school.getCountry();
        this.province = school.getProvince();
        this.city = school.getCity();
        this.address = school.getAddress();
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

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Follow getFollow() {
        return follow;
    }

    public void setFollow(Follow follow) {
        this.follow = follow;
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
