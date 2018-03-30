package com.ican.async.handler;

import com.ican.async.EventHandler;
import com.ican.async.EventModel;
import com.ican.async.EventType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class TestHandler implements EventHandler {

    @Override
    public void doHandle(EventModel model) {
        //每个两秒输入一次
        for (int i=0; i<10; i++) {
            try {
                Thread.sleep(1000);
                System.out.println("============="+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.TEST);
    }
}
