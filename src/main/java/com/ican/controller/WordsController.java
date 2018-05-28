package com.ican.controller;

import com.ican.config.Constant;
import com.ican.domain.Words;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/words")
public class WordsController {

    private final static Logger logger = LoggerFactory.getLogger(WordsController.class);

    @RequestMapping(value = {"/listJson"}, method = RequestMethod.GET)
    public BaseResult listJson(HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            List<Words> wordsList = Constant.ServiceFacade.getWordsService().list(null, null, 1, 100);
            BaseResultUtil.setSuccess(result, wordsList);
            return result;
        } catch (Exception e) {
            logger.debug("获取单词列表异常", e);
            return result;
        }
    }

}
