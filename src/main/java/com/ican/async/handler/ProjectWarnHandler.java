package com.ican.async.handler;

import com.ican.async.EventHandler;
import com.ican.async.EventModel;
import com.ican.async.EventType;
import com.ican.config.Constant;
import com.ican.domain.Project;
import com.ican.domain.UserInfo;
import com.ican.util.MailSender;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ProjectWarnHandler implements EventHandler {
    @Override
    public void doHandle(EventModel model) {
        //任务快到了，还没完成，就应该通知一下
        try {
            UserInfo userInfo = Constant.ServiceFacade.getUserInfoService().select(model.getEntityOwnerId());
            String to = userInfo.getEmail();
            String content = "系统发现你有任务明天即将过期还没完成，请登录ICAN毕业设计平台查看，及时完成任务更新进度。";
            MailSender mailSender = new MailSender();
            mailSender.send(to, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.PROJECT_WARN);
    }
}
