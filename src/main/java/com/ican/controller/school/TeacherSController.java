package com.ican.controller.school;

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
import java.text.SimpleDateFormat;
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
            List<Teacher> teacherList = Constant.ServiceFacade.getTeacherService().list(null, self.getId(), jobId, degree, "id desc", page, size);
            int total = Constant.ServiceFacade.getTeacherService().count(null, self.getId(), jobId, degree);
            Set<String> teacherSet = new HashSet<>();
            for (Teacher teacher : teacherList) {
                teacherSet.add(teacher.getId() + "");
            }
            List<TeacherVO> teacherVOList = new ArrayList<>();
            String teacherIds = String.join(",", teacherSet);
            if (!StringUtils.isEmpty(teacherIds)) {
                List<UserInfo> teacherInfoList = Constant.ServiceFacade.getUserInfoService().list(teacherIds, null, null, 0, null, page, size);
                Map teacherMap = new HashMap();
                for (UserInfo userInfo : teacherInfoList) {
                    teacherMap.put(userInfo.getId(), userInfo);
                }
                for (Teacher teacher : teacherList) {
                    TeacherVO teacherVO = new TeacherVO(teacher, (UserInfo) teacherMap.get(teacher.getId()));
                    teacherVOList.add(teacherVO);
                }
            }
            HashMap data = new HashMap();
            data.put("list", teacherVOList);
            data.put("total", total);
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
    @RequestMapping(value = "/deleteTeacher", method = RequestMethod.POST)
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

    @ApiOperation("获取教师加入的口令")
    @ResponseBody
    @RequestMapping(value = "/keyt", method = RequestMethod.POST)
    public BaseResult Keyt(HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo userInfo = Ums.getUser(request);
        try {
            JedisAdapter jedisAdapter = new JedisAdapter();
            String keyt = jedisAdapter.get(RedisKeyUtil.getKeytTeacher(userInfo.getId()));
            if (keyt == null) {
                result.setCode(3);
                return result;
            }
            int timeSecond = (int) jedisAdapter.ttl(keyt);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, timeSecond);   //2小时之后的时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
            String time = sdf.format(calendar.getTime());
            Map data = new HashMap();
            data.put("keyt", keyt);
            data.put("time", time);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取教师加入的口令");
            return result;
        }
    }

    @ApiOperation("更新教师加入的口令")
    @ResponseBody
    @RequestMapping(value = "/updateKeyt", method = RequestMethod.POST)
    public BaseResult updateKeyt(@RequestParam(value = "hours", defaultValue = "0") int hours,
                                 HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo userInfo = Ums.getUser(request);
        try {
            JedisAdapter jedisAdapter = new JedisAdapter();
            //删除旧的口令,避免被利用
            String oldKeyt = jedisAdapter.get(RedisKeyUtil.getKeytTeacher(userInfo.getId()));
            if (oldKeyt != null) {
                jedisAdapter.delete(oldKeyt);
                jedisAdapter.delete(RedisKeyUtil.getKeytTeacher(userInfo.getId()));
            }
            String keyt = IcanUtil.getStrNumRandom(6);
            //先查一下，避免口令有冲突
            String userIdStr = jedisAdapter.get(keyt);
            while (userIdStr != null) {
                keyt = IcanUtil.getStrNumRandom(6);
                userIdStr = jedisAdapter.get(keyt);
            }
            //这个口令可以用
            jedisAdapter.set(keyt, userInfo.getId() + "", 3600 * hours);
            jedisAdapter.set(RedisKeyUtil.getKeytTeacher(userInfo.getId()), keyt, 3600 * hours);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, 3600 * hours);   //2小时之后的时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
            String time = sdf.format(calendar.getTime());
            Map data = new HashMap();
            data.put("keyt", keyt);
            data.put("time", time);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("更新教师加入的口令");
            return result;
        }
    }

    @ApiOperation("删除教师加入的口令")
    @ResponseBody
    @RequestMapping(value = "/deleteKeyt", method = RequestMethod.POST)
    public BaseResult deleteKeyt(HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo userInfo = Ums.getUser(request);
        try {
            JedisAdapter jedisAdapter = new JedisAdapter();
            String keyt = jedisAdapter.get(RedisKeyUtil.getKeytTeacher(userInfo.getId()));
            jedisAdapter.delete(keyt);
            jedisAdapter.delete(RedisKeyUtil.getKeytTeacher(userInfo.getId()));
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("删除教师加入的口令异常", e);
            return result;
        }
    }
}
