package com.ican.controller;

import com.ican.config.BaseConfig;
import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.service.UserInfoService;
import com.ican.service.UserService;
import com.ican.util.*;
import com.ican.vo.StudentVO;
import com.ican.vo.TeacherVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * 基本数据接口
 * autohor: lin jiayu
 * 2018-02-16 16：:13
 */
@Api("基本数据接口")
@RestController
public class DataController {
    private final static Logger logger = LoggerFactory.getLogger(DataController.class);

    @Autowired
    private BaseConfig baseConfig;

    @ApiOperation(value = "上传图片", notes = "")
    @ResponseBody
    @RequestMapping(value = "/photoUpload", method = RequestMethod.POST)
    public BaseResult photoUpload(@RequestParam("file") MultipartFile file,
                                  HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            String time = DateUtil.getCurrentYM();
            if (!file.isEmpty()) {
                File normalFile = new File(file.getOriginalFilename());
                String ext = normalFile.getName().substring(normalFile.getName().lastIndexOf(".") + 1);
                String filename = System.currentTimeMillis() + "." + ext;
                String filePath = baseConfig.getBasePath() + "/" + time;
                File path = new File(filePath);
                if (!path.exists()) {
                    path.mkdirs();
                }
                String dist = filePath + "/" + filename;
                String url = baseConfig.getBaseUrl() + time + "/" + filename;
                File localFile = new File(dist);
                FileUtil.writIn(file.getInputStream(), dist);
                BaseResultUtil.setSuccess(result, url);
                return result;
            }
            return result;
        } catch (Exception e) {
            logger.error("上传图片异常： ", e);
            e.printStackTrace();
            return result;
        }
    }

    @ApiOperation(value = "上传图片", notes = "")
    @ResponseBody
    @RequestMapping(value = "/docUpload", method = RequestMethod.POST)
    public BaseResult docUpload(@RequestParam("file") MultipartFile file,
                                HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            String time = DateUtil.getCurrentYM();
            if (!file.isEmpty()) {
                File normalFile = new File(file.getOriginalFilename());
                String ext = normalFile.getName().substring(normalFile.getName().lastIndexOf(".") + 1);
                String filename = IcanUtil.getStrNumRandom(32) + System.currentTimeMillis() + "." + ext;
                String filePath = baseConfig.getBaseDocPath() + "/" + time;
                File path = new File(filePath);
                if (!path.exists()) {
                    path.mkdirs();
                }
                String dist = filePath + "/" + filename;
                String url = baseConfig.getBaseDocUrl() + time + "/" + filename;
                File localFile = new File(dist);
                FileUtil.writIn(file.getInputStream(), dist);
                BaseResultUtil.setSuccess(result, url);
                return result;
            }
            return result;
        } catch (Exception e) {
            logger.error("上传图片异常： ", e);
            e.printStackTrace();
            return result;
        }
    }

    @ApiOperation("获取所有国家省份城市数据接口")
    @RequestMapping(value = "/allCityJson", method = RequestMethod.GET)
    public BaseResult allCityJson() {
        BaseResult result = BaseResultUtil.initResult();
        try {
            List<City> cityList = Constant.ServiceFacade.getCityService().list(-1, -1, null, 1, 10000);
            return BaseResultUtil.setSuccess(result, cityList);
        } catch (Exception e) {
            logger.error("获取所有国家省份城市数据接口", e);
            return result;
        }
    }

    @ApiOperation("获取国家数据接口")
    @RequestMapping(value = "/countryJson", method = RequestMethod.GET)
    public BaseResult countryJson() {
        BaseResult result = BaseResultUtil.initResult();
        try {
            List<City> cityList = Constant.ServiceFacade.getCityService().list(0, 1, null, 1, 250);
            return BaseResultUtil.setSuccess(result, cityList);
        } catch (Exception e) {
            logger.error("获取国家数据出错", e);
            return result;
        }
    }

    @ApiOperation("获取相关省份数据接口")
    @RequestMapping(value = "/provinceJson/{parentId}", method = RequestMethod.GET)
    public BaseResult provinceJson(@PathVariable("parentId") int parentId) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            List<City> cityList = Constant.ServiceFacade.getCityService().list(parentId, 2, null, 1, 250);
            return BaseResultUtil.setSuccess(result, cityList);
        } catch (Exception e) {
            logger.error("获取相关省份数出错", e);
            return result;
        }
    }

    @ApiOperation("获取相关城市数据接口")
    @RequestMapping(value = "/cityJson/{parentId}", method = RequestMethod.GET)
    public BaseResult cityJson(@PathVariable("parentId") int parentId) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            List<City> cityList = Constant.ServiceFacade.getCityService().list(parentId, 3, null, 1, 250);
            return BaseResultUtil.setSuccess(result, cityList);
        } catch (Exception e) {
            logger.error("获取相关城市数出错", e);
            return result;
        }
    }

    @ApiOperation("获取本校的二级学院")
    @RequestMapping(value = "/collegeListJson",method = RequestMethod.GET)
    public BaseResult collegeListJson(HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (self == null) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            List<College> collegeList = Constant.ServiceFacade.getCollegeService().list(null, self.getId(), null, 1, 1000);
            BaseResultUtil.setSuccess(result, collegeList);
            return result;
        } catch (Exception e) {
            logger.error("获取本校的二级学院列表异常", e);
            return result;
        }
    }

    @ApiOperation("获取本校本院的系类")
    @RequestMapping(value = "/departmentListJson",method = RequestMethod.GET)
    public BaseResult departmentListJson(@RequestParam(value = "collegeId",required = false) int collegeId,
                                         HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (self == null || collegeId <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            if (self.getRole() == UserInfoService.USER_COLLEGE) {
                collegeId = self.getId();
            }
            List<Department> departmentList = Constant.ServiceFacade.getDepartmentService().list(null, 0, collegeId, null, 1, 1000);
            BaseResultUtil.setSuccess(result, departmentList);
            return result;
        } catch (Exception e) {
            logger.error("获取本校本院的系类列表异常", e);
            return result;
        }
    }

    @ApiOperation("根据活动获取本校本院的系类")
    @RequestMapping(value = "/departmentListJson2",method = RequestMethod.GET)
    public BaseResult departmentListJson2(@RequestParam(value = "activityId") int activityId,
                                          HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (self == null || activityId <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            if (activity == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            List<Department> departmentList = Constant.ServiceFacade.getDepartmentService().list(null, 0, activity.getUserId(), null, 1, 1000);
            BaseResultUtil.setSuccess(result, departmentList);
            return result;
        } catch (Exception e) {
            logger.error("根据活动获取本校本院的系类列表异常", e);
            return result;
        }
    }

    @ApiOperation("获取本院的系类")
    @RequestMapping(value = "/departmentListJson3",method = RequestMethod.GET)
    public BaseResult departmentListJson3(HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (self == null) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            List<Department> departmentList = Constant.ServiceFacade.getDepartmentService().list(null, 0, self.getId(), null, 1, 1000);
            BaseResultUtil.setSuccess(result, departmentList);
            return result;
        } catch (Exception e) {
            logger.error("根据活动获取本校本院的系类列表异常", e);
            return result;
        }
    }

    @ApiOperation("获取本系的专业")
    @RequestMapping(value = "/majorListJson",method = RequestMethod.GET)
    public BaseResult majorListJson(@RequestParam(value = "departmentId",required = false) int departmentId,
                                    HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (self == null || departmentId <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            List<Major> majorList = Constant.ServiceFacade.getMajorService().list(null, 0, 0, departmentId, 0, null, 1, 1000);
            BaseResultUtil.setSuccess(result, majorList);
            return result;
        } catch (Exception e) {
            logger.error("获取本系的专业异常", e);
            return result;
        }
    }


    @ApiOperation("根据活动获取专业")
    @RequestMapping(value = "/majorListJson2",method = RequestMethod.GET)
    public BaseResult majorListJson2(@RequestParam(value = "activityId") int activityId,
                                     HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (self == null || activityId <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            if (activity == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            List<Major> majorList = Constant.ServiceFacade.getMajorService().list(null, 0, activity.getUserId(), 0, 0, null, 1, 1000);
            BaseResultUtil.setSuccess(result, majorList);
            return result;
        } catch (Exception e) {
            logger.error("根据活动获取专业异常", e);
            return result;
        }
    }

    @ApiOperation("获取本校本院本系的班级")
    @RequestMapping(value = "/clazzListJson",method = RequestMethod.GET)
    public BaseResult clazzListJson(@RequestParam(value = "departmentId",required = false) int departmentId,
                                    @RequestParam(value = "majorId",required = false) int majorId,
                                    @RequestParam(value = "current",required = false) int current,
                                    HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (self == null) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            List<Clazz> clazzList = Constant.ServiceFacade.getClazzService().list(null, 0, 0, departmentId,
                    majorId, current, null, 1, 1000);
            BaseResultUtil.setSuccess(result, clazzList);
            return result;
        } catch (Exception e) {
            logger.error("获取本校本院本系的班级列表异常", e);
            return result;
        }
    }

    @ApiOperation("获取本院的教师")
    @RequestMapping(value = "/teacherListJson",method = RequestMethod.GET)
    public BaseResult teacherListJson(HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (self == null || self.getRole() != UserInfoService.USER_COLLEGE) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            HashMap data = Constant.ServiceFacade.getTeacherWebService().listVO(0, self.getId(), 0, null, 1, 2000);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取本院的教师异常", e);
            return result;
        }
    }

    @ApiOperation("获取本院的某届所有学生")
    @RequestMapping(value = "/studentListJson2",method = RequestMethod.GET)
    public BaseResult studentListJson2(@RequestParam(value = "activityId") int activityId,
                                      HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (self == null || activityId <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            if (activity == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            List<Student> studentList = Constant.ServiceFacade.getStudentService().list(null, 0, activity.getUserId(), 0, 0, 0, 0, null, null, 1, 2500);
            List<StudentVO> studentVOList = Constant.ServiceFacade.getStudentWebService().listVO(studentList);
            BaseResultUtil.setSuccess(result, studentVOList);
            return result;
        } catch (Exception e) {
            logger.error("获取本院的某届所有学生异常", e);
            return result;
        }
    }

    @ApiOperation("获取该活动下所有的项目")
    @RequestMapping(value = "/projectListJson2",method = RequestMethod.GET)
    public BaseResult projectListJson2(@RequestParam(value = "activityId") int activityId,
                                       HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (self == null || activityId <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            if (activity == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            List<Project> projectList = Constant.ServiceFacade.getProjectService().list(null, activityId, 0, 0, 0, 0, 0, 0,
                    0, null, 0, null, 1, 2000);

            BaseResultUtil.setSuccess(result, projectList);
            return result;
        } catch (Exception e) {
            logger.error("获取该活动下所有的项目异常", e);
            return result;
        }
    }

    @ApiOperation("获取本院的教师特定数据")
    @RequestMapping(value = "/teacherListJson2",method = RequestMethod.GET)
    public BaseResult teacherListJson2(@RequestParam(value = "activityId") int activityId,
                                       HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (self == null || self.getRole() != UserInfoService.USER_COLLEGE) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            if (activity == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            HashMap data = Constant.ServiceFacade.getTeacherWebService().listVO(0, activity.getUserId(), 0, null, 1, 2000);
            List<TeacherVO> teacherVOList = (List<TeacherVO>) data.get("list");
            if (teacherVOList != null && teacherVOList.size() > 0) {

            }
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取本院的教师异常", e);
            return result;
        }
    }



}
