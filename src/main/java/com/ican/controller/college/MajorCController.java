package com.ican.controller.college;

import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.service.FileService;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.tomcat.util.bcel.Const;
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

@Api("专业审核人")
@Controller
@RequestMapping("/college/major")
public class MajorCController {
    private final static Logger logger = LoggerFactory.getLogger(MajorCController.class);

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response) {
        return "college/major/list";
    }

    @ApiOperation("二级学院专业列表")
    @RequestMapping(value = "/listJson", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listJson(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "20") int size,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            List<Major> majorList = Constant.ServiceFacade.getMajorService().list(null, 0, self.getId(), 0, 0, null, page, size);
            int total = Constant.ServiceFacade.getMajorService().count(null, 0, self.getId(), 0, 0);
            Map data = new HashMap();
            data.put("list", majorList);
            data.put("total", total);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("二级学院专业列表异常", e);
            return result;
        }
    }

    @ApiOperation("二级学院专业设置专业审核人")
    @RequestMapping(value = "/teacher", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult teacher(@RequestParam(value = "majorId", defaultValue = "0") int majorId,
                              @RequestParam(value = "teacherId", defaultValue = "0") int teacherId,
                              HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (majorId <= 0 || teacherId <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        UserInfo self = Ums.getUser(request);
        try {
            Major major = Constant.ServiceFacade.getMajorService().select(majorId);
            if (major == null || major.getCollegeId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Teacher teacher = Constant.ServiceFacade.getTeacherService().select(teacherId);
            if (teacher == null){
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            major.setTeacherId(teacherId);
            Constant.ServiceFacade.getMajorService().save(major);
            BaseResultUtil.setSuccess(result, major);
            return result;
        } catch (Exception e) {
            logger.error("二级学院专业设置专业审核人异常", e);
            return result;
        }
    }

}
