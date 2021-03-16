package io.github.yangyouwang.springbootstarterim.config;

import io.github.yangyouwang.springbootstarterim.constant.MsgActionEnum;
import io.github.yangyouwang.springbootstarterim.strategy.MsgStrategy;
import io.github.yangyouwang.springbootstarterim.strategy.impl.ChatStrategy;
import io.github.yangyouwang.springbootstarterim.strategy.impl.ConnectStrategy;
import io.github.yangyouwang.springbootstarterim.strategy.impl.KeepAliveStrategy;
import io.github.yangyouwang.springbootstarterim.strategy.impl.SignedStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangyouwang
 * @title: MsgActionConfig
 * @projectName springboot-starter-im
 * @description: 消息bean
 * @date 2021/3/1511:16 AM
 */
@Configuration
public class MsgConfig {

    @Bean
    public ChatStrategy chatStrategy() {
        return new ChatStrategy();
    }

    @Bean
    public ConnectStrategy connectStrategy() {
        return new ConnectStrategy();
    }

    @Bean
    public SignedStrategy signedStrategy() {
        return new SignedStrategy();
    }

    @Bean
    public KeepAliveStrategy keepAliveStrategy() {
        return new KeepAliveStrategy();
    }

    @Bean
    public Map<Integer, MsgStrategy> actionMap(@Qualifier("connectStrategy") ConnectStrategy connectStrategy,
                                               @Qualifier("chatStrategy") ChatStrategy chatStrategy,
                                               @Qualifier("signedStrategy") SignedStrategy signedStrategy,
                                               @Qualifier("keepAliveStrategy") KeepAliveStrategy keepAliveStrategy){
        Map<Integer, MsgStrategy> map = new HashMap<>();
        map.put(MsgActionEnum.CONNECT.TYPE, connectStrategy);
        map.put(MsgActionEnum.CHAT.TYPE, chatStrategy);
        map.put(MsgActionEnum.SIGNED.TYPE, signedStrategy);
        map.put(MsgActionEnum.KEEP_ALIVE.TYPE, keepAliveStrategy);
        return map;
    }
}
