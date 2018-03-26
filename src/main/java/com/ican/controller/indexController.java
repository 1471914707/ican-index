package com.ican.controller;

import com.ican.util.JedisAdapter;
import com.ican.util.UrlUtil;
import com.ican.vo.SchoolActiveVO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api("Index主页")
@Controller
public class indexController {
    private final static Logger logger = LoggerFactory.getLogger(indexController.class);

    @RequestMapping(value = {"/", "/index"})
    public String index() {
        return "/ican_index";
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success(@RequestParam("role") int role) {
        //目前只有六种角色
        if (role < 0 || role > 6) {
            return "/index";
        }
        /*switch (role) {
            case 1:
            case 2:
                return UrlUtil.adminUrl;
            case 3:
                return UrlUtil.schoolUrl;
            case 4:
                return UrlUtil.collegeUrl;
            case 5:
                return UrlUtil.teacherUrl;
            case 6:
                return UrlUtil.studentUrl;
        }*/
        return "/index";
    }

    @ApiOperation("获取学校活跃程度数据")
    @ResponseBody
    @RequestMapping(value = "/schoolActiveData")
    public BaseResult schoolActiveData() {
        BaseResult result = BaseResultUtil.initResult();
        try {
            List<SchoolActiveVO> schoolActiveVOList = new ArrayList<>();
            JedisAdapter jedisAdapter = new JedisAdapter();
            Map<String, String> schoolActiveMap = jedisAdapter.getSchoolDayLogin();
            if (schoolActiveMap != null) {
                for (Map.Entry entry : schoolActiveMap.entrySet()) {
                    SchoolActiveVO schoolActiveVO = new SchoolActiveVO();
                    schoolActiveVO.setName((String) entry.getKey());
                    schoolActiveVO.setValue(Integer.valueOf((String) entry.getValue()));
                    schoolActiveVOList.add(schoolActiveVO);
                }
                BaseResultUtil.setSuccess(result, schoolActiveVOList);
                return result;
            }
            return result;
        } catch (Exception e) {
            logger.error("获取学校活跃程度异常" + e);
            return result;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String schoolLogin(@RequestParam("role") int role,
                              HttpServletRequest request, HttpServletResponse response) {
        //目前只有六种角色
        if (role < 0 || role > 6) {
            return "/index";
        }
        request.setAttribute("role", role);
        return "/login";
    }

    @RequestMapping("/register/school")
    public String school() {
        return "/school/register";
    }

    @RequestMapping("/register/teacher")
    public String teacher() {
        return "/teacher/register";
    }

    @RequestMapping("/register/student")
    public String student() {
        return "/student/register";
    }
}
