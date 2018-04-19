package com.ican.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.ican.config.Constant;
import com.ican.domain.AuthPhoto;
import com.ican.domain.SchoolAppeal;
import com.ican.domain.UserInfo;
import com.ican.service.AuthPhotoService;
import com.ican.service.UserInfoService;
import com.ican.to.SchoolTO;
import com.ican.to.StudentTO;
import com.ican.to.TeacherTO;
import com.ican.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

@Api("注册Controller")
@Controller
@RequestMapping("/register")
public class RegisterController {
    private final static Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @RequestMapping(value = {"/schoolRegister"}, method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response) {
        return "register/school_register";
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

    @ApiOperation("获取邮箱验证码")
    //@RequestMapping(value = "/getEmailVerCode", method = RequestMethod.POST)
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
            //校验一下邮箱师范已经存在了
            if (!isAccountValid(email, role)) {
                result.setMsg("邮箱已被注册");
                return result;
            }
            //这里应该对频繁调用邮箱验证码的行为有限制，三次就得等隔天了，用redis来做控制
            JedisAdapter jedisAdapter = new JedisAdapter();
            String emailVerCodeNumStr = jedisAdapter.get(RedisKeyUtil.getEmailVer(email));
            if (emailVerCodeNumStr == null) {
                jedisAdapter.set(RedisKeyUtil.getEmailVer(email),"1",43200);
            } else {
                int emailVerCodeNum = Integer.valueOf(emailVerCodeNumStr);
                if (emailVerCodeNum > 3) {
                    //第三次调用邮箱验证码功能了
                    result.setMsg("你请求验证码超过三次了，请12个小时后再使用该邮箱注册");
                    Map data = new HashMap();
                    data.put("num", emailVerCodeNum);
                    BaseResultUtil.setSuccess(result, data);
                    return result;
                }
                jedisAdapter.set(RedisKeyUtil.getEmailVer(email), emailVerCodeNum + 1 + "");
            }
            String code = getRandom();
            MailSender mailSender = new MailSender();
            mailSender.send(email, "【ICAN】打开链接完成校验：http://localhost:8001/register/schoolRegister");
            BaseResultUtil.setSuccess(result, code);
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

    //获取随机六位验证码
    public static String getRandom() {
        String num = "";
        for (int i = 0 ; i < 6 ; i ++) {
            num = num + String.valueOf((int) Math.floor(Math.random() * 9 + 1));
        }
        return num;
    }

    //随机获取数字和字母组成特定长度的字符串
    public static String getStrNumRandom(Integer length) {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            boolean b = random.nextBoolean();
            if (b) { // 字符串
                int choice = random.nextBoolean() ? 65 : 97; //取得65大写字母还是97小写字母
                str.append((char) (choice + random.nextInt(26)));// 取得大写字母
            } else { // 数字
                str.append(random.nextInt(10));
            }
        }
        return str.toString();
    }

}
