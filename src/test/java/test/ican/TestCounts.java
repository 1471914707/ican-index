package test.ican;

import com.alibaba.fastjson.JSONObject;
import com.ican.IcanIndexApplication;
import com.ican.config.Constant;
import com.ican.domain.Activity;
import com.ican.domain.Counts;
import com.ican.domain.Project;
import com.ican.service.CountsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IcanIndexApplication.class)
public class TestCounts {

    @Test
    public void counts() {
        try {
            //分批次处理活动
            List<Activity> activityList = Constant.ServiceFacade.getActivityService().listByDate("2018-4-30", null, 1, 20);
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
                jsonObject.put("11", successTotal);
                Counts counts = new Counts();
                counts.setActivityId(activity.getId());
                counts.setType(CountsService.TYPE_COMPLETE);
                counts.setContent(jsonObject.toJSONString());
                Constant.ServiceFacade.getCountsService().save(counts);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
