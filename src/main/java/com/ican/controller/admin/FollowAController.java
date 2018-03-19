package com.ican.controller.admin;

import com.ican.config.Constant;
import com.ican.domain.Follow;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
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

@Api("跟进接口")
@Controller
@RequestMapping("/admin/follow")
public class FollowAController {

    private final static Logger logger = LoggerFactory.getLogger(FollowAController.class);

    @ApiOperation("跟进列表接口")
    @ResponseBody
    @RequestMapping(value = "/listJson",method = RequestMethod.GET)
    public BaseResult listJson(@RequestParam(value = "followUserId", defaultValue = "0") int followUserId,
                                @RequestParam(value = "followId", defaultValue = "0") int followId,
                                @RequestParam(value = "followType", defaultValue = "0") int followType,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "size", defaultValue = "20") int size,
                                HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            List<Follow> followList = Constant.ServiceFacade.getFollowService().list(null, followUserId, followId, followType, "id desc", page, size);
            int total = Constant.ServiceFacade.getFollowService().count(null, followUserId, followId, followType);
            Map param = new HashMap();
            param.put("list", followList);
            param.put("total", total);
            BaseResultUtil.setSuccess(result, param);
            return result;
        } catch (Exception e) {
            logger.error("获取跟进列表异常", e);
            return result;
        }
    }

    @ApiOperation("跟进保存接口,一般保存是新增加的")
    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public BaseResult save(Follow follow,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            if (follow == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Constant.ServiceFacade.getFollowService().save(follow);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("跟进保存异常", e);
            return result;
        }
    }
}
