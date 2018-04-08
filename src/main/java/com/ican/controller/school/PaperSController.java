package com.ican.controller.school;

import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.service.UserInfoService;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
import com.ican.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.bcel.Const;
import org.aspectj.apache.bcel.classfile.ConstantNameAndType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Api("学校操作选题")
@Controller
@RequestMapping("/school/paper")
public class PaperSController {
    private final static Logger logger = LoggerFactory.getLogger(PaperSController.class);

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public String index() {
        return "/school/paper/list";
    }

    @ApiOperation("获取选题列表")
    @RequestMapping(value = "/listJson", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listJson(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "20") int size,
                               @RequestParam(value = "collegeId", defaultValue = "0") int collegeId,
                               @RequestParam(value = "departmentId", defaultValue = "0") int departmentId,
                               @RequestParam(value = "teacherId", defaultValue = "0") int teacherId,
                               @RequestParam(value = "current", defaultValue = "0") int current,
                               @RequestParam(value = "activityId", defaultValue = "0") int activityId,
                               @RequestParam(value = "title", defaultValue = "0") String title,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            List<Paper> paperList = Constant.ServiceFacade.getPaperService().list(null, activityId, current, self.getId(), collegeId, departmentId,
                    teacherId, title, "id desc", page, size);
            //关联的教师
            Set<String> teacherSet = new HashSet<>();
            for (Paper paper : paperList) {
                teacherSet.add(paper.getTeacherId() + "");
            }
            String teacherIds = String.join(",", teacherSet);
            List<Teacher> teacherList = Constant.ServiceFacade.getTeacherService().list(teacherIds, null, 0, null, page, size);
            List<UserInfo> teacherInfoList = Constant.ServiceFacade.getUserInfoService().list(teacherIds, null, null, 0, null, page, size);
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
            List<College> collegeList = Constant.ServiceFacade.getCollegeService().list(collegeIds, 0, null, page, size);
            List<UserInfo> collegeInfoList = Constant.ServiceFacade.getUserInfoService().list(collegeIds, null, null, 0, null, page, size);
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
            List<Department> departmentList = Constant.ServiceFacade.getDepartmentService().list(departmentIds, 0, 0, null, page, size);
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
                int num = Constant.ServiceFacade.getPaperStudentService().count(null, activityId, current, self.getId(), paper.getCollegeId(),
                        paper.getDepartmentId(), 0, 0, 0, paper.getId());
                paperVO.setNum(num);
                paperVOList.add(paperVO);
            }
            int total = Constant.ServiceFacade.getPaperService().count(null, activityId, current, self.getId(), collegeId, departmentId, teacherId,
                    title);
            Map data = new HashMap();
            data.put("list", paperVOList);
            data.put("total", total);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取选题列表异常", e);
            return result;
        }
    }

    @ApiOperation("获取选题详情")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public BaseResult info(@RequestParam(value = "id", defaultValue = "0") int id,
                           @RequestParam(value = "activityId", defaultValue = "0") int activityId,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            Paper paper = Constant.ServiceFacade.getPaperService().select(id);
            if (paper == null || paper.getSchoolId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            PaperVO paperVO = new PaperVO();
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
            TeacherVO teacherVO = new TeacherVO();
            paperVO.setTeacher(teacherVO);
            //关联的学生
            List<PaperStudent> paperStudentList = Constant.ServiceFacade.getPaperStudentService().list(null, activityId, 0, self.getId(),
                    0, 0, 0, 0, 0, paper.getId(), "id desc", 1, 100);
            Set<String> studentSet = new HashSet<>();
            for (PaperStudent paperStudent : paperStudentList) {
                studentSet.add(paperStudent.getStudentId() + "");
            }
            String studentIds = String.join(",", studentSet);
            List<Student> studentList = Constant.ServiceFacade.getStudentService().list(studentIds, self.getId(), 0, 0, 0, 0,
                    0, null, null, 1, 100);
            Set<String> studentInfoSet = new HashSet<>();
            for (Student student : studentList) {
                studentInfoSet.add(student.getId() + "");
            }
            String studentInfoIds = String.join(",", studentInfoSet);
            List<UserInfo> studentInfoList = Constant.ServiceFacade.getUserInfoService().list(studentInfoIds, null, null, UserInfoService.USER_STUDENT,
                    null, 1, 100);
            Map studentMap = new HashMap();
            for (Student student : studentList) {
                studentMap.put(student.getId(), student);
            }
            List<StudentVO> studentVOList = new ArrayList<>();
            for (Student student : studentList) {
                StudentVO studentVO = new StudentVO(student, (UserInfo) studentMap.get(student.getId()));
                studentVOList.add(studentVO);
            }
            BaseResultUtil.setSuccess(result, paperVO);
            return result;
        } catch (Exception e) {
            logger.error("获取选题详情异常", e);
            return result;
        }

    }
}
