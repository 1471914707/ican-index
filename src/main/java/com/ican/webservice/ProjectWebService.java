package com.ican.webservice;

import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.vo.CollegeVO;
import com.ican.vo.ProjectVO;
import com.ican.vo.SchoolVO;
import com.ican.vo.TeacherVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProjectWebService {

    private final static Logger logger = LoggerFactory.getLogger(ProjectWebService.class);

    public ProjectVO getProjectVO(Project project) {
        try {
            ProjectVO projectVO = new ProjectVO();
            projectVO.setProject(project);
            //学校
            School school = Constant.ServiceFacade.getSchoolService().select(project.getSchoolId());
            UserInfo schoolInfo = Constant.ServiceFacade.getUserInfoService().select(project.getSchoolId());
            SchoolVO schoolVO = new SchoolVO(school, schoolInfo);
            projectVO.setSchool(schoolVO);
            //二级学院
            College college = Constant.ServiceFacade.getCollegeService().select(project.getCollegeId());
            UserInfo collegeInfo = Constant.ServiceFacade.getUserInfoService().select(project.getCollegeId());
            CollegeVO collegeVO = new CollegeVO(college, collegeInfo);
            projectVO.setCollege(collegeVO);
            //系
            Department department = Constant.ServiceFacade.getDepartmentService().select(project.getDepartmentId());
            projectVO.setDepartment(department);
            //教师
            Teacher teacher = Constant.ServiceFacade.getTeacherService().select(project.getTeacherId());
            UserInfo teacherInfo = Constant.ServiceFacade.getUserInfoService().select(project.getTeacherId());
            TeacherVO teacherVO = new TeacherVO(teacher, teacherInfo);
            projectVO.setTeacher(teacherVO);
            return projectVO;
        } catch (Exception e) {
            logger.error("返回ProjectVO异常", e);
            return null;
        }
    }


}
