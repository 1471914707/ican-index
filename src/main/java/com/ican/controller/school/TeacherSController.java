package com.ican.controller.school;

import com.ican.domain.UserInfo;
import com.ican.util.BaseResult;
import com.ican.util.Ums;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api("学校操作教师")
@Controller
@RequestMapping("/school/teacher")
public class TeacherSController {
    private final static Logger logger = LoggerFactory.getLogger(TeacherSController.class);

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "school/teacher/list";
    }

    @ApiOperation("获取教师列表数据")
    @RequestMapping("/listJson")
    public BaseResult listJson(@RequestParam(value = "page",defaultValue = "1") int page,
                               @RequestParam(value = "size",defaultValue = "20") int size,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = new BaseResult();
        UserInfo userInfo = Ums.getUser(request);
        try {
            return result;
        } catch (Exception e) {
            logger.error("获取教师列表数据异常", e);
            return result;
        }
    }
}
