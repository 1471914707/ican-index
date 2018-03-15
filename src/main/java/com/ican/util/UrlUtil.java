package com.ican.util;

import org.springframework.beans.factory.annotation.Value;

public class UrlUtil {

    @Value("${ican.url.admin}")
    private String adminUrl;

    @Value("${ican.url.school}")
    private String schoolUrl;

    @Value("${ican.url.college}")
    private String collegeUrl;

    @Value("${ican.url.teacher}")
    private String teacherUrl;

    @Value("${ican.url.student}")
    private String studentUrl;

    @Value("${ican.url.bk}")
    private String bkUrl;

    public String getAdminUrl() {
        return adminUrl;
    }

    public void setAdminUrl(String adminUrl) {
        this.adminUrl = adminUrl;
    }

    public String getSchoolUrl() {
        return schoolUrl;
    }

    public void setSchoolUrl(String schoolUrl) {
        this.schoolUrl = schoolUrl;
    }

    public String getCollegeUrl() {
        return collegeUrl;
    }

    public void setCollegeUrl(String collegeUrl) {
        this.collegeUrl = collegeUrl;
    }

    public String getTeacherUrl() {
        return teacherUrl;
    }

    public void setTeacherUrl(String teacherUrl) {
        this.teacherUrl = teacherUrl;
    }

    public String getStudentUrl() {
        return studentUrl;
    }

    public void setStudentUrl(String studentUrl) {
        this.studentUrl = studentUrl;
    }

    public String getBkUrl() {
        return bkUrl;
    }

    public void setBkUrl(String bkUrl) {
        this.bkUrl = bkUrl;
    }
}
