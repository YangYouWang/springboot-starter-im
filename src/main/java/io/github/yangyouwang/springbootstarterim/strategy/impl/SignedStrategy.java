package io.github.yangyouwang.springbootstarterim.strategy.impl;

import io.github.yangyouwang.springbootstarterim.bean.ChatMsg;
import io.github.yangyouwang.springbootstarterim.bean.DataContent;
import io.github.yangyouwang.springbootstarterim.bean.DataContentEvent;
import io.github.yangyouwang.springbootstarterim.constant.MsgActionEnum;
import io.github.yangyouwang.springbootstarterim.constant.MsgStatusEnum;
import io.github.yangyouwang.springbootstarterim.strategy.MsgStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * @author yangyouwang
 * @title: SignedStrategy
 * @projectName springboot-starter-im
 * @description: 消息签收
 * @date 2021/3/159:25 AM
 */
public class SignedStrategy implements MsgStrategy {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void doAction(DataContent dataContent) {
        MsgActionEnum msgActionEnum = MsgActionEnum.getEnum(dataContent.getAction());
        ChatMsg chatMsg = dataContent.getChatMsg();
        Long toUserId = chatMsg.getToUserId();
        Long fromUserId = chatMsg.getFromUserId();
        // 签收消息类型
        DataContentEvent dataContentEvent = new DataContentEvent(applicationContext);
        dataContentEvent.setStatus(MsgStatusEnum.MSG_STATUS_HAVE_READ);
        dataContentEvent.setFromUserId(fromUserId);
        dataContentEvent.setToUserId(toUserId);
        dataContentEvent.setAction(msgActionEnum);
        applicationContext.publishEvent(dataContentEvent);
    }
}
