package io.github.yangyouwang.springbootstarterim.strategy.impl;

import io.github.yangyouwang.springbootstarterim.bean.DataContent;
import io.github.yangyouwang.springbootstarterim.bean.DataContentEvent;
import io.github.yangyouwang.springbootstarterim.constant.MsgActionEnum;
import io.github.yangyouwang.springbootstarterim.strategy.MsgStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * @author yangyouwang
 * @title: KeepAliveStrategy
 * @projectName springboot-starter-im
 * @description: 检测心跳
 * @date 2021/3/155:14 PM
 */
public class KeepAliveStrategy implements MsgStrategy {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void doAction(DataContent dataContent) {
        MsgActionEnum msgActionEnum = MsgActionEnum.getEnum(dataContent.getAction());
        DataContentEvent dataContentEvent = new DataContentEvent(applicationContext);
        dataContentEvent.setAction(msgActionEnum);
        applicationContext.publishEvent(dataContentEvent);
    }
}
