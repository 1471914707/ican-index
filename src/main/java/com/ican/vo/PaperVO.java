package com.ican.vo;

import com.ican.domain.Department;
import com.ican.domain.Paper;
import com.ican.domain.School;

import java.util.List;

public class PaperVO {
    private Paper paper;
    private TeacherVO teacher;
    private SchoolVO school;
    private CollegeVO college;
    private Department department;
    private List<StudentVO> studentList;

    private int num; //已选人数

    public SchoolVO getSchool() {
        return school;
    }

    public void setSchool(SchoolVO school) {
        this.school = school;
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

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public TeacherVO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherVO teacher) {
        this.teacher = teacher;
    }

    public List<StudentVO> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentVO> studentList) {
        this.studentList = studentList;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
