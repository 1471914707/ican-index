package com.ican.vo;

import com.ican.domain.Department;
import com.ican.domain.Major;
import com.ican.domain.Paper;
import com.ican.domain.Project;

public class ProjectVO {
    private Project project;
    private SchoolVO school;
    private TeacherVO teacher;
    private CollegeVO college;
    private Department department;
    private Major major;
    private StudentVO student;
    private Paper paper;

    private int id;
    private String collegeName;
    private int collegeId;
    private String departmentName;
    private int departmentId;
    private String TeacherName;
    private int teacherId;
    private int majorId;
    private String majorName;

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
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

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public SchoolVO getSchool() {
        return school;
    }

    public void setSchool(SchoolVO school) {
        this.school = school;
    }

    public TeacherVO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherVO teacher) {
        this.teacher = teacher;
    }

    public CollegeVO getCollege() {
        return college;
    }

    public void setCollege(CollegeVO college) {
        this.college = college;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public StudentVO getStudent() {
        return student;
    }

    public void setStudent(StudentVO student) {
        this.student = student;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }
}
