package test.ican;

import com.ican.IcanIndexApplication;
import com.ican.config.Constant;
import com.ican.domain.AnswerArrange;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IcanIndexApplication.class)
public class addAnswerArrange {
    @Test
    public void add() {
        try {
            for (int i = 1; i < 8; i ++) {
                AnswerArrange answerArrange = new AnswerArrange();
                answerArrange.setActivityId(12);
                answerArrange.setName("第" + i + "次答辩安排");
                answerArrange.setType(i == 7 ? 2 : 1);
                answerArrange.setRatio1(40);
                answerArrange.setRatio2(60);
                Constant.ServiceFacade.getAnswerArrangeService().save(answerArrange);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
