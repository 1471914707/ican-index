package com.ican.controller.school;

import com.ican.config.Constant;
import com.ican.domain.College;
import com.ican.domain.UserInfo;
import com.ican.to.CollegeTO;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.util.logging.Logger;

@Api("学校操作学院")
@Controller
@RequestMapping("/school/college")
public class CollegeController {
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(CollegeController.class);

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        return "/school/college/list";
    }

    @ApiOperation("获取二级学院列表Json")
    @RequestMapping(value = "/listJson", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult listJson(@RequestParam(value = "page",defaultValue = "1") int page,
                               @RequestParam(value = "size",defaultValue = "20") int size,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo userInfo = Ums.getUser(request);
        try {
            List<College> collegeList = Constant.ServiceFacade.getCollegeService().list(userInfo.getId(), null, null, "id desc", page, size);
            int total = Constant.ServiceFacade.getCollegeService().count(userInfo.getId(), null, null);
            Map data = new HashMap();
            data.put("list", collegeList);
            data.put("total", total);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取二级学院列表Json异常", e);
            return result;
        }
    }

    @ApiOperation("保存二级学院")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult save(CollegeTO collegeTO,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (collegeTO == null) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        UserInfo school = Ums.getUser(request);
        if (collegeTO.getSchoolId() > 0) {
            if (collegeTO.getSchoolId() != school.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
        }
        collegeTO.setSchoolId(school.getId());
        try {
            if (collegeTO.getId() > 0) {
                UserInfo userInfo = Constant.ServiceFacade.getUserInfoService().select(collegeTO.getId());
                //不能保存不存在的
                if (userInfo == null) {
                    result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                    return result;
                }
            }
            int id = Constant.ServiceFacade.getCollegeService().save(collegeTO);
            BaseResultUtil.setSuccess(result, id);
            return result;
        } catch (Exception e) {
            logger.error("保存二级学院异常", e);
            return result;
        }
    }
}
