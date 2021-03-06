package com.ican.async;


import com.alibaba.fastjson.JSONObject;
import com.ican.util.JedisAdapter;
import com.ican.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {
    /*@Autowired
    JedisAdapter jedisAdapter;*/
    private static JedisAdapter jedisAdapter = new JedisAdapter();

    public boolean fireEvent(EventModel model) {
        try {
            //JedisAdapter jedisAdapter = new JedisAdapter();
            String json = JSONObject.toJSONString(model);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
