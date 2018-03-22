package com.ican.to;

import com.ican.domain.School;
import com.ican.domain.UserInfo;

public class SchoolTO {
    private int id;
    private String headshot;
    private String name;
    private String phone;
    private String email;
    private String schoolName;
    private String url;
    private int country;
    private int province;
    private int city;
    private String address;

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

    public UserInfo toUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(this.id);
        userInfo.setHeadshot(this.headshot);
        userInfo.setName(this.name);
        return userInfo;
    }

    public School toSchool() {
        School school = new School();
        school.setId(id);
        school.setAddress(this.address);
        school.setName(this.schoolName);
        school.setUrl(this.url);
        school.setPhone(this.phone);
        school.setEmail(this.email);
        school.setCountry(this.country);
        school.setProvince(this.province);
        school.setCity(this.city);
        school.setAddress(this.address);
        return school;
    }


}
