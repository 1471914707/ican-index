package com.ican.async.handler;

import com.ican.async.EventHandler;
import com.ican.async.EventModel;
import com.ican.async.EventType;
import com.ican.config.Constant;
import com.ican.domain.Project;
import com.ican.domain.Task;
import com.ican.domain.UserInfo;
import com.ican.util.MailSender;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ProjectAddHandler implements EventHandler {
    @Override
    public void doHandle(EventModel model) {
        //当教师为学生增加任务，会自动给学生发送邮件通知一下
        try {
            UserInfo teacher = Constant.ServiceFacade.getUserInfoService().select(model.getEntityOwnerId());
            UserInfo student = Constant.ServiceFacade.getUserInfoService().select(model.getActorId());
            Task task = Constant.ServiceFacade.getTaskService().select(model.getEntityId());
            if (task != null) {
                Project project = Constant.ServiceFacade.getProjectService().select(task.getProjectId());
                if (project != null) {
                    String to = student.getEmail();
                    String content = student.getName() + "同学，你好！" + "教师" + teacher.getName() + "对你的" + project.getTitle() + "项目" +
                            "添加/修改了的任务《" + task.getTitle() + "》，请及时登录网站查看。";
                    MailSender mailSender = new MailSender();
                    mailSender.send(to, content);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.PROJECT_ADD);
    }
}
