package test.ican;


import com.ican.IcanIndexApplication;
import com.ican.config.Constant;
import com.ican.domain.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IcanIndexApplication.class)
public class messageTest {

    @Test
    public void messge() {
        try {
            List<Message> messageList = Constant.DaoFacade.getMessageDao().listByUserId(100020, 1, 2);
            int total = Constant.ServiceFacade.getMessageService().count(null, 100020, 100020, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
