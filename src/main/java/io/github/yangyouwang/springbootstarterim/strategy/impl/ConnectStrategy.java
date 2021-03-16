package io.github.yangyouwang.springbootstarterim.strategy.impl;

import io.github.yangyouwang.springbootstarterim.bean.DataContent;
import io.github.yangyouwang.springbootstarterim.bean.DataContentEvent;
import io.github.yangyouwang.springbootstarterim.constant.MsgActionEnum;
import io.github.yangyouwang.springbootstarterim.core.ChatHandler;
import io.github.yangyouwang.springbootstarterim.core.UserChanelRel;
import io.github.yangyouwang.springbootstarterim.strategy.MsgStrategy;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * @author yangyouwang
 * @title: ConnectStrategy
 * @projectName springboot-starter-im
 * @description: 第一次(或重连)初始化连接
 * @date 2021/3/159:06 AM
 */
public class ConnectStrategy implements MsgStrategy {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void doAction(DataContent dataContent) {
        Long fromUserId = dataContent.getChatMsg().getFromUserId();
        MsgActionEnum msgActionEnum = MsgActionEnum.getEnum(dataContent.getAction());

        Channel channel =  ChatHandler.ctx.channel();
        UserChanelRel.put(fromUserId,channel);

        DataContentEvent dataContentEvent = DataContentEvent.getInstance();
        dataContentEvent.setFromUserId(fromUserId);
        dataContentEvent.setAction(msgActionEnum);
        applicationContext.publishEvent(dataContentEvent);
    }
}
