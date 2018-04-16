package com.ican.schedule;

import com.ican.async.EventModel;
import com.ican.async.EventProducer;
import com.ican.async.EventType;
import com.ican.config.Constant;
import com.ican.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ProjectScheduleTask {

    private final static Logger logger = LoggerFactory.getLogger(ProjectScheduleTask.class);

    //明天7点扫一扫
    @Scheduled(cron = "0 0 7 * * ?")
    //@Scheduled(cron = "0/10 * * * * ?")
    private void ProjectWarn() {
        try {
            int total = Constant.ServiceFacade.getProjectService().count(ProjectService.WARN_YES);
            int size = 1000;
            int pages = total / size + 1;
            int surplus = total % size;
            Calendar calendar = Calendar.getInstance();
            calendar.add(calendar.DATE, 1);
            String nowDay = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()); //获得明天的日期
            for (int i=1; i<=pages; i++) {
                int newSize = size;
                if (i == pages) {
                    newSize = surplus;
                }
                List<Integer> list = Constant.ServiceFacade.getProjectService().list(ProjectService.WARN_YES, i, newSize);
                if (list != null && list.size() > 0) {
                    for (int j=0; j<list.size(); j++) {
                        int count = Constant.ServiceFacade.getTaskService().count(list.get(j), nowDay);
                        if (count > 0) {
                            //异步发送通知
                            new EventProducer().fireEvent(new EventModel().setType(EventType.PROJECT_WARN).setEntityOwnerId(list.get(j)));
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("项目提醒功能异常", e);
        }
    }
}
