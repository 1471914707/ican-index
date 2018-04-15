package test.ican;

import com.ican.IcanIndexApplication;
import com.ican.config.Constant;
import com.ican.domain.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IcanIndexApplication.class)
public class addTask {

    @Test
    public void add() {
        try {
            for (int i=0; i<10; i++) {
                Task task = new Task();
                task.setOwnerId(100033);
                task.setExecutorId(100033);
                task.setProjectId(1);
                task.setTitle("第" + i + "次计划");
                task.setContent("没有内容" + i);
                task.setActivityId(12);
                Constant.ServiceFacade.getTaskService().save(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


