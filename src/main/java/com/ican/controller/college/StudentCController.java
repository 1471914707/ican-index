package com.ican.controller.college;

import com.ican.config.Constant;
import com.ican.controller.school.StudentSController;
import com.ican.domain.Student;
import com.ican.domain.UserInfo;
import com.ican.util.*;
import com.ican.vo.StudentVO;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("二级学院操作学生")
@Controller
@RequestMapping("/college/student")
public class StudentCController {

    private final static Logger logger = LoggerFactory.getLogger(StudentSController.class);

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public String index(@RequestParam(value = "activityId", defaultValue = "0", required = false) int activityId,
                        HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("activityId", activityId);
        return "college/student/list";
    }

    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public String detail(@RequestParam(value = "studentId", defaultValue = "0") String studentId,
                         @RequestParam(value = "activityId",defaultValue = "0",required = false) int activityId,
                         HttpServletRequest request, HttpServletResponse response){
        request.setAttribute("studentId", studentId);
        request.setAttribute("activityId", activityId);
        return "college/student/detail";
    }

    @ApiOperation("获取学生列表")
    @ResponseBody
    @RequestMapping(value = "/listJson", method = RequestMethod.GET)
    public BaseResult listJson(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "20") int size,
                               @RequestParam(value = "departmentId", defaultValue = "0") int departmentId,
                               @RequestParam(value = "clazzId", defaultValue = "0") int clazzId,
                               @RequestParam(value = "teacherId", defaultValue = "0") int teacherId,
                               @RequestParam(value = "current", defaultValue = "0") int current,
                               @RequestParam(value = "jobId", defaultValue = "",required = false) String jobId,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            List<Student> studentList = Constant.ServiceFacade.getStudentService().list(null, 0,
                    self.getId(), departmentId, clazzId, teacherId, current, jobId, "current desc,id desc", page, size);
            List<StudentVO> studentVOList = Constant.ServiceFacade.getStudentWebService().listVO(studentList);
            int total = Constant.ServiceFacade.getStudentService().count(null, 0,
                    self.getId(), departmentId, clazzId, teacherId, current, jobId);
            Map data = new HashMap();
            data.put("list", studentVOList);
            data.put("total", total);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取学生列表异常", e);
            return result;
        }
    }

    @ApiOperation("获取学生详情")
    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public BaseResult info(@RequestParam(value = "id", defaultValue = "0") int id,
                           @RequestParam(value = "activityId", defaultValue = "0") int activityId,
                           HttpServletRequest request, HttpServletResponse response){
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            //基本信息
            Student student = Constant.ServiceFacade.getStudentService().select(id);
            if (student == null || student.getCollegeId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            StudentVO studentVO = Constant.ServiceFacade.getStudentWebService().studentVO(student, activityId);
            BaseResultUtil.setSuccess(result, studentVO);
            return result;
        } catch (Exception e) {
            logger.error("获取学生详情异常", e);
            return result;
        }
    }

    @ApiOperation("获取学生加入的口令")
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
            logger.error("获取学生加入的口令");
            return result;
        }
    }

    @ApiOperation("更新学生加入的口令")
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
            calendar.add(Calendar.SECOND, 3600 * hours);   //hours小时之后的时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
            String time = sdf.format(calendar.getTime());
            Map data = new HashMap();
            data.put("keyt", keyt);
            data.put("time", time);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("更新学生加入的口令");
            return result;
        }
    }

    @ApiOperation("删除学生加入的口令")
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
            logger.error("删除学生加入的口令异常", e);
            return result;
        }
    }
}
