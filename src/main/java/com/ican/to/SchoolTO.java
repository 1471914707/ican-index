package com.ican.to;

import com.ican.domain.School;
import com.ican.domain.UserInfo;
import com.ican.service.UserInfoService;
import com.ican.util.IcanUtil;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class SchoolTO {
    private int id;
    @NotNull(message = "头像不能为空")
    private String headshot;
    @NotNull(message = "姓名不能为空")
    private String name;
    @NotNull(message = "手机不能为空")
    private String phone;
    @Email(message = "邮箱不能为空")
    private String email;
    @NotNull(message = "校名不能为空")
    private String schoolName;
    @NotNull(message = "网址不能为空")
    private String url;
    @NotNull(message = "横幅不能为空")
    private String banner;
    @NotNull(message = "学校电话不能为空")
    private String schoolPhone;
    @NotNull(message = "学校邮箱不能为空")
    private String schoolEmail;
    private int country;
    private int province;
    private int city;
    private String address;
    @NotNull(message = "密码不能为空")
    private String password;

    //认证照片
    @NotNull(message = "认证图片不能为空")
    private String auth1;//手持身份认证
    @NotNull(message = "认证图片不能为空")
    private String auth2;//手持与该校的关系认证照片

    @NotNull(message = "验证码不能为空")
    private String code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getSchoolPhone() {
        return schoolPhone;
    }

    public void setSchoolPhone(String schoolPhone) {
        this.schoolPhone = schoolPhone;
    }

    public String getSchoolEmail() {
        return schoolEmail;
    }

    public void setSchoolEmail(String schoolEmail) {
        this.schoolEmail = schoolEmail;
    }

    public UserInfo toUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(this.id);
        userInfo.setHeadshot(this.headshot);
        userInfo.setName(this.schoolName);
        userInfo.setPhone(this.phone);
        userInfo.setEmail(this.email);
        String salt = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
        this.password = IcanUtil.MD5(this.password + salt);
        userInfo.setPassword(this.password);
        userInfo.setSalt(salt);
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
        school.setBanner(this.banner);
        school.setCountry(this.country);
        school.setProvince(this.province);
        school.setCity(this.city);
        school.setAddress(this.address);
        school.setEmail(this.schoolEmail);
        school.setPhone(this.schoolPhone);
        return school;
    }

}
