package com.ican.controller.admin;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api("管理员控制器")
@RequestMapping("/admin")
@Controller
public class adminController {

    @RequestMapping(value = {"/","/adminList"},method = RequestMethod.GET)
    public String index() {
        return "/admin/super/admin_index";
    }
}
