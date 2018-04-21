package com.ican.webservice;

import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.service.UserInfoService;
import com.ican.vo.StudentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class StudentWebService {
    private final static Logger logger = LoggerFactory.getLogger(StudentWebService.class);

    public List<StudentVO> listVO(List<Student> studentList) {
        if (studentList == null || studentList.size() <= 0) {
            return new ArrayList<>();
        }
        try {
            Set<String> studentSet = new HashSet<>();
            //二级学院
            Set<String> collegeSet = new HashSet<>();
            //系
            Set<String> departmentSet = new HashSet<>();
            //教师
            Set<String> teacherSet = new HashSet<>();
            //专业
            Set<String> majorSet = new HashSet<>();
            //班级
            Set<String> clazzSet = new HashSet<>();

            for (Student student : studentList) {
                studentSet.add(student.getId() + "");
                collegeSet.add(student.getCollegeId() + "");
                departmentSet.add(student.getDepartmentId() + "");
                teacherSet.add(student.getTeacherId() + "");
                clazzSet.add(student.getClazzId() + "");
                majorSet.add(student.getMajorId() + "");
            }
            String ids = String.join(",", studentSet);
            String collegeIds = String.join(",", collegeSet);
            String departmentIds = String.join(",", departmentSet);
            String majorIds = String.join(",", majorSet);
            String teacherIds = String.join(",", teacherSet);
            String clazzIds = String.join(",", clazzSet);
            List<UserInfo> collegeList = new ArrayList<>();
            List<UserInfo> userInfoList = new ArrayList<>();
            List<UserInfo> teacherInfoList = new ArrayList<>();
            List<Department> departmentList = new ArrayList<>();
            List<Major> majorList = new ArrayList<>();
            List<Clazz> clazzList = new ArrayList<>();
            if (!StringUtils.isEmpty(ids)) {
                userInfoList = Constant.ServiceFacade.getUserInfoService().list(ids, null, null,
                        UserInfoService.USER_STUDENT, null, 1, 100);
            }
            if (!StringUtils.isEmpty(collegeIds)) {
                collegeList = Constant.ServiceFacade.getUserInfoService().list(collegeIds, null, null, 0, null, 1, 100);
            }
            if (!StringUtils.isEmpty(teacherIds)) {
                teacherInfoList = Constant.ServiceFacade.getUserInfoService().list(teacherIds, null, null, 0, null, 1, 100);
            }
            if (!StringUtils.isEmpty(departmentIds)) {
                departmentList = Constant.ServiceFacade.getDepartmentService().list(departmentIds, 0, 0, null, 1, 100);
            }
            if (!StringUtils.isEmpty(majorIds)) {
                majorList = Constant.ServiceFacade.getMajorService().list(majorIds, 0, 0, 0, 0, null, 1, 100);
            }
            if (!StringUtils.isEmpty(clazzIds)) {
                clazzList = Constant.ServiceFacade.getClazzService().list(clazzIds, 0, 0, 0, 0, 0, null, 1, 100);
            }
            Map userInfoMap = new HashMap();
            Map collegeMap = new HashMap();
            Map departmentMap = new HashMap();
            Map teacherMap = new HashMap();
            Map majorMap = new HashMap();
            Map clazzMap = new HashMap();
            for (UserInfo userInfo : userInfoList) {
                userInfoMap.put(userInfo.getId(), userInfo);
            }
            for (UserInfo college : collegeList) {
                collegeMap.put(college.getId(), college);
            }
            for (Department department : departmentList) {
                departmentMap.put(department.getId(), department);
            }
            for (Major major : majorList) {
                majorMap.put(major.getId(), major);
            }
            for (UserInfo userInfo : teacherInfoList) {
                teacherMap.put(userInfo.getId(), userInfo);
            }
            for (Clazz clazz : clazzList) {
                clazzMap.put(clazz.getId(), clazz);
            }
            List<StudentVO> studentVOList = new ArrayList<>();
            for (Student student : studentList) {
                StudentVO studentVO = new StudentVO(student, (UserInfo) userInfoMap.get(student.getId()));
                studentVO.setCollegeName(((UserInfo) collegeMap.get(student.getCollegeId())).getName());
                studentVO.setDepartmentName(((Department) departmentMap.get(student.getDepartmentId())).getName());
                studentVO.setMajorName(((Major) majorMap.get(student.getMajorId())).getName());
                studentVO.setClazzName(((Clazz) clazzMap.get(student.getClazzId())).getName());
                studentVO.setTeacherName(((UserInfo) (teacherMap.get(student.getTeacherId()))).getName());
                studentVOList.add(studentVO);
            }
            return studentVOList;
        } catch (Exception e) {
            logger.error("获取StudentVOList异常", e);
            return new ArrayList<>();
        }
    }

    public StudentVO studentVO(Student student, int activityId) {
        if (student == null) {
            return null;
        }
        try {
            int id = student.getId();
            int schoolId = student.getSchoolId();
            UserInfo userInfo = Constant.ServiceFacade.getUserInfoService().select(id);
            StudentVO studentVO = new StudentVO(student, userInfo);
            //学校
            UserInfo school = Constant.ServiceFacade.getUserInfoService().select(student.getSchoolId());
            studentVO.setSchoolName(school.getName());
            //学院
            UserInfo college = Constant.ServiceFacade.getUserInfoService().select(student.getCollegeId());
            studentVO.setCollegeName(college.getName());
            //系
            Department department = Constant.ServiceFacade.getDepartmentService().select(student.getDepartmentId());
            studentVO.setDepartmentName(department.getName());
            //专业
            Major major = Constant.ServiceFacade.getMajorService().select(student.getMajorId());
            studentVO.setMajorName(major.getName());
            //班级
            Clazz clazz = Constant.ServiceFacade.getClazzService().select(student.getClazzId());
            studentVO.setClazzName(clazz.getName());
            //导师
            if (student.getTeacherId() > 0) {
                UserInfo teacher = Constant.ServiceFacade.getUserInfoService().select(student.getTeacherId());
                studentVO.setTeacherName(teacher.getName());
            }
            //认证图片
            List<AuthPhoto> authPhotoList = Constant.ServiceFacade.getAuthPhotoService().list(student.getId(), "id desc", 1, 100);
            studentVO.setAuthPhotoList(authPhotoList);
            if (activityId > 0) {
                //选题,只有一条
                List<PaperStudent> paperStudentList = Constant.ServiceFacade.getPaperStudentService().list(null, activityId, 0, schoolId, 0, 0,
                        0,0, 0, id, 0, null, 1, 100);
                if (paperStudentList != null && paperStudentList.size() > 0) {
                    Paper paper = Constant.ServiceFacade.getPaperService().select(paperStudentList.get(0).getPaperId());
                    studentVO.setPaper(paper);
                }
                //学生的项目,只有一条
                List<Project> projectList = Constant.ServiceFacade.getProjectService().list(null, activityId, 0, 0, 0, 0,
                        0, 0, id, null, 0,null, 1, 100);
                if (projectList != null && projectList.size() > 0) {
                    Project project = projectList.get(0);
                    studentVO.setProject(project);
                }
            }
            return studentVO;
        } catch (Exception e) {
            logger.error("获取StudentVo异常", e);
            return null;
        }
    }

}
