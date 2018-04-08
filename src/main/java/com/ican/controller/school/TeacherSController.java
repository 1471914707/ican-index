package com.ican.controller.school;

import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.service.UserInfoService;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
import com.ican.vo.CollegeVO;
import com.ican.vo.DepartmentVO;
import com.ican.vo.TeacherVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api("学校操作教师")
@Controller
@RequestMapping("/school/teacher")
public class TeacherSController {
    private final static Logger logger = LoggerFactory.getLogger(TeacherSController.class);

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public String index() {
        return "school/teacher/list";
    }

    @RequestMapping(value = {"/detail"}, method = RequestMethod.GET)
    public String detail(@RequestParam(value = "teacherId",defaultValue = "0") String teacherId,
                         HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("teacherId", teacherId);
        return "school/teacher/detail";
    }

    @ApiOperation("获取教师列表数据")
    @ResponseBody
    @RequestMapping(value = "/listJson", method = RequestMethod.GET)
    public BaseResult listJson(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "20") int size,
                               @RequestParam(value = "degree", defaultValue = "0") int degree,
                               @RequestParam(value = "jobId", defaultValue = "", required = false) String jobId,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = new BaseResult();
        UserInfo self = Ums.getUser(request);
        try {
            //先找出该学校之下的所有系
            int departmentTotal = Constant.ServiceFacade.getDepartmentService().count(null, self.getId(), 0);
            List<Department> departmentList = Constant.ServiceFacade.getDepartmentService().list(null, self.getId(), 0, null, 1, departmentTotal);
            //拿到系ids
            Set<String> departmentSet = new HashSet<>();
            for (Department department : departmentList) {
                departmentSet.add(department.getId() + "");
            }
            String departmentIds = String.join(",", departmentSet);
            //查出所有的关联教师id
            int departmentTeacherTotal = Constant.ServiceFacade.getDepartmentTeacherService().count(departmentIds, 0, 0);
            List<DepartmentTeacher> departmentTeacherList = Constant.ServiceFacade.getDepartmentTeacherService().list(departmentIds, 0, 0, null, 1, departmentTeacherTotal);
            Set<String> teacherSet = new HashSet<>();
            for (DepartmentTeacher departmentTeacher : departmentTeacherList) {
                teacherSet.add(departmentTeacher.getTeacherId() + "");
            }
            String teacherIds = String.join(",", teacherSet);
            //查出教师列表
            List<Teacher> teacherList = Constant.ServiceFacade.getTeacherService().list(teacherIds, jobId, degree, "id desc", page, size);
            int teacherTotal = Constant.ServiceFacade.getTeacherService().count(teacherIds, jobId, 0);
            Set<String> userInfoSet = new HashSet<>();
            for (Teacher teacher : teacherList) {
                userInfoSet.add(teacher.getId() + "");
            }
            //对应的基础信息
            String userInfoIds = String.join(",", userInfoSet);
            List<UserInfo> userInfoList = Constant.ServiceFacade.getUserInfoService().list(userInfoIds, null, null, UserInfoService.USER_TEACHER, null, page, size);
            Map userInfoMap = new HashMap();
            for (UserInfo userInfo : userInfoList) {
                userInfoMap.put(userInfo.getId(), userInfo);
            }
            List<TeacherVO> teacherVOList = new ArrayList<>();
            for (Teacher teacher : teacherList) {
                TeacherVO teacherVO = new TeacherVO(teacher, (UserInfo) userInfoMap.get(teacher.getId()));
                teacherVOList.add(teacherVO);
            }
            Map data = new HashMap();
            data.put("list", teacherVOList);
            data.put("total", teacherTotal);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取教师列表数据异常", e);
            return result;
        }
    }

    @ApiOperation("获取教师详情")
    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public BaseResult info(@RequestParam(value = "id", defaultValue = "") String id,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        int teacherId = Integer.valueOf(id);
        try {
            Teacher teacher = Constant.ServiceFacade.getTeacherService().select(teacherId);
            if (teacher == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            UserInfo userInfo = Constant.ServiceFacade.getUserInfoService().select(teacherId);
            TeacherVO teacherVO = new TeacherVO(teacher, userInfo);
            //查看认证图片
            List<AuthPhoto> authPhotoList = Constant.ServiceFacade.getAuthPhotoService().list(teacherId, null, 1, 10);
            //学院-系等信息
            List<DepartmentTeacher> departmentTeacherList = Constant.ServiceFacade.getDepartmentTeacherService().list(null, 0, teacherId, null, 1, 1000);
            List<DepartmentVO> departmentVOList = new ArrayList<>();
            for (DepartmentTeacher departmentTeacher : departmentTeacherList) {
                DepartmentVO departmentVO = new DepartmentVO();
                Department department = Constant.ServiceFacade.getDepartmentService().select(departmentTeacher.getDepartmentId());
                if (department != null) {
                    departmentVO.setId(department.getId());
                    departmentVO.setName(department.getName());
                    College college = Constant.ServiceFacade.getCollegeService().select(department.getCollegeId());
                    //不能查看别人学校的系
                    if (college.getSchoolId() != self.getId()) {
                        continue;
                    }
                    UserInfo collegeInfo = Constant.ServiceFacade.getUserInfoService().select(department.getCollegeId());
                    CollegeVO collegeVO = new CollegeVO(college, collegeInfo);
                    departmentVO.setCollegeVO(collegeVO);
                    departmentVOList.add(departmentVO);
                }
            }

            Map data = new HashMap();
            data.put("departmentList", departmentVOList);
            data.put("authPhotoList", authPhotoList);
            data.put("teacher", teacherVO);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取教师详情异常", e);
            return result;
        }
    }

    @ApiOperation("删除教师与系部的关系")
    @ResponseBody
    @RequestMapping(value = "/deleteDepartmentTeacher", method = RequestMethod.POST)
    public BaseResult deleteDepartmentTeacher(@RequestParam(value = "teacherId", defaultValue = "") int teacherId,
                                              @RequestParam(value = "departmentId", defaultValue = "") int departmentId,
                                              HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            //查找教师是否存在
            Teacher teacher = Constant.ServiceFacade.getTeacherService().select(teacherId);
            if (teacher == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            //查找系是否存在
            Department department = Constant.ServiceFacade.getDepartmentService().select(departmentId);
            if (department == null || department.getSchoolId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            //校验是否有关联
            List<DepartmentTeacher> departmentTeacherList = Constant.ServiceFacade.getDepartmentTeacherService().list(null, departmentId, teacherId, null, 1, 1000);
            if (departmentTeacherList == null || departmentTeacherList.size() <= 0) {
                result.setMsg("该教师还未注册到本系,请先核实");
                return result;
            }
            Constant.ServiceFacade.getDepartmentTeacherService().delete(departmentTeacherList.get(0).getId());
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("删除教师与系部的关系异常", e);
            return result;
        }
    }
}
