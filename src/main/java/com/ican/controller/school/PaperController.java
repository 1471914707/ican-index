package com.ican.controller.school;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/school/paper")
public class PaperController {
    private final static Logger logger = LoggerFactory.getLogger(PaperController.class);

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String index() {
        return "/school/paper/list";
    }
}
