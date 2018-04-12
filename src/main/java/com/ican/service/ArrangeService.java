package com.ican.service;

import com.ican.domain.Arrange;
import com.ican.domain.Follow;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface ArrangeService {

    //类型定义
    int FILE_INVALID = 1;
    int FILE_VALID = 2;

    int FOLLOW_INVALID = 1;
    int FOLLOW_VALID = 2;

      //基础方法
    int insert(Arrange arrange) throws icanServiceException;

    Arrange select(int id) throws icanServiceException;

    int update(Arrange arrange) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(Arrange arrange) throws icanServiceException;

    List<Arrange> list(String ids, int userId, int activityId, String order, int page, int size) throws icanServiceException;

    int count(String ids, int userId, int activityId) throws icanServiceException;

}
