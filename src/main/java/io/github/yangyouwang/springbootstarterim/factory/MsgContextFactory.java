package io.github.yangyouwang.springbootstarterim.factory;

import io.github.yangyouwang.springbootstarterim.constant.MsgActionEnum;
import io.github.yangyouwang.springbootstarterim.strategy.MsgStrategy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author yangyouwang
 * @title: MsgContextFactory
 * @projectName springboot-starter-im
 * @description: 上下文
 * @date 2021/3/159:45 AM
 */
@Service
public class MsgContextFactory {

    @Resource
    private Map<String, MsgStrategy> actionMap;

    public MsgStrategy getMsgStrategy(MsgActionEnum msgActionEnum) {
        MsgStrategy msgStrategy = actionMap.get(msgActionEnum.TYPE);
        if (null == msgStrategy) {
            throw new RuntimeException("no strategy defined");
        }
       return msgStrategy;
    }
}
