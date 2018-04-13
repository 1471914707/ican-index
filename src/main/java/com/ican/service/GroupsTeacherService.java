package com.ican.service;

import com.ican.domain.Groups;
import com.ican.domain.GroupsTeacher;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface GroupsTeacherService {
    //基础方法
    int insert(GroupsTeacher groupsTeacher) throws icanServiceException;

    GroupsTeacher select(int id) throws icanServiceException;

    int update(GroupsTeacher groupsTeacher) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(GroupsTeacher groupsTeacher) throws icanServiceException;

    List<GroupsTeacher> list(String ids, int activityId, int teacherId, int groupsId, String order, int page, int size) throws icanServiceException;

    int count(String ids, int activityId, int teacherId, int groupsId) throws icanServiceException;

}
