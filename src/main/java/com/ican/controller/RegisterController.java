package com.ican.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.service.AuthPhotoService;
import com.ican.service.UserInfoService;
import com.ican.to.SchoolTO;
import com.ican.to.StudentTO;
import com.ican.to.TeacherTO;
import com.ican.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;
import java.util.regex.Pattern;

@Api("注册Controller")
@Controller
@RequestMapping("/register")
public class RegisterController {
    private final static Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @RequestMapping(value = {"/schoolRegister"}, method = RequestMethod.GET)
    public String schoolRegister(HttpServletRequest request, HttpServletResponse response) {
        return "register/school_register";
    }

    @RequestMapping(value = {"/teacherRegister"}, method = RequestMethod.GET)
    public String teacherRegister(HttpServletRequest request, HttpServletResponse response) {
        return "register/teacher_register";
    }

    @RequestMapping(value = {"/studentRegister"}, method = RequestMethod.GET)
    public String studentRegister(HttpServletRequest request, HttpServletResponse response) {
        return "register/student_register";
    }


    @ApiOperation("保存学校的注册")
    @RequestMapping(value = "/schoolSave", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult schoolSave(@Valid SchoolTO schoolTO,
                                HttpServletRequest request, HttpServletResponse response,
                                BindingResult bindingBesult){
        BaseResult result = BaseResultUtil.initResult();
        if (bindingBesult.hasErrors()) {
            result.setMsg(bindingBesult.getFieldError().getDefaultMessage());
            return result;
        }
        try {
            //校验邮箱验证码
            JedisAdapter jedisAdapter = new JedisAdapter();
            JSONObject jsonObject = JSONObject.parseObject(jedisAdapter.get(RedisKeyUtil.getEmailVer(schoolTO.getEmail())));
            if (!schoolTO.getCode().equals(jsonObject.getString("code"))) {
                result.setMsg("验证码错误");
                return result;
            }
            if (!isAccountValid(schoolTO.getPhone(), UserInfoService.USER_SCHOOL) || !isAccountValid(schoolTO.getEmail(), UserInfoService.USER_SCHOOL)) {
                result.setMsg("手机或邮箱已被注册");
                return result;
            }
            int id = Constant.ServiceFacade.getSchoolService().save(schoolTO);
            saveAuthPhoto(schoolTO.getId(), schoolTO.getAuth1(), AuthPhotoService.TYPE_SCHOOL);
            saveAuthPhoto(schoolTO.getId(), schoolTO.getAuth2(), AuthPhotoService.TYPE_SCHOOL);
            if (id > 0) {
                BaseResultUtil.setSuccess(result, null);
                return result;
            }
            return result;
        } catch (Exception e) {
            logger.error("保存学校的注册异常", e);
            return result;
        }
    }

    @ApiOperation("保存教师的注册")
    @RequestMapping(value = "/teacherSave", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult teacherSave(@Valid TeacherTO teacherTO,
                                  HttpServletRequest request, HttpServletResponse response,
                                  BindingResult bindingBesult){
        BaseResult result = BaseResultUtil.initResult();
        if (bindingBesult.hasErrors()) {
            result.setMsg(bindingBesult.getFieldError().getDefaultMessage());
            return result;
        }
        try {
            //校验邮箱验证码
            JedisAdapter jedisAdapter = new JedisAdapter();
            JSONObject jsonObject = JSONObject.parseObject(jedisAdapter.get(RedisKeyUtil.getEmailVer(teacherTO.getEmail())));
            if (!teacherTO.getCode().equals(jsonObject.getString("code"))) {
                result.setMsg("验证码错误");
                return result;
            }
            //校验邀请码是否还正确
            String schoolIdStr = jedisAdapter.get(teacherTO.getKeyt());
            if (schoolIdStr == null) {
                result.setMsg("邀请码有误");
                return result;
            } else {
                if (Integer.valueOf(schoolIdStr) != teacherTO.getSchoolId()) {
                    result.setMsg("邀请码有误");
                    return result;
                }
            }
            if (!isAccountValid(teacherTO.getPhone(), UserInfoService.USER_SCHOOL) || !isAccountValid(teacherTO.getEmail(), UserInfoService.USER_SCHOOL)) {
                result.setMsg("手机或邮箱已被注册");
                return result;
            }
            int id = Constant.ServiceFacade.getTeacherService().save(teacherTO);
            saveAuthPhoto(teacherTO.getId(), teacherTO.getAuth1(), AuthPhotoService.TYPE_TEACHER);
            saveAuthPhoto(teacherTO.getId(), teacherTO.getAuth2(), AuthPhotoService.TYPE_TEACHER);
            if (id > 0) {
                BaseResultUtil.setSuccess(result, null);
                return result;
            }
            return result;
        } catch (Exception e) {
            logger.error("保存教师的注册异常", e);
            return result;
        }
    }

    @ApiOperation("保存学生的注册")
    @RequestMapping(value = "/studentSave", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult studentSave(@Valid StudentTO studentTO,
                                  HttpServletRequest request, HttpServletResponse response,
                                  BindingResult bindingResult){
        BaseResult result = BaseResultUtil.initResult();
        if (bindingResult.hasErrors()) {
            result.setMsg(bindingResult.getFieldError().getDefaultMessage());
            return result;
        }
        try {
            //校验邮箱验证码
            JedisAdapter jedisAdapter = new JedisAdapter();
            JSONObject jsonObject = JSONObject.parseObject(jedisAdapter.get(RedisKeyUtil.getEmailVer(studentTO.getEmail())));
            if (!studentTO.getCode().equals(jsonObject.getString("code"))) {
                result.setMsg("验证码错误");
                return result;
            }
            //校验邀请码是否还正确
            String collegeIdStr = jedisAdapter.get(studentTO.getKeyt());
            if (collegeIdStr == null) {
                result.setMsg("邀请码有误");
                return result;
            } else {
                if (Integer.valueOf(collegeIdStr) != studentTO.getCollegeId()) {
                    result.setMsg("邀请码有误");
                    return result;
                }
            }
            if (!isAccountValid(studentTO.getPhone(), UserInfoService.USER_SCHOOL) || !isAccountValid(studentTO.getEmail(), UserInfoService.USER_SCHOOL)) {
                result.setMsg("手机或邮箱已被注册");
                return result;
            }
            int id = Constant.ServiceFacade.getStudentService().save(studentTO);
            saveAuthPhoto(studentTO.getId(), studentTO.getAuth1(), AuthPhotoService.TYPE_STUDENT);
            saveAuthPhoto(studentTO.getId(), studentTO.getAuth2(), AuthPhotoService.TYPE_STUDENT);
            if (id > 0) {
                BaseResultUtil.setSuccess(result, null);
                return result;
            }
            return result;
        } catch (Exception e) {
            logger.error("保存学生的注册异常", e);
            return result;
        }
    }

    @RequestMapping(value = "/schoolAppeal")
    public String schoolAppeal() {
        return "/register/school_appeal";
    }

    @ApiOperation("保存申议信息")
    @ResponseBody
    @RequestMapping(value = "/schoolAppeal/save", method = RequestMethod.POST)
    public BaseResult save(SchoolAppeal schoolAppeal,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (schoolAppeal == null) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            //只能新增加
            schoolAppeal.setId(0);
            Constant.ServiceFacade.getSchoolAppealService().save(schoolAppeal);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("保存申议信息异常", e);
            return result;
        }
    }

    @ApiOperation("教师校验注册码")
    @ResponseBody
    @RequestMapping(value = "/teacherVerKeyt", method = RequestMethod.POST)
    public BaseResult teacherVerKeyt(@RequestParam(value = "keyt",required = true) String keyt,
                                     HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            JedisAdapter jedisAdapter = new JedisAdapter();
            String schoolIdStr = jedisAdapter.get(keyt);
            if (schoolIdStr == null) {
                result.setMsg("注册码过期或不存在");
                return result;
            }
            int schoolId = Integer.valueOf(schoolIdStr);
            List<College> collegeList = Constant.ServiceFacade.getCollegeService().list(null, schoolId, null, 1, 200);
            List<UserInfo> collegeInfoList = new ArrayList<>();
            Set<String> collegeSet = new HashSet<>();
            for (College college : collegeList) {
                collegeSet.add(college.getId() + "");
            }
            String collegeIds = String.join(",", collegeSet);
            if (!StringUtils.isEmpty(collegeIds)) {
                collegeInfoList = Constant.ServiceFacade.getUserInfoService().list(collegeIds, null, null, 0, null, 1, 200);
            }
            List<Department> departmentList = Constant.ServiceFacade.getDepartmentService().list(null, schoolId, 0, null, 1, 1000);
            Map data = new HashMap();
            data.put("collegeList", collegeInfoList);
            data.put("departmentList", departmentList);
            data.put("schoolId", schoolId);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("教师校验注册码异常", e);
            return result;
        }
    }

    @ApiOperation("学生校验注册码")
    @ResponseBody
    @RequestMapping(value = "/studentVerKeyt", method = RequestMethod.POST)
    public BaseResult studentVerKeyt(@RequestParam(value = "keyt",required = true) String keyt,
                                     HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            JedisAdapter jedisAdapter = new JedisAdapter();
            String collegeIdStr = jedisAdapter.get(keyt);
            if (collegeIdStr == null) {
                result.setMsg("注册码过期或不存在");
                return result;
            }
            int collegeId = Integer.valueOf(collegeIdStr);
            College college = Constant.ServiceFacade.getCollegeService().select(collegeId);
            List<Department> departmentList = Constant.ServiceFacade.getDepartmentService().list(null, 0, collegeId, null, 1, 100);
            List<Major> majorList = Constant.ServiceFacade.getMajorService().list(null, 0, collegeId, 0, 0, null, 1, 100);
            List<Clazz> clazzList = Constant.ServiceFacade.getClazzService().list(null, 0, collegeId, 0, 0, 0, null, 1, 300);
            Map data = new HashMap();
            data.put("collegeId", collegeId);
            data.put("schoolId", college.getSchoolId());
            data.put("departmentList", departmentList);
            data.put("majorList", majorList);
            data.put("clazzList", clazzList);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("学生校验注册码异常", e);
            return result;
        }
    }

    @ApiOperation("获取邮箱验证码")
    @RequestMapping(value = "/getEmailVerCode", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getEmailVerCode(@RequestParam("email") String email,
                                      @RequestParam("role") int role){
        BaseResult result = BaseResultUtil.initResult();
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            if (!Pattern.matches(check, email)) {
                result.setMsg("邮箱格式不正确");
                return result;
            }
            //校验一下邮箱师是否已经存在了
            if (!isAccountValid(email, role)) {
                result.setMsg("邮箱已被注册");
                return result;
            }
            //这里应该对频繁调用邮箱验证码的行为有限制，三次就得等隔天了，用redis来做控制
            JedisAdapter jedisAdapter = new JedisAdapter();
            String code = IcanUtil.getRandom();
            JSONObject emailVerObj = new JSONObject();
            emailVerObj.put("code", code);
            String emailVerCodeNumStr = jedisAdapter.get(RedisKeyUtil.getEmailVer(email));
            if (emailVerCodeNumStr == null) {
                emailVerObj.put("num", 1);
                jedisAdapter.set(RedisKeyUtil.getEmailVer(email), emailVerObj.toJSONString(),14400);
            } else {
                JSONObject jsonObject = JSONObject.parseObject(emailVerCodeNumStr);
                int emailVerCodeNum = (Integer)jsonObject.get("num");
                if (emailVerCodeNum > 3) {
                    //第三次调用邮箱验证码功能了
                    Map data = new HashMap();
                    data.put("num", emailVerCodeNum);
                    BaseResultUtil.setSuccess(result, "你请求验证码超过三次了，请4小时后再使用该邮箱注册", data);
                    return result;
                }
                emailVerObj.put("num", ++emailVerCodeNum);
                jedisAdapter.set(RedisKeyUtil.getEmailVer(email), emailVerObj.toJSONString(),3600);
            }
            MailSender mailSender = new MailSender();
            mailSender.send(email, "【ICAN】您的验证码是：" + code);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("获取邮箱验证码异常", e);
            result.setMsg("邮箱发送失败，请重试");
            return result;
        }
    }


    public Boolean isAccountValid(String account, int role) {
        try {
            List<UserInfo> InfoList = Constant.ServiceFacade.getUserInfoService().listByAccount(account, role);
            if (InfoList != null && InfoList.size() > 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("校验手机或邮箱是否已经存在异常", e);
            return false;
        }
    }

    public void saveAuthPhoto(int userId, String url, int type) {
        try {
            AuthPhoto authPhoto = new AuthPhoto();
            authPhoto.setType(type);
            authPhoto.setUserId(userId);
            authPhoto.setUrl(url);
            Constant.ServiceFacade.getAuthPhotoService().save(authPhoto);
        } catch (Exception e) {
            logger.error("保存认证图片异常");
        }
    }
}
