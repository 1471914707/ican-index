package com.ican.service;

import com.ican.domain.File;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface FileService {

    //文件类型定义
    int FILE_TYPE_ACTIVITY = 1;
    int FILE_TYPE_ARRANGE = 2;
    int FILE_TYPE_COLLEGE = 3;
    int FILE_TYPE_COLLEGE_TO_TEACHER = 4;
    int FILE_TYPE_COLLEGE_TO_STUDENT = 5;
    int FILE_TYPE_TEACHER = 6;
    int FILE_TYPE_TEACHER_STUDENT = 7;
    int FILE_TYPE_STUDENT = 8;
    int FILE_TYPE_STUDENT_TEACHER = 9;

/*    int FILE_TYPE_BLOG = 3;
    int FILE_TYPE_SCHOOL = 4;
    int FILE_TYPE_COLLEGE = 5;
    int FILE_TYPE_TEACHER = 6;
    int FILE_TYPE_STUDENT = 7;
    int FOLLOW_TYPE_PAPER = 8;
    int FOLLOW_TYPE_TASK = 9;
    int FOLLOW_TYPE_MESSAGE = 10;*/

    //基础方法
    int insert(File file) throws icanServiceException;

    File select(int id) throws icanServiceException;

    int update(File file) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(File file) throws icanServiceException;

    List<File> list(String ids, int userId, int targetId, int type, String order, int page, int size) throws icanServiceException;

    int count(String ids, int userId, int targetId, int type) throws icanServiceException;

}
