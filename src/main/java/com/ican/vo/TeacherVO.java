package com.ican.vo;

import com.ican.domain.Teacher;
import com.ican.domain.UserInfo;

public class TeacherVO {
    private int id;
    private String headshot;
    private String name;
    private int role;
    private int status;
    private String phone;
    private String email;
    private String jobId;
    private int degree;
    private String degree_name;
    private String gmtCreate;
    private String gmtModified;

    public TeacherVO() {

    }

    public TeacherVO(Teacher teacher, UserInfo userInfo) {
        this.id = teacher.getId();
        this.headshot = userInfo.getHeadshot();
        this.name = userInfo.getName();
        this.role = userInfo.getRole();
        this.status = userInfo.getStatus();
        this.phone = userInfo.getPhone();
        this.email = userInfo.getEmail();
        this.jobId = teacher.getJobId();
        this.degree = teacher.getDegree();
        this.degree_name = teacher.getDegreeName();
        this.gmtCreate = userInfo.getGmtCreate();
        this.gmtModified = userInfo.getGmtModified();
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

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public String getDegree_name() {
        return degree_name;
    }

    public void setDegree_name(String degree_name) {
        this.degree_name = degree_name;
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
