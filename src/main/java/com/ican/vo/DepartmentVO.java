package com.ican.vo;

import com.ican.domain.College;

public class DepartmentVO {
    private SchoolVO schoolVO;
    private CollegeVO collegeVO;
    private TeacherVO teacherVO;
    private int id;
    private String name;

    public DepartmentVO() {

    }

    public DepartmentVO(int id, int name, SchoolVO schoolVO, CollegeVO collegeVO, TeacherVO teacherVO) {
        this.id = id;
        this.schoolVO = schoolVO;
        this.collegeVO = collegeVO;
        this.teacherVO = teacherVO;
    }

    public TeacherVO getTeacherVO() {
        return teacherVO;
    }

    public void setTeacherVO(TeacherVO teacherVO) {
        this.teacherVO = teacherVO;
    }

    public SchoolVO getSchoolVO() {
        return schoolVO;
    }

    public void setSchoolVO(SchoolVO schoolVO) {
        this.schoolVO = schoolVO;
    }

    public CollegeVO getCollegeVO() {
        return collegeVO;
    }

    public void setCollegeVO(CollegeVO collegeVO) {
        this.collegeVO = collegeVO;
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
