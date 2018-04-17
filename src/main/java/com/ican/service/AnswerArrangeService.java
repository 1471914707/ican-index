package com.ican.service;

import com.ican.domain.AnswerArrange;
import com.ican.domain.Arrange;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface AnswerArrangeService {

    //类型定义
    int ANSWER_INVALID = 1;
    int ANSWER_VALID = 2;

    //基础方法
    int insert(AnswerArrange answerArrange) throws icanServiceException;

    AnswerArrange select(int id) throws icanServiceException;

    int update(AnswerArrange answerArrange) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(AnswerArrange answerArrange) throws icanServiceException;

    List<AnswerArrange> list(String ids, int activityId, int type, String order, int page, int size) throws icanServiceException;

    int count(String ids, int activityId, int type) throws icanServiceException;
}
