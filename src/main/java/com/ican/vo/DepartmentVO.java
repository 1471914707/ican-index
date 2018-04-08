package com.ican.vo;

import com.ican.domain.College;

public class DepartmentVO {
    private SchoolVO school;
    private CollegeVO college;
    private TeacherVO teacher;
    private int id;
    private String name;

    public DepartmentVO() {

    }

    public DepartmentVO(int id, int name, SchoolVO schoolVO, CollegeVO collegeVO, TeacherVO teacherVO) {
        this.id = id;
        this.school = schoolVO;
        this.college = collegeVO;
        this.teacher = teacherVO;
    }

    public TeacherVO getTeacherVO() {
        return teacher;
    }

    public void setTeacherVO(TeacherVO teacherVO) {
        this.teacher = teacherVO;
    }

    public SchoolVO getSchoolVO() {
        return school;
    }

    public void setSchoolVO(SchoolVO schoolVO) {
        this.school = schoolVO;
    }

    public CollegeVO getCollegeVO() {
        return college;
    }

    public void setCollegeVO(CollegeVO collegeVO) {
        this.college = collegeVO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
