package com.ican.webservice;

import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.service.FollowService;
import com.ican.service.UserInfoService;
import com.ican.vo.*;
import org.apache.catalina.LifecycleState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class ProjectWebService {

    private final static Logger logger = LoggerFactory.getLogger(ProjectWebService.class);

    public List<ProjectVO> projectVOList(List<Project> projectList) {
        if (projectList == null || projectList.size() <= 0) {
            return new ArrayList<>();
        }
        try {
            int schoolId = projectList.get(0).getSchoolId();
            //关联学院,因为学院不多，所以全拿出来
            List<College> collegeList = Constant.ServiceFacade.getCollegeService().list(null, schoolId, null, 1, 1000);
            Set<String> collegeSet = new HashSet<>();
            Map collegeMap = new HashMap();
            for (College college : collegeList) {
                collegeSet.add(college.getId() + "");
            }
            String collegeIds = String.join(",", collegeSet);
            if (!StringUtils.isEmpty(collegeIds)) {
                List<UserInfo> collegeInfoList = Constant.ServiceFacade.getUserInfoService().list(collegeIds, null, null, UserInfoService.USER_COLLEGE, null, 1, 1000);
                for (UserInfo userInfo : collegeInfoList) {
                    collegeMap.put(userInfo.getId(), userInfo);
                }
            }
            //关联教师
            Set<String> teacherSet = new HashSet<>();
            //系
            Set<String> departmentSet = new HashSet<>();
            //专业
            Set<String> majorSet = new HashSet<>();
            //班级
            Set<String> clazzSet = new HashSet<>();
            //学生
            Set<String> studentSet = new HashSet<>();
            //关联课题
            Set<String> paperSet = new HashSet<>();

            for (Project project : projectList) {
                teacherSet.add(project.getTeacherId() + "");
                departmentSet.add(project.getDepartmentId() + "");
                majorSet.add(project.getMajorId() + "");
                studentSet.add(project.getStudentId() + "");
                paperSet.add(project.getPaperId() + "");
                clazzSet.add(project.getClazzId() + "");
            }

            String teacherIds = String.join(",", teacherSet);
            String departmentIds = String.join(",", departmentSet);
            String majorIds = String.join(",", majorSet);
            String studentIds = String.join(",", studentSet);
            String paperIds = String.join(",", paperSet);
            String clazzIds = String.join(",", clazzSet);

            List<UserInfo> teacherInfoList = new ArrayList<>();
            List<Teacher> teacherList = new ArrayList<>();
            List<Department> departmentList = new ArrayList<>();
            List<Major> majorList = new ArrayList<>();
            List<UserInfo> studentInfoList = new ArrayList<>();
            List<Student> studentList = new ArrayList<>();
            List<Paper> paperList = new ArrayList<>();
            List<Clazz> clazzList = new ArrayList<>();

            if (!StringUtils.isEmpty(teacherIds)) {
                teacherInfoList = Constant.ServiceFacade.getUserInfoService().list(teacherIds, null, null,
                        UserInfoService.USER_TEACHER, null, 1, 100);
                teacherList = Constant.ServiceFacade.getTeacherService().list(teacherIds, 0, null, 0, null, 1, 100);
            }
            if (!StringUtils.isEmpty(departmentIds)) {
                departmentList = Constant.ServiceFacade.getDepartmentService().list(departmentIds, schoolId, 0, null, 1, 100);
            }
            if (!StringUtils.isEmpty(majorIds)) {
                majorList = Constant.ServiceFacade.getMajorService().list(majorIds, 0, 0, 0, 0, null, 1, 100);
            }
            if (!StringUtils.isEmpty(studentIds)) {
                studentInfoList = Constant.ServiceFacade.getUserInfoService().list(studentIds, null, null, 0, null, 1, 100);
                studentList = Constant.ServiceFacade.getStudentService().list(studentIds, 0, 0, 0, 0, 0, 0, null, null, 1, 100);
            }
            if (!StringUtils.isEmpty(paperIds)) {
                paperList = Constant.ServiceFacade.getPaperService().list(paperIds, 0, 0, 0, 0, 0, 0, null, 0, null, 1, 100);
            }
            if (!StringUtils.isEmpty(clazzIds)) {
                clazzList = Constant.ServiceFacade.getClazzService().list(clazzIds, 0, 0, 0, 0, 0, null, 1, 100);
            }

            Map teacherInfoMap = new HashMap();
            Map teacherMap = new HashMap();
            Map studentMap = new HashMap();
            Map studentInfoMap = new HashMap();
            Map departmentMap = new HashMap();
            Map majorMap = new HashMap();
            Map paperMap = new HashMap();
            Map clazzMap = new HashMap();

            for (UserInfo userInfo : teacherInfoList) {
                teacherInfoMap.put(userInfo.getId(), userInfo);
            }
            for (Teacher teacher : teacherList) {
                teacherMap.put(teacher.getId(), teacher);
            }
            for (Department department : departmentList) {
                departmentMap.put(department.getId(), department);
            }
            for (Major major : majorList) {
                majorMap.put(major.getId(), major);
            }
            for (Student student : studentList) {
                studentMap.put(student.getId(), student);
            }
            for (UserInfo userInfo : studentInfoList) {
                studentInfoMap.put(userInfo.getId(), userInfo);
            }
            for (Paper paper : paperList) {
                paperMap.put(paper.getId(), paper);
            }
            for (Clazz clazz : clazzList) {
                clazzMap.put(clazz.getId(), clazz);
            }
            List<ProjectVO> projectVOList = new ArrayList<>();
            for (Project project : projectList) {
                ProjectVO projectVO = new ProjectVO();
                projectVO.setProject(project);
                projectVO.setId(project.getId());
                projectVO.setCollegeId(((UserInfo) collegeMap.get(project.getCollegeId())).getId());
                projectVO.setCollegeName(((UserInfo) collegeMap.get(project.getCollegeId())).getName());
                projectVO.setDepartmentId(((Department) departmentMap.get(project.getDepartmentId())).getId());
                projectVO.setDepartmentName(((Department) departmentMap.get(project.getDepartmentId())).getName());
                projectVO.setMajorId(((Major) majorMap.get(project.getMajorId())).getId());
                projectVO.setMajorName(((Major) majorMap.get(project.getMajorId())).getName());
                projectVO.setTeacherId(((UserInfo) teacherInfoMap.get(project.getTeacherId())).getId());
                projectVO.setTeacherName(((UserInfo) teacherInfoMap.get(project.getTeacherId())).getName());
                projectVO.setTeacher(new TeacherVO((Teacher) teacherMap.get(project.getTeacherId()), (UserInfo) teacherInfoMap.get(project.getTeacherId())));
                projectVO.setStudent(new StudentVO((Student) studentMap.get(project.getStudentId()), (UserInfo) studentInfoMap.get(project.getStudentId())));
                projectVO.setPaper((Paper)paperMap.get(project.getPaperId()));
                projectVO.setClazz((Clazz) clazzMap.get(project.getClazzId()));
                projectVO.setClazzId(((Clazz) clazzMap.get(project.getClazzId())).getId());
                projectVO.setClazzName(((Clazz) clazzMap.get(project.getClazzId())).getName());
                projectVOList.add(projectVO);
            }
            return projectVOList;
        } catch (Exception e) {
            logger.error("获取项目VOList异常", e);
            return new ArrayList<>();
        }
    }

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
            //专业
            Major major = Constant.ServiceFacade.getMajorService().select(project.getMajorId());
            projectVO.setMajor(major);
            //教师
            Teacher teacher = Constant.ServiceFacade.getTeacherService().select(project.getTeacherId());
            UserInfo teacherInfo = Constant.ServiceFacade.getUserInfoService().select(project.getTeacherId());
            TeacherVO teacherVO = new TeacherVO(teacher, teacherInfo);
            projectVO.setTeacher(teacherVO);
            //学生
            UserInfo studentInfo = Constant.ServiceFacade.getUserInfoService().select(project.getStudentId());
            Student student = Constant.ServiceFacade.getStudentService().select(project.getStudentId());
            StudentVO studentVO = new StudentVO(student, studentInfo);
            projectVO.setSchool(schoolVO);
            //关联选题
            List<PaperStudent> paperStudentList = Constant.ServiceFacade.getPaperStudentService().list(null, project.getActivityId(), project.getCurrent(),
                    project.getSchoolId(), project.getCollegeId(), project.getDepartmentId(), project.getMajorId(), project.getClazzId(), project.getTeacherId(),
                    project.getStudentId(), 0, null, 1, 1);
            if (paperStudentList != null && paperStudentList.size() > 0) {
                int paperId = paperStudentList.get(0).getPaperId();
                Paper paper = Constant.ServiceFacade.getPaperService().select(paperId);
                projectVO.setPaper(paper);
            }
            return projectVO;
        } catch (Exception e) {
            logger.error("返回ProjectVO异常", e);
            return null;
        }
    }


}
