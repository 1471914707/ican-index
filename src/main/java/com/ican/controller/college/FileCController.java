package com.ican.controller.college;


import com.ican.config.Constant;
import com.ican.domain.Activity;
import com.ican.domain.File;
import com.ican.domain.UserInfo;
import com.ican.service.FileService;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("文件")
@Controller
@RequestMapping("/college/file")
public class FileCController {
    private final static Logger logger = LoggerFactory.getLogger(FileCController.class);

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public String list(@RequestParam("activityId") String activityId,
                       HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("activityId", activityId);
        return "file/list";
    }

    @ApiOperation("二级学院过程文档")
    @RequestMapping(value = "/listJson", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listJson(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "20") int size,
                               @RequestParam("activityId") int activityId,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            List<File> fileList = Constant.ServiceFacade.getFileService().list(null, self.getId(), activityId, FileService.FILE_TYPE_ACTIVITY, "id desc", page, size);
            int total = Constant.ServiceFacade.getFileService().count(null, self.getId(), activityId, FileService.FILE_TYPE_ACTIVITY);
            Map data = new HashMap<>();
            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            data.put("list", fileList);
            data.put("total", total);
            data.put("activity", activity);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("二级学院过程文档异常", e);
            return result;
        }
    }

    @ApiOperation("二级学院保存共享文档")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult save(@RequestParam(value = "activityId", required = true) int activityId,
                           @RequestParam(value = "name", required = true) String name,
                           @RequestParam(value = "url", required = true) String url,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (activityId <= 0 || StringUtils.isEmpty(name) || StringUtils.isEmpty(url)) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        UserInfo self = Ums.getUser(request);
        try {
            File file = new File();
            file.setUrl(url);
            file.setName(name);
            file.setTargetId(activityId);
            file.setType(FileService.FILE_TYPE_ACTIVITY);
            file.setUserId(self.getId());
            Constant.ServiceFacade.getFileService().save(file);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("二级学院保存共享文档异常", e);
            return result;
        }
    }

    @ApiOperation("二级学院删除共享文档")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult delete(@RequestParam("id") int id,
                             HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (id <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        UserInfo self = Ums.getUser(request);
        try {
            File file = Constant.ServiceFacade.getFileService().select(id);
            if (file == null || file.getUserId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Constant.ServiceFacade.getFileService().delete(id);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("二级学院删除共享文档异常", e);
            return result;
        }
    }
}
