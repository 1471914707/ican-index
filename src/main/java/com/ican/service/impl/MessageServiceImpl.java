package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.domain.Message;
import com.ican.exception.icanServiceException;
import com.ican.service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public int insert(Message message) throws icanServiceException {
        return Constant.DaoFacade.getMessageDao().insert(message);
    }

    @Override
    public Message select(int id) throws icanServiceException {
        return (Message)Constant.DaoFacade.getMessageDao().select(id);
    }

    @Override
    public int update(Message message) throws icanServiceException {
        return Constant.DaoFacade.getMessageDao().update(message);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getMessageDao().delete(id);
    }

    @Override
    public int save(Message message) throws icanServiceException {
        if (message == null) {
            return 0;
        }
        if (message.getId() > 0) {
            update(message);
        } else {
            if (StringUtils.isEmpty(message.getContent())) {
                message.setContent("[消息错误!!!]");
            }
            insert(message);
        }
        return message.getId();
    }

    @Override
    public List<Message> list(String ids, int fromId, int toId, String conversationId, String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("fromId", fromId);
        param.put("toId", toId);
        param.put("conversationId", conversationId);
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getMessageDao().list(param);
    }

    @Override
    public int count(String ids, int fromId, int toId, String conversationId) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("fromId", fromId);
        param.put("toId", toId);
        param.put("conversationId", conversationId);
        return Constant.DaoFacade.getMessageDao().count(param);
    }
}
