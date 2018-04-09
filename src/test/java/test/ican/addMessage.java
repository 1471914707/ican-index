package test.ican;


import com.ican.IcanIndexApplication;
import com.ican.config.Constant;
import com.ican.domain.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IcanIndexApplication.class)
public class addMessage {

    @Test
    public void add() {
        for (int i=0; i<25; i++) {
            Message message = new Message();
            message.setConversationId("100002_100020");
            message.setFromId(100002);
            message.setToId(100020);
            message.setContent(i + "条信息--我是校长");

            Message message1 = new Message();
            message1.setContent(i + "条信息--我还院长");
            message1.setConversationId("100002_100020");
            message1.setFromId(100020);
            message1.setToId(100002);
            try {
                Constant.ServiceFacade.getMessageService().save(message);
                Constant.ServiceFacade.getMessageService().save(message1);
            } catch (Exception e) {

            }
        }
    }
}
