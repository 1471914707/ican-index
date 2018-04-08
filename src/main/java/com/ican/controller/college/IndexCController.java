package com.ican.controller.college;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api("二级学院主页")
@Controller
@RequestMapping("/college")
public class IndexCController {
    private final Logger logger = LoggerFactory.getLogger(IndexCController.class);

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String index() {
        return "college/index";
    }


}
