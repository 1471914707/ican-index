package com.ican.controller.college;

import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.service.ArrangeService;
import com.ican.service.UserInfoService;
import com.ican.service.UserService;
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

@Api("二级学院发布流程")
@Controller
@RequestMapping("/arrange")
public class ArrangeCController {
    private final static Logger logger = LoggerFactory.getLogger(ArrangeCController.class);

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public String list(@RequestParam("activityId") String activityId,
                       @RequestParam("collegeId") String collegeId,
                       HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("activityId", activityId);
        request.setAttribute("collegeId", collegeId);
        return "arrange/list";
    }

    @ApiOperation("获取安排流程")
    @RequestMapping(value = "/listJson", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listJson(@RequestParam(value = "activityId", required = true) int activityId,
                               @RequestParam(value = "collegeId", required = true) int collegeId,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "100") int size,
                               HttpServletRequest request, HttpServletResponse response){
        UserInfo self = Ums.getUser(request);
        BaseResult result = BaseResultUtil.initResult();
        try {
            if (self == null || activityId <= 0 || collegeId <= 0) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            int total = Constant.ServiceFacade.getArrangeService().count(null, collegeId, activityId);
            List<Arrange> arrangeList = Constant.ServiceFacade.getArrangeService().list(null, 0, activityId, "weight desc", 1, total);
            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            Map data = new HashMap();
            data.put("list", arrangeList);
            data.put("total", total);
            data.put("userInfo", self);
            data.put("activity", activity);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取安排流程异常", e);
            return result;
        }
    }

    @ApiOperation("获取单个安排流程")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult info(@RequestParam(value = "id", required = true) int id,
                           HttpServletRequest request, HttpServletResponse response){
        UserInfo self = Ums.getUser(request);
        BaseResult result = BaseResultUtil.initResult();
        try {
            if (self == null || self.getRole() != UserInfoService.USER_COLLEGE) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Arrange arrange = Constant.ServiceFacade.getArrangeService().select(id);
            if (arrange != null && arrange.getId() > 0) {
                BaseResultUtil.setSuccess(result, arrange);
                return result;
            }
            return result;
        } catch (Exception e) {
            logger.error("获取单个安排流程异常", e);
            return result;
        }
    }

    @ApiOperation("保存安排流程")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult save(Arrange arrange,
                           HttpServletRequest request, HttpServletResponse response){
        UserInfo self = Ums.getUser(request);
        BaseResult result = BaseResultUtil.initResult();
        try {
            if (self == null || self.getRole() != UserInfoService.USER_COLLEGE) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            if (arrange.getId() > 0) {
                Arrange oldArrange = Constant.ServiceFacade.getArrangeService().select(arrange.getId());
                if (oldArrange == null || oldArrange.getUserId() != self.getId()) {
                    result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                    return result;
                }
            }
            Constant.ServiceFacade.getArrangeService().save(arrange);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("保存安排流程异常", e);
            return result;
        }
    }

    @ApiOperation("删除流程")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult delete(@RequestParam(value = "id", required = true) int id,
                             HttpServletRequest request, HttpServletResponse response){
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (id <= 0){
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            if (self == null || self.getRole() != UserInfoService.USER_COLLEGE) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            if (id >= 0) {
                Arrange oldArrange = Constant.ServiceFacade.getArrangeService().select(id);
                if (oldArrange == null || oldArrange.getUserId() != self.getId()) {
                    result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                    return result;
                }
            }
            Constant.ServiceFacade.getArrangeService().delete(id);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("删除流程异常", e);
            return result;
        }
    }

    @ApiOperation("交换安排流程")
    @RequestMapping(value = "/exchange", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult exchange(@RequestParam(value = "fromId", required = true) int fromId,
                               @RequestParam(value = "toId", required = true) int toId,
                               HttpServletRequest request, HttpServletResponse response){
        UserInfo self = Ums.getUser(request);
        BaseResult result = BaseResultUtil.initResult();
        try {
            if (self == null || fromId <= 0 || toId <= 0) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Arrange fromArrange = Constant.ServiceFacade.getArrangeService().select(fromId);
            if (fromArrange == null || fromArrange.getUserId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Arrange toArrange = Constant.ServiceFacade.getArrangeService().select(toId);
            if (toArrange == null || toArrange.getUserId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            int temp;
            temp = fromArrange.getWeight();
            fromArrange.setWeight(toArrange.getWeight());
            toArrange.setWeight(temp);
            Constant.ServiceFacade.getArrangeService().save(fromArrange);
            Constant.ServiceFacade.getArrangeService().save(toArrange);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("交换安排流程异常", e);
            return result;
        }
    }

   /* @ApiOperation("保存文件是否需要")
    @RequestMapping(value = "/fileSave", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult fileSave(@RequestParam(value = "id", required = true) int id,
                               HttpServletRequest request, HttpServletResponse response){
        UserInfo self = Ums.getUser(request);
        BaseResult result = BaseResultUtil.initResult();
        try {
            if (self == null || id <= 0) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Arrange arrange = Constant.ServiceFacade.getArrangeService().select(id);
            if (arrange == null || arrange.getUserId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            int file = arrange.getFile();
            if (file == ArrangeService.FILE_INVALID){
                file = ArrangeService.FILE_VALID;
            } else if (file == ArrangeService.FILE_VALID) {
                file = ArrangeService.FILE_INVALID;
            } else {
                file = ArrangeService.FILE_INVALID;
            }
            arrange.setFile(file);
            Constant.ServiceFacade.getArrangeService().save(arrange);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("保存文件是否需要异常", e);
            return result;
        }
    }


    @ApiOperation("审核是否需要")
    @RequestMapping(value = "/followSave", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult followSave(@RequestParam(value = "id", required = true) int id,
                                 HttpServletRequest request, HttpServletResponse response){
        UserInfo self = Ums.getUser(request);
        BaseResult result = BaseResultUtil.initResult();
        try {
            if (self == null || id <= 0) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Arrange arrange = Constant.ServiceFacade.getArrangeService().select(id);
            if (arrange == null || arrange.getUserId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            int follow = arrange.getFollow();
            if (follow == ArrangeService.FOLLOW_INVALID){
                follow = ArrangeService.FOLLOW_INVALID;
            } else if (follow == ArrangeService.FOLLOW_INVALID) {
                follow = ArrangeService.FOLLOW_INVALID;
            } else {
                follow = ArrangeService.FOLLOW_INVALID;
            }
            arrange.setFollow(follow);
            Constant.ServiceFacade.getArrangeService().save(arrange);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("审核是否需要异常", e);
            return result;
        }
    }*/
}
