package test.ican;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ican.IcanIndexApplication;
import com.ican.config.Constant;
import com.ican.domain.Blog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IcanIndexApplication.class)
public class addBlog {

    @Test
    public void add() {
        try {
            for (int i = 0; i < 25; i ++) {
                Blog blog = new Blog();
                JSONArray imageJson = new JSONArray();
                int maxJ = new Random().nextInt(9);
                for (int j=0; j< maxJ; j++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("url", "https://static.nowcoder.net/head/"+new Random().nextInt(1000)+"m.png");
                    imageJson.add(jsonObject);
                }
                blog.setImage(imageJson.toJSONString());
                blog.setUserId(100002);
                blog.setContent("这是我发布的第" + i + "条广告，请大家都注意一下！！！填充一些无所谓的东西");
                Constant.ServiceFacade.getBlogService().save(blog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
