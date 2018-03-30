package com.ican.controller.school;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/school/teacher")
public class TeacherController {
    private final static Logger logger = LoggerFactory.getLogger(TeacherController.class);

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "school/teacher/list";
    }
}
