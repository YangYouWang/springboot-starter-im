package io.github.yangyouwang.springbootstarterim.core;

import io.netty.channel.Channel;

import java.util.HashMap;

/**
 * 用户id 和channel 的关联关系处理
 * @author yangyouwang
 */
public class UserChanelRel {

    private static HashMap<Long, Channel> manage = new HashMap<>();

    public static  void put(Long fromUserId,Channel channel){
        manage.put(fromUserId,channel);
    }

    public static Channel get(Long toUserId){
        return manage.get(toUserId);
    }
}
