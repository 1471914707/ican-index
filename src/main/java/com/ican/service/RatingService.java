package com.ican.service;

import com.ican.domain.Groups;
import com.ican.domain.Rating;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface RatingService {
    //基础方法
    int insert(Rating rating) throws icanServiceException;

    Rating select(int id) throws icanServiceException;

    int update(Rating rating) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(Rating rating) throws icanServiceException;

    List<Rating> list(String ids, int answerId, int groupsId, int projectId, int teacherId, String order, int page, int size) throws icanServiceException;

    int count(String ids, int answerId, int groupsId, int projectId, int teacherId) throws icanServiceException;

}
