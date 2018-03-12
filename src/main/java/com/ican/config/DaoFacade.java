package com.ican.config;

import com.ican.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("DaoFacade")
public class DaoFacade {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private AuthPhotoDao authPhotoDao;

    @Autowired
    private CollegeDao collegeDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private DepartmentTeacherDao departmentTeacherDao;

    @Autowired
    private ClazzDao clazzDao;

    @Autowired
    private StudentDao studentDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public CityDao getCityDao() {
        return cityDao;
    }

    public void setCityDao(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }

    public void setUserInfoDao(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    public AdminDao getAdminDao() {
        return adminDao;
    }

    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    public SchoolDao getSchoolDao() {
        return schoolDao;
    }

    public void setSchoolDao(SchoolDao schoolDao) {
        this.schoolDao = schoolDao;
    }

    public AuthPhotoDao getAuthPhotoDao() {
        return authPhotoDao;
    }

    public void setAuthPhotoDao(AuthPhotoDao authPhotoDao) {
        this.authPhotoDao = authPhotoDao;
    }

    public CollegeDao getCollegeDao() {
        return collegeDao;
    }

    public void setCollegeDao(CollegeDao collegeDao) {
        this.collegeDao = collegeDao;
    }

    public TeacherDao getTeacherDao() {
        return teacherDao;
    }

    public void setTeacherDao(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    public DepartmentDao getDepartmentDao() {
        return departmentDao;
    }

    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    public DepartmentTeacherDao getDepartmentTeacherDao() {
        return departmentTeacherDao;
    }

    public void setDepartmentTeacherDao(DepartmentTeacherDao departmentTeacherDao) {
        this.departmentTeacherDao = departmentTeacherDao;
    }

    public ClazzDao getClazzDao() {
        return clazzDao;
    }

    public void setClazzDao(ClazzDao clazzDao) {
        this.clazzDao = clazzDao;
    }

    public StudentDao getStudentDao() {
        return studentDao;
    }

    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }
}
