package com.ican.controller.college;

import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.service.UserInfoService;
import com.ican.util.*;
import com.ican.vo.CollegeVO;
import com.ican.vo.DepartmentVO;
import com.ican.vo.TeacherVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.TabExpander;
import java.text.SimpleDateFormat;
import java.util.*;

@Api("二级学院操作教师")
@Controller
@RequestMapping("/college/teacher")
public class TeacherCController {
    private final static Logger logger = LoggerFactory.getLogger(TeacherCController.class);

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public String index() {
        return "college/teacher/list";
    }

    @RequestMapping(value = {"/detail"}, method = RequestMethod.GET)
    public String detail(@RequestParam(value = "teacherId", defaultValue = "0") String teacherId,
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
            HashMap data = new HashMap();
            data = Constant.ServiceFacade.getTeacherWebService().listVO(0, self.getId(), degree, jobId, page, size);
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

    @ApiOperation("删除教师")
    @ResponseBody
   // @RequestMapping(value = "/deleteTeacher", method = RequestMethod.POST)
    public BaseResult deleteTeacher(@RequestParam(value = "id", defaultValue = "") int id,
                                    HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            //查找教师是否存在
            Teacher teacher = Constant.ServiceFacade.getTeacherService().select(id);
            if (teacher == null || teacher.getSchoolId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            teacher.setSchoolId(0);
            Constant.ServiceFacade.getTeacherService().save(teacher);
            //删除教师与系部的关系
            List<DepartmentTeacher> departmentTeacherList = Constant.ServiceFacade.getDepartmentTeacherService().list(null, 0, teacher.getId(), null, 1, 100);
            //因为条数不数，所以直接遍历
            for (DepartmentTeacher departmentTeacher : departmentTeacherList) {
                Department department = Constant.ServiceFacade.getDepartmentService().select(departmentTeacher.getDepartmentId());
                if (department != null) {
                    if (department.getSchoolId() == self.getId()) {
                        Constant.ServiceFacade.getDepartmentTeacherService().delete(departmentTeacher.getId());
                    }
                }
            }
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("删除教师异常", e);
            return result;
        }
    }

    @ApiOperation("增加教师与系部的关系")
    @ResponseBody
    @RequestMapping(value = "/addDepartmentTeacher", method = RequestMethod.POST)
    public BaseResult addDepartmentTeacher(@RequestParam(value = "teacherId", defaultValue = "") int teacherId,
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
            if (department == null || department.getCollegeId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            //校验是否有过关联
            List<DepartmentTeacher> departmentTeacherList = Constant.ServiceFacade.getDepartmentTeacherService().list(null, departmentId, teacherId, null, 1, 20);
            if (departmentTeacherList != null && departmentTeacherList.size() >
                    0) {
                result.setMsg("该教师已经关联到本系,无需再关联");
                return result;
            }
            //需要删除他在二级学院里的其他关联，因为只能有一个
            List<Department> departmentList = Constant.ServiceFacade.getDepartmentService().list(null, 0, self.getId(), null, 1, 200);
            Set<String> departmentSet = new HashSet<>();
            for (Department department1 : departmentList) {
                departmentSet.add(department1.getId() + "");
            }
            String departmentIds = String.join(",", departmentSet);
            List<DepartmentTeacher> departmentTeacherList1 = Constant.ServiceFacade.getDepartmentTeacherService().list(departmentIds, 0, teacherId, null, 1, 20);
            for (DepartmentTeacher departmentTeacher : departmentTeacherList1) {
                Constant.ServiceFacade.getDepartmentTeacherService().delete(departmentTeacher.getId());
            }
            DepartmentTeacher departmentTeacher = new DepartmentTeacher();
            departmentTeacher.setDepartmentId(departmentId);
            departmentTeacher.setTeacherId(teacherId);
            Constant.ServiceFacade.getDepartmentTeacherService().save(departmentTeacher);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("增加教师与系部的关系异常", e);
            return result;
        }
    }

    @ApiOperation("删除教师与二级学院(系部)的关系")
    @ResponseBody
    @RequestMapping(value = "/deleteTeacher", method = RequestMethod.POST)
    public BaseResult deleteDepartmentTeacher(@RequestParam(value = "teacherId", defaultValue = "") int teacherId,
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
            List<Department> departmentList = Constant.ServiceFacade.getDepartmentService().list(null, 0, self.getId(), null, 1, 200);
            Set<String> departmentSet = new HashSet<>();
            for (Department department1 : departmentList) {
                departmentSet.add(department1.getId() + "");
            }
            String departmentIds = String.join(",", departmentSet);
            List<DepartmentTeacher> departmentTeacherList1 = Constant.ServiceFacade.getDepartmentTeacherService().list(departmentIds, 0, teacherId, null, 1, 20);
            for (DepartmentTeacher departmentTeacher : departmentTeacherList1) {
                Constant.ServiceFacade.getDepartmentTeacherService().delete(departmentTeacher.getId());
            }
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("删除教师与系部的关系异常", e);
            return result;
        }
    }

    @ApiOperation("搜索教师")
    @ResponseBody
    @RequestMapping(value = "/searchTeacher", method = RequestMethod.GET)
    public BaseResult searchTeacher(@RequestParam(value = "jobId", defaultValue = "") String jobId,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "size", defaultValue = "20", required = false) int size,
                                    HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (StringUtils.isEmpty(jobId)) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        UserInfo self = Ums.getUser(request);
        try {
            College college = Constant.ServiceFacade.getCollegeService().select(self.getId());
            List<Teacher> teacherList = Constant.ServiceFacade.getTeacherService().list(null, college.getSchoolId(), jobId, 0, null, page, size);
            Set<String> teacherSet = new HashSet<>();
            Map teacherMap = new HashMap();
            for (Teacher teacher : teacherList) {
                teacherSet.add(teacher.getId() + "");
                teacherMap.put(teacher.getId(), teacher);
            }
            String teacherIds = String.join(",", teacherSet);
            List<TeacherVO> teacherVOList = new ArrayList<>();
            if (!StringUtils.isEmpty(teacherIds)) {
                List<UserInfo> teacherInfoList = Constant.ServiceFacade.getUserInfoService().list(teacherIds, null, null, 0, null, 1, size);
                for (UserInfo userInfo : teacherInfoList) {
                    TeacherVO teacherVO = new TeacherVO((Teacher) teacherMap.get(userInfo.getId()), userInfo);
                    teacherVOList.add(teacherVO);
                }
            }
            int total = Constant.ServiceFacade.getTeacherService().count(null, college.getSchoolId(), jobId, 0);
            Map data = new HashMap();
            data.put("list", teacherVOList);
            data.put("total", total);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("搜索教师异常", e);
            return result;
        }
    }

}
