package com.ican.controller.admin;

import com.ican.config.Constant;
import com.ican.domain.Follow;
import com.ican.domain.SchoolAppeal;
import com.ican.domain.User;
import com.ican.domain.UserInfo;
import com.ican.service.FollowService;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/schoolAppeal")
public class SchoolAppealAController {

    private final static Logger logger = LoggerFactory.getLogger(SchoolAppealAController.class);


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String schoolApeal() {
        return "/admin/school/appeal_list";
    }

    @ApiOperation("获取学校申议的列表")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public BaseResult list(@RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "size", defaultValue = "20") int size,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            List<Map> mapList = new ArrayList<>();
            List<SchoolAppeal> schoolAppealList = Constant.ServiceFacade.getSchoolAppealService().list(null, "id desc", page, size);
            int total = Constant.ServiceFacade.getSchoolAppealService().count(null);
            for (SchoolAppeal schoolAppeal : schoolAppealList) {
                Follow follow = new Follow();
                List<Follow> followList = Constant.ServiceFacade.getFollowService().list(null, 0, schoolAppeal.getId(), FollowService.FOLLOW_TYPE_APPEAL, "id desc", 1, 1);
                if (followList != null && followList.size() > 0) {
                    follow = followList.get(0);
                }
                Map map = new HashMap();
                map.put("follow", follow);
                map.put("schoolAppeal", schoolAppeal);
                mapList.add(map);
            }
            HashMap param = new HashMap();
            param.put("list", schoolAppealList);
            param.put("total", total);
            BaseResultUtil.setSuccess(result, param);
            return result;
        } catch (Exception e) {
            logger.error("获取学校申议的列表异常", e);
            return result;
        }
    }

    @ApiOperation("保存学校申议状态")
    @ResponseBody
    @RequestMapping(value = "/statusSave", method = RequestMethod.POST)
    public BaseResult save(@RequestParam("id") int id,
                           @RequestParam("status") int status,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (id <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            SchoolAppeal schoolAppeal = Constant.ServiceFacade.getSchoolAppealService().select(id);
            if (schoolAppeal == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            schoolAppeal.setStatus(status);
            Constant.ServiceFacade.getSchoolAppealService().save(schoolAppeal);
            //添加跟进记录
            UserInfo userInfo = Ums.getUser(request);
            if (userInfo == null) {
                result.setMsg("请先登录");
                return result;
            }
            Follow follow = new Follow();
            follow.setFollowUserId(userInfo.getId());
            follow.setFollowUserName(userInfo.getName());
            follow.setContent("更改状态");
            follow.setFollowId(schoolAppeal.getId());
            follow.setFollowType(FollowService.FOLLOW_TYPE_APPEAL);
            Constant.ServiceFacade.getFollowService().save(follow);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("保存学校申议状态异常", e);
            return result;
        }
    }

}
