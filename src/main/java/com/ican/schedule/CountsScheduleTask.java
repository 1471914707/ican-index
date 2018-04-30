package com.ican.schedule;

import com.alibaba.fastjson.JSONObject;
import com.ican.async.EventModel;
import com.ican.async.EventProducer;
import com.ican.async.EventType;
import com.ican.config.Constant;
import com.ican.domain.Activity;
import com.ican.domain.Counts;
import com.ican.domain.Project;
import com.ican.service.CountsService;
import com.ican.service.ProjectService;
import com.qiniu.util.Json;
import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class CountsScheduleTask {
    private final static Logger logger = LoggerFactory.getLogger(ProjectScheduleTask.class);

    //每星期六12点点扫一扫
    @Scheduled(cron = "0 0 1 * * 1")
    //@Scheduled(cron = "0/50 * * * * ?")
    //@Scheduled(cron = "0 0 19 * * ?")
    private void Counts() {
        try {
            //类似列变量来弄一下
            String nowDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date()); //获得明天的日期
            int total = Constant.ServiceFacade.getActivityService().countByDate(nowDay);

            int size = 30;
            int pages = total / size + 1;
            int surplus = total % size;
            for (int i=1; i<=pages; i++) {
                int newSize = size;
                if (i == pages) {
                    newSize = surplus;
                }
                //分批次处理活动
                List<Activity> activityList = Constant.ServiceFacade.getActivityService().listByDate(nowDay, null, i, newSize);
                for (Activity activity : activityList) {
                    int[] pers = new int[10];
                    int successTotal = 0;
                    //找到项目和它的进度
                    int projectTotal = Constant.ServiceFacade.getProjectService().count(null, activity.getId(), 0, 0, 0);
                    List<Project> projectList = Constant.ServiceFacade.getProjectService().list(null, activity.getId(), 0, 0, 0, null, 1, projectTotal);
                    for (Project project : projectList) {
                        int complete = project.getComplete();
                        //完成人数
                        if (complete == 100) {
                            successTotal++;
                            pers[9]++;
                        } else {
                            pers[complete / 10]++;
                        }
                    }
                    //记录入库
                    JSONObject jsonObject = new JSONObject();
                    //避免重复储存那么多key,用数字来代表了1-10是区间进度，11是完成人数
                    for (int j=0; j<10; j++) {
                        jsonObject.put("" + (j + 1), pers[j]);
                    }
                    jsonObject.put("11", projectTotal);
                    jsonObject.put("12", successTotal);
                    Counts counts = new Counts();
                    counts.setActivityId(activity.getId());
                    counts.setType(CountsService.TYPE_COMPLETE);
                    counts.setContent(jsonObject.toJSONString());
                    Constant.ServiceFacade.getCountsService().save(counts);
                }
            }
        } catch (Exception e) {
            logger.error("数据统计异常", e);
        }
    }
}
