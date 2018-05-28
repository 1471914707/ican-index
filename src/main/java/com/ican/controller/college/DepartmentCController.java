package com.ican.controller.college;

import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("二级学院操作系、专业和班级")
@Controller
@RequestMapping("/college/department")
public class DepartmentCController {
    private final static Logger logger = LoggerFactory.getLogger(DepartmentCController.class);

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response) {
        return "/college/department/list";
    }

    @ApiOperation("获取系部-专业-班级信息")
    @RequestMapping(value = "/allListJson",method = RequestMethod.GET)
    @ResponseBody
    public BaseResult addListJson(HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            int departmentTotal = Constant.ServiceFacade.getDepartmentService().count(null, 0, self.getId());
            List<Department> departmentList = Constant.ServiceFacade.getDepartmentService().list(null, 0, self.getId(), null, 1, departmentTotal);
            int majorTotal = Constant.ServiceFacade.getMajorService().count(null, 0, self.getId(), 0, 0);
            List<Major> majorList = Constant.ServiceFacade.getMajorService().list(null, 0, self.getId(), 0, 0, null, 1, majorTotal);
            int clazzTotal = Constant.ServiceFacade.getClazzService().count(null, 0, self.getId(), 0, 0, 0);
            List<Clazz> clazzList = Constant.ServiceFacade.getClazzService().list(null, 0, self.getId(), 0, 0, 0, null, 1, clazzTotal);
            Map data = new HashMap<>();
            data.put("departmentList", departmentList);
            data.put("departmentTotal", departmentTotal);
            data.put("majorList", majorList);
            data.put("majorTotal", majorTotal);
            data.put("clazzList", clazzList);
            data.put("clazzTotal", clazzTotal);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取系部、专业、班级异常", e);
            return result;
        }
    }

    @ApiOperation("保存系部信息")
    @RequestMapping(value = "/departmentSave",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult departmentSave(Department department,
                                     HttpServletRequest request, HttpServletResponse response){
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            if (department.getCollegeId() <= 0) {
                department.setCollegeId(self.getId());
            }
            if (department.getSchoolId() <= 0){
                College college = Constant.ServiceFacade.getCollegeService().select(self.getId());
                department.setSchoolId(college.getSchoolId());
            }
            Constant.ServiceFacade.getDepartmentService().save(department);
            BaseResultUtil.setSuccess(result, department);
            return result;
        } catch (Exception e) {
            logger.error("保存系部信息异常", e);
            return result;
        }
    }

    @ApiOperation("删除系部信息")
    @RequestMapping(value = "/departmentDelete",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult departmentDelete(@RequestParam(value = "id",required = true) int id,
                                       HttpServletRequest request, HttpServletResponse response){
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            //删除系部之前应该确定该系部下是否还有专业，还有专业就不能删除
            int majorTotal = Constant.ServiceFacade.getMajorService().count(null, 0, 0, id, 0);
            if (majorTotal > 0) {
                result.setMsg("删除系部异常，该系部下还没有专业转移");
                return result;
            }
            //验证之后就可以删除
            Department department = Constant.ServiceFacade.getDepartmentService().select(id);
            if (department == null || department.getCollegeId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Constant.ServiceFacade.getDepartmentService().delete(id);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("删除系部信息异常", e);
            return result;
        }
    }

    //保存专业
    @ApiOperation("保存专业信息")
    @RequestMapping(value = "/majorSave",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult majorSave(Major major,
                                HttpServletRequest request, HttpServletResponse response){
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            if (major.getCollegeId() <= 0) {
                major.setCollegeId(self.getId());
                College college = Constant.ServiceFacade.getCollegeService().select(self.getId());
                major.setSchoolId(college.getSchoolId());
            }
            Constant.ServiceFacade.getMajorService().save(major);
            BaseResultUtil.setSuccess(result, major);
            return result;
        } catch (Exception e) {
            logger.error("保存专业信息异常", e);
            return result;
        }
    }

    //删除专业
    @ApiOperation("删除专业信息")
    @RequestMapping(value = "/majorDelete",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult majorDelete(@RequestParam(value = "id",required = true) int id,
                                  HttpServletRequest request, HttpServletResponse response){
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            //删除专业之前应该确定该专业下是否还有班级，还有班级就不能删除
            int clazzTotal = Constant.ServiceFacade.getClazzService().count(null, 0, 0, 0, id, 0);
            if (clazzTotal > 0) {
                result.setMsg("删除专业异常，该系部下还没有班级转移");
                return result;
            }
            Major major = Constant.ServiceFacade.getMajorService().select(id);
            if (major == null || major.getCollegeId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Constant.ServiceFacade.getMajorService().delete(id);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("删除专业信息异常", e);
            return result;
        }
    }

    //保存班级
    @ApiOperation("保存班级信息")
    @RequestMapping(value = "/clazzSave",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult clazzSave(Clazz clazz,
                                HttpServletRequest request, HttpServletResponse response){
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            if (clazz.getCollegeId() <= 0) {
                clazz.setCollegeId(self.getId());
            }
            if (clazz.getSchoolId() <= 0) {
                College college = Constant.ServiceFacade.getCollegeService().select(self.getId());
                clazz.setSchoolId(college.getSchoolId());
            }
            clazz.setAmount(0);
            Constant.ServiceFacade.getClazzService().save(clazz);
            BaseResultUtil.setSuccess(result, clazz);
            return result;
        } catch (Exception e) {
            logger.error("保存班级信息异常", e);
            return result;
        }
    }

    //删除班级
    @ApiOperation("删除班级信息")
    @RequestMapping(value = "/clazzDelete",method = RequestMethod.POST)
    @ResponseBody
    public BaseResult clazzDelete(@RequestParam(value = "id",required = true) int id,
                                  HttpServletRequest request, HttpServletResponse response){
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            //删除班级之前应该确定该班级下是否还有学生，还有学生就不能删除
            int studentTotal = Constant.ServiceFacade.getStudentService().count(null, 0, 0, 0, id, 0, 0, null);
            if (studentTotal > 0) {
                result.setMsg("删除班级异常，该系部下还没有学生转移");
                return result;
            }
            Clazz clazz = Constant.ServiceFacade.getClazzService().select(id);
            if (clazz == null || clazz.getCollegeId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Constant.ServiceFacade.getClazzService().delete(id);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("删除班级信息异常", e);
            return result;
        }
    }

}
