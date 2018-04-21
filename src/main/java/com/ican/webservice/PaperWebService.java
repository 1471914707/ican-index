package com.ican.webservice;

import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.service.UserInfoService;
import com.ican.vo.CollegeVO;
import com.ican.vo.PaperVO;
import com.ican.vo.StudentVO;
import com.ican.vo.TeacherVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaperWebService {
    private final static Logger logger = LoggerFactory.getLogger(PaperWebService.class);

    public List<PaperVO> listVO(List<Paper> paperList) {
        if (paperList == null || paperList.size() <= 0) {
            return new ArrayList<>();
        }
        try {
            //关联的教师
            Set<String> teacherSet = new HashSet<>();
            for (Paper paper : paperList) {
                teacherSet.add(paper.getTeacherId() + "");
            }
            String teacherIds = String.join(",", teacherSet);
            List<Teacher> teacherList = Constant.ServiceFacade.getTeacherService().list(teacherIds, 0, null, 0, null, 1, 100);
            List<UserInfo> teacherInfoList = Constant.ServiceFacade.getUserInfoService().list(teacherIds, null, null, 0, null, 1, 100);
            Map teacherInfoMap = new HashMap();
            Map teacherMap = new HashMap();
            for (UserInfo userInfo : teacherInfoList) {
                teacherInfoMap.put(userInfo.getId(), userInfo);
            }
            for (Teacher teacher : teacherList) {
                TeacherVO teacherVO = new TeacherVO(teacher, (UserInfo) teacherInfoMap.get(teacher.getId()));
                teacherMap.put(teacher.getId(), teacherVO);
            }
            //所在二级学院
            Set<String> collegeSet = new HashSet<>();
            for (Paper paper : paperList) {
                teacherSet.add(paper.getCollegeId() + "");
            }
            String collegeIds = String.join(",", collegeSet);
            List<College> collegeList = Constant.ServiceFacade.getCollegeService().list(collegeIds, 0, null, 1, 100);
            List<UserInfo> collegeInfoList = Constant.ServiceFacade.getUserInfoService().list(collegeIds, null, null, 0, null, 1, 100);
            Map collegeInfoMap = new HashMap();
            Map collegeMap = new HashMap();
            for (UserInfo userInfo : collegeInfoList) {
                collegeInfoMap.put(userInfo.getId(), userInfo);
            }
            for (College college : collegeList) {
                CollegeVO collegeVO = new CollegeVO(college, (UserInfo) collegeInfoMap.get(college.getId()));
                collegeMap.put(college.getId(), collegeVO);
            }
            //所在系
            Set<String> departmentSet = new HashSet<>();
            for (Paper paper : paperList) {
                departmentSet.add(paper.getDepartmentId() + "");
            }
            String departmentIds = String.join(",", departmentSet);
            List<Department> departmentList = Constant.ServiceFacade.getDepartmentService().list(departmentIds, 0, 0, null, 1, 100);
            Map departmentMap = new HashMap();
            for (Department department : departmentList) {
                departmentMap.put(department.getId(), department);
            }
            //关联的学生选择人数
            List<PaperVO> paperVOList = new ArrayList<>();
            for (Paper paper : paperList) {
                PaperVO paperVO = new PaperVO();
                paperVO.setCollege((CollegeVO) collegeMap.get(paper.getCollegeId()));
                paperVO.setDepartment((Department) departmentMap.get(paper.getDepartmentId()));
                paperVO.setTeacher((TeacherVO) teacherMap.get(paper.getTeacherId()));
                paperVO.setPaper(paper);
                int num = Constant.ServiceFacade.getPaperStudentService().count(null, paper.getActivityId(), paper.getCurrent(), 0, 0,
                        0, 0, 0, 0, 0, paper.getId());
                paperVO.setNum(num);
                paperVOList.add(paperVO);
            }
            return paperVOList;
        } catch (Exception e) {
            logger.error("获取PaperListVo异常", e);
            return new ArrayList<>();
        }
    }

    public PaperVO getPaperVO(Paper paper) {
        if (paper == null) {
            return null;
        }
        try {
            PaperVO paperVO = new PaperVO();
            paperVO.setPaper(paper);
            //关联二级学院
            College college = Constant.ServiceFacade.getCollegeService().select(paper.getCollegeId());
            UserInfo collegeInfo = Constant.ServiceFacade.getUserInfoService().select(paper.getCollegeId());
            CollegeVO collegeVO = new CollegeVO(college, collegeInfo);
            paperVO.setCollege(collegeVO);
            //系
            Department department = Constant.ServiceFacade.getDepartmentService().select(paper.getDepartmentId());
            paperVO.setDepartment(department);
            //导师
            Teacher teacher = Constant.ServiceFacade.getTeacherService().select(paper.getTeacherId());
            UserInfo teacherInfo = Constant.ServiceFacade.getUserInfoService().select(paper.getTeacherId());
            TeacherVO teacherVO = new TeacherVO(teacher,teacherInfo);
            paperVO.setTeacher(teacherVO);
            //关联的学生
            List<PaperStudent> paperStudentList = Constant.ServiceFacade.getPaperStudentService().list(null, paper.getActivityId(), 0, paper.getSchoolId(),
                    0, 0, 0 ,0, 0, 0, paper.getId(), "id desc", 1, 100);
            Set<String> studentSet = new HashSet<>();
            for (PaperStudent paperStudent : paperStudentList) {
                studentSet.add(paperStudent.getStudentId() + "");
            }
            String studentIds = String.join(",", studentSet);
            List<Student> studentList = Constant.ServiceFacade.getStudentService().list(studentIds, paper.getSchoolId(), 0, 0, 0, 0,
                    0, null, null, 1, 100);
            Set<String> studentInfoSet = new HashSet<>();
            for (Student student : studentList) {
                studentInfoSet.add(student.getId() + "");
            }
            String studentInfoIds = String.join(",", studentInfoSet);
            List<UserInfo> studentInfoList = Constant.ServiceFacade.getUserInfoService().list(studentInfoIds, null, null, UserInfoService.USER_STUDENT,
                    null, 1, 100);
            Map studentInfoMap = new HashMap();
            for (UserInfo userInfo : studentInfoList) {
                studentInfoMap.put(userInfo.getId(), userInfo);
            }
            List<StudentVO> studentVOList = new ArrayList<>();
            for (Student student : studentList) {
                StudentVO studentVO = new StudentVO(student, (UserInfo) studentInfoMap.get(student.getId()));
                studentVOList.add(studentVO);
            }
            paperVO.setStudentList(studentVOList);
            return paperVO;
        } catch (Exception e) {
            logger.error("获取paperVo异常", e);
            return null;
        }
    }

}
