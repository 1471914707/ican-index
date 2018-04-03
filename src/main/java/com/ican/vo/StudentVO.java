package com.ican.vo;

import com.ican.domain.*;

import java.util.List;

public class StudentVO {
    private int id;
    private int schoolId;
    private int collegeId;
    private int departmentId;
    private int clazzId;
    private int teacherId;
    private String headshot;
    private String name;
    private int role;
    private int status;
    private String phone;
    private String email;
    private int current;
    private String jobId;

    private Paper paper;
    private Project project;

    private List<AuthPhoto> authPhotoList;

    private String gmtCreate;
    private String gmtModified;

    public StudentVO() {

    }

    public StudentVO(Student student, UserInfo userInfo) {
        this.id = student.getId();
        this.schoolId = student.getSchoolId();
        this.collegeId = student.getCollegeId();
        this.departmentId = student.getDepartmentId();
        this.clazzId = student.getClazzId();
        this.teacherId = student.getTeacherId();
        this.current = student.getCurrent();
        this.jobId = student.getJobId();

        this.headshot = userInfo.getHeadshot();
        this.name = userInfo.getName();
        this.phone = userInfo.getPhone();
        this.email = userInfo.getEmail();
        this.role = userInfo.getRole();
        this.status = userInfo.getStatus();
        this.gmtCreate = userInfo.getGmtCreate();
        this.gmtModified = userInfo.getGmtModified();
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<AuthPhoto> getAuthPhotoList() {
        return authPhotoList;
    }

    public void setAuthPhotoList(List<AuthPhoto> authPhotoList) {
        this.authPhotoList = authPhotoList;
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

    public int getClazzId() {
        return clazzId;
    }

    public void setClazzId(int clazzId) {
        this.clazzId = clazzId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
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

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
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
