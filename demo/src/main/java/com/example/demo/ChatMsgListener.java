package com.example.demo;

import io.github.yangyouwang.springbootstarterim.core.DataContentEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author yangyouwang
 * @title: ChatMsgListener
 * @projectName oa
 * @description: 监听消息
 * @date 2021/3/1211:01 AM
 */
@Component
public class ChatMsgListener {

    @EventListener
    public void getData(DataContentEvent dataContentEvent) {
        System.out.println("收到消息了" + dataContentEvent.getDataContent());
    }
}
