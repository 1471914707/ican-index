package test.ican;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ican.IcanIndexApplication;
import com.ican.config.Constant;
import com.ican.domain.Arrange;
import com.ican.domain.Blog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IcanIndexApplication.class)
public class addArrange {
    @Test
    public void add() {
        try {
            for (int i = 1; i < 8; i ++) {
                Arrange arrange = new Arrange();
                arrange.setName("计划是这个月的" + (10 + i) + "号提交所有的报告");
                arrange.setFollow(1);
                arrange.setFile(2);
                arrange.setWeight(i);
                arrange.setActivityId(12);
                arrange.setUserId(100020);
                Constant.ServiceFacade.getArrangeService().save(arrange);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
