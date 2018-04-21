package com.ican.to;

import com.ican.domain.DepartmentTeacher;
import com.ican.domain.Student;
import com.ican.domain.Teacher;
import com.ican.domain.UserInfo;
import com.ican.service.UserInfoService;
import com.ican.util.IcanUtil;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class TeacherTO {
    private int id;
    @NotNull(message = "头像不能为空")
    private String headshot;
    @NotNull(message = "姓名不能为空")
    private String name;
    @NotNull(message = "手机不能为空")
    private String phone;
    @NotNull(message = "邮箱不能为空")
    private String email;
    @Min(value = 1,message = "所在学校未知")
    private int schoolId;
  //  @Min(value = 1,message = "所在二级学院为必选")
    private int collegeId;
  //  @Min(value = 1,message = "所在系为必选")
    private int departmentId;
    @NotNull(message = "教师证编号不能为空")
    private String jobId;
    @NotNull(message = "密码不能为空")
    private String password;
    @NotNull(message = "职称不能为空")
    private int degree;
    private String degreeName;

    //认证照片
    @NotNull(message = "认证图片不能为空")
    private String auth1;//手持身份认证
    @NotNull(message = "认证图片不能为空")
    private String auth2;//手持与该校的关系认证照片

    @NotNull(message = "口令不能为空")
    private String keyt;

    @NotNull(message = "验证码不能为空")
    private String code;

    public UserInfo toUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(this.id);
        userInfo.setHeadshot(this.headshot);
        userInfo.setName(this.name);
        userInfo.setPhone(this.phone);
        userInfo.setEmail(this.email);
        String salt = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
        this.password = IcanUtil.MD5(this.password + salt);
        userInfo.setPassword(this.password);
        userInfo.setSalt(salt);
        userInfo.setRole(UserInfoService.USER_TEACHER);
        userInfo.setStatus(UserInfoService.USER_STATUS_VALID);
        return userInfo;
    }

    public Teacher toTeacher() {
        Teacher teacher = new Teacher();
        teacher.setSchoolId(this.schoolId);
        teacher.setId(this.id);
        teacher.setJobId(this.jobId);
        teacher.setDegreeName(this.degreeName);
        teacher.setDegree(this.degree);
        return teacher;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKeyt() {
        return keyt;
    }

    public void setKeyt(String keyt) {
        this.keyt = keyt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
