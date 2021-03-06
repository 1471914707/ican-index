package com.ican.controller.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api/userInfo")
public class UserInfoIController {

    @ApiOperation("登陆接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String schoolLogin(@RequestParam("account") String account,
                              @RequestParam("password") String password,
                              @RequestParam("role") int role,
                              HttpServletRequest request, HttpServletResponse response) {
        //目前只有六种角色
        if (role < 0 || role > 6) {
            return "/index";
        }
        request.setAttribute("role",role);
        return "/login";
    }

}
