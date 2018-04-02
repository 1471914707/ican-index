package com.ican.controller.school;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/school/student")
public class StudentSController {
    private final static Logger logger = LoggerFactory.getLogger(StudentSController.class);

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "school/student/list";
    }

}
