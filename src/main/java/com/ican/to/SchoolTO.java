package com.ican.to;

import com.ican.domain.School;
import com.ican.domain.UserInfo;
import com.ican.service.UserInfoService;

public class SchoolTO {
    private int id;
    private String headshot;
    private String name;
    private String phone;
    private String email;
    private String schoolName;
    private String url;
    private String banner;
    private int country;
    private int province;
    private int city;
    private String address;

    //认证照片
    private String auth1;//手持身份认证
    private String auth2;//手持与该校的关系认证照片

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

    public String getAuth1() {
        return auth1;
    }

    public void setAuth1(String auth1) {
        this.auth1 = auth1;
    }

    public String getAuth2() {
        return auth2;
    }

    public void setAuth2(String auth2) {
        this.auth2 = auth2;
    }

    public UserInfo toUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(this.id);
        userInfo.setHeadshot(this.headshot);
        userInfo.setName(this.schoolName);
        userInfo.setPhone(this.phone);
        userInfo.setEmail(this.email);
        userInfo.setRole(UserInfoService.USER_SCHOOL);
        userInfo.setStatus(UserInfoService.USER_STATUS_VALID);
        return userInfo;
    }

    public School toSchool() {
        School school = new School();
        school.setId(id);
        school.setAddress(this.address);
        school.setName(this.name);
        school.setUrl(this.url);
        school.setCountry(this.country);
        school.setProvince(this.province);
        school.setCity(this.city);
        school.setAddress(this.address);
        return school;
    }

}
