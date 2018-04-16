package com.ican.async.handler;

import com.ican.async.EventHandler;
import com.ican.async.EventModel;
import com.ican.async.EventType;
import com.ican.config.Constant;
import com.ican.domain.Paper;
import com.ican.domain.Project;
import com.ican.domain.Teacher;
import com.ican.domain.UserInfo;
import com.ican.util.MailSender;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ProjectFollowHandler implements EventHandler {
    @Override
    public void doHandle(EventModel model) {
        //当教师审核一次的时候，会自动给学生发送邮件通知一下
        try {
            UserInfo teacher = Constant.ServiceFacade.getUserInfoService().select(model.getActorId());
            UserInfo student = Constant.ServiceFacade.getUserInfoService().select(model.getEntityOwnerId());
            Project project = Constant.ServiceFacade.getProjectService().select(model.getEntityId());
            String to = student.getEmail();
            String content = student.getName() + "同学，你好！" + "教师" + teacher.getName() + "对你的" + project.getTitle() + "项目做出了审核建言，请及时登录网站查看。";
            MailSender mailSender = new MailSender();
            mailSender.send(to, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.PROJECT_FOLLOW);
    }
}
