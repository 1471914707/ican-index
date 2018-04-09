package com.ican.controller.school;

import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.service.UserInfoService;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
import com.ican.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.bcel.Const;
import org.aspectj.apache.bcel.classfile.ConstantNameAndType;
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

@Api("学校操作选题")
@Controller
@RequestMapping("/school/paper")
public class PaperSController {
    private final static Logger logger = LoggerFactory.getLogger(PaperSController.class);

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public String index() {
        return "/school/paper/list";
    }

    @ApiOperation("获取选题列表")
    @RequestMapping(value = "/listJson", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listJson(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "20") int size,
                               @RequestParam(value = "collegeId", defaultValue = "0") int collegeId,
                               @RequestParam(value = "departmentId", defaultValue = "0") int departmentId,
                               @RequestParam(value = "teacherId", defaultValue = "0") int teacherId,
                               @RequestParam(value = "current", defaultValue = "0") int current,
                               @RequestParam(value = "activityId", defaultValue = "0") int activityId,
                               @RequestParam(value = "title", defaultValue = "",required = false) String title,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            List<Paper> paperList = Constant.ServiceFacade.getPaperService().list(null, activityId, current, self.getId(), collegeId, departmentId,
                    teacherId, title, "id desc", page, size);
            List<PaperVO> paperVOList = Constant.ServiceFacade.getPaperWebService().listVO(paperList);
            int total = Constant.ServiceFacade.getPaperService().count(null, activityId, current, self.getId(), collegeId, departmentId, teacherId,
                    title);
            Map data = new HashMap();
            data.put("list", paperVOList);
            data.put("total", total);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取选题列表异常", e);
            return result;
        }
    }

    @ApiOperation("获取选题详情")
    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public BaseResult info(@RequestParam(value = "id", defaultValue = "0") int id,
                           @RequestParam(value = "activityId", defaultValue = "0") int activityId,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            Paper paper = Constant.ServiceFacade.getPaperService().select(id);
            if (paper == null || paper.getSchoolId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            PaperVO paperVO = Constant.ServiceFacade.getPaperWebService().getPaperVO(paper);
            BaseResultUtil.setSuccess(result, paperVO);
            return result;
        } catch (Exception e) {
            logger.error("获取选题详情异常", e);
            return result;
        }

    }
}
