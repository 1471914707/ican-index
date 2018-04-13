package com.ican.controller;

import com.ican.config.Constant;
import com.ican.domain.College;
import com.ican.domain.Message;
import com.ican.domain.School;
import com.ican.domain.UserInfo;
import com.ican.service.UserInfoService;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.runtime.regexp.joni.ast.ConsAltNode;
import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Api("消息")
@Controller
@RequestMapping("/message")
public class MessageController {
    private final static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @RequestMapping(value = {"/my"}, method = RequestMethod.GET)
    public String my(HttpServletRequest request, HttpServletResponse response) {
        return "/message/list";
    }

    @RequestMapping(value = {"","/","/index"}, method = RequestMethod.GET)
    public String index(@RequestParam("toId") String toId,
                        HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("toId", toId);
        return "/message/conversation_list";
    }

    @ApiOperation("获取个人全部对话列表")
    @RequestMapping(value = "/listMy", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listMy(@RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "size", defaultValue = "20") int size,
                             HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (self == null) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            List<Message> messageList = Constant.ServiceFacade.getMessageService().list(self.getId(), page, size);
            int total = Constant.ServiceFacade.getMessageService().count(self.getId());
            Set<String> userSet = new HashSet<>();
            for (Message message : messageList) {
                userSet.add(message.getFromId() + "");
                userSet.add(message.getToId() + "");
            }
            String userIds = String.join(",", userSet);
            List<UserInfo> userList = new ArrayList<>();
            if (!StringUtils.isEmpty(userIds)) {
                userList = Constant.ServiceFacade.getUserInfoService().list(userIds, null, null, 0, null, 1, 100);
            }
            Map data = new HashMap();
            data.put("list", messageList);
            data.put("total", total);
            data.put("userList", userList);
            data.put("userId", self.getId());
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取个人全部对话列表异常", e);
            return result;
        }
    }

    @ApiOperation("获取对话列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult list(@RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "size", defaultValue = "20") int size,
                           @RequestParam(value = "toId", defaultValue = "0") int toId,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo fromUser = Ums.getUser(request);
        if (toId <= 0 || fromUser == null) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            UserInfo toUser = Constant.ServiceFacade.getUserInfoService().select(toId);
            if (toUser == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            String conversationId = fromUser.getId() < toUser.getId() ? String.format("%d_%d", fromUser.getId(), toUser.getId()) : String.format("%d_%d", toUser.getId(), fromUser.getId());
            List<Message> messageList = Constant.ServiceFacade.getMessageService().list(null, 0, 0, conversationId, "id desc", page, size);
            for (Message message : messageList) {
                if (fromUser.getId() == message.getToId() && message.getHasRead() == 1) {
                    message.setHasRead(2);
                    Constant.ServiceFacade.getMessageService().save(message);
                }
            }
            int total = Constant.ServiceFacade.getMessageService().count(null, 0, 0, conversationId);
            Map data = new HashMap();
            data.put("fromName", fromUser.getName());
            data.put("fromHeadshot", fromUser.getHeadshot());
            data.put("toName", toUser.getName());
            data.put("toHeadshot", toUser.getHeadshot());
            data.put("list", messageList);
            data.put("total", total);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取对话列表异常", e);
            return result;
        }
    }

    @ApiOperation("发送对话")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult list(@RequestParam(value = "toId", defaultValue = "0") int toId,
                           @RequestParam(value = "content",defaultValue = "") String content,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo fromUser = Ums.getUser(request);
        if (toId <= 0 || fromUser == null) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            UserInfo toUser = Constant.ServiceFacade.getUserInfoService().select(toId);
            if (toUser == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            String conversationId = fromUser.getId() < toUser.getId() ? String.format("%d_%d", fromUser.getId(), toUser.getId()) : String.format("%d_%d", toUser.getId(), fromUser.getId());
            Message message = new Message();
            message.setConversationId(conversationId);
            message.setContent(content);
            message.setFromId(fromUser.getId());
            message.setToId(toUser.getId());
            int id = Constant.ServiceFacade.getMessageService().save(message);
            if (id > 0) {
                BaseResultUtil.setSuccess(result, id);
            }
            return result;
        } catch (Exception e) {
            logger.error("发送信息异常", e);
            return result;
        }
    }
}
