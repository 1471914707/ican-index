package com.ican.service;

import com.ican.domain.Follow;
import com.ican.domain.Message;
import com.ican.exception.icanServiceException;

import java.util.List;

public interface MessageService {
    //基础方法
    int insert(Message message) throws icanServiceException;

    Message select(int id) throws icanServiceException;

    int update(Message message) throws icanServiceException;

    int delete(int id) throws icanServiceException;

    int save(Message message) throws icanServiceException;

    List<Message> list(String ids, int fromId, int toId, String conversationId, String order, int page, int size) throws icanServiceException;

    int count(String ids, int fromId, int toId, String conversationId) throws icanServiceException;

}
