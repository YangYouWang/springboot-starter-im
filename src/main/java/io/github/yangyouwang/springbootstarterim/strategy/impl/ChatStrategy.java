package io.github.yangyouwang.springbootstarterim.strategy.impl;

import com.alibaba.fastjson.JSONObject;
import io.github.yangyouwang.springbootstarterim.bean.ChatMsg;
import io.github.yangyouwang.springbootstarterim.bean.DataContent;
import io.github.yangyouwang.springbootstarterim.bean.DataContentEvent;
import io.github.yangyouwang.springbootstarterim.constant.MsgActionEnum;
import io.github.yangyouwang.springbootstarterim.constant.MsgStatusEnum;
import io.github.yangyouwang.springbootstarterim.constant.MsgTypeEnum;
import io.github.yangyouwang.springbootstarterim.core.ChatHandler;
import io.github.yangyouwang.springbootstarterim.core.UserChanelRel;
import io.github.yangyouwang.springbootstarterim.strategy.MsgStrategy;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * @author yangyouwang
 * @title: ChatStrategy
 * @projectName springboot-starter-im
 * @description: 聊天消息
 * @date 2021/3/159:20 AM
 */
public class ChatStrategy implements MsgStrategy {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void doAction(DataContent dataContent) {
        // 聊天类型的消息
        MsgActionEnum msgActionEnum = MsgActionEnum.getEnum(dataContent.getAction());
        ChatMsg chatMsg = dataContent.getChatMsg();
        Long toUserId = chatMsg.getToUserId();
        Long fromUserId = chatMsg.getFromUserId();
        String message = chatMsg.getMessage();
        MsgTypeEnum msgTypeEnum = MsgTypeEnum.getEnum(dataContent.getChatMsg().getType());
        //发送消息
        Channel receiverChannel = UserChanelRel.get(toUserId);
        if(receiverChannel!= null){
            Channel findChanel = ChatHandler.users.find(receiverChannel.id());
            if(findChanel!=null){
                //用户在线
                receiverChannel.writeAndFlush(
                        new TextWebSocketFrame(
                                JSONObject.toJSONString(dataContent)
                        )
                );
            }
            // 用户离线
            DataContentEvent dataContentEvent = DataContentEvent.getInstance();
            dataContentEvent.setFromUserId(fromUserId);
            dataContentEvent.setToUserId(toUserId);
            dataContentEvent.setType(msgTypeEnum);
            dataContentEvent.setStatus(MsgStatusEnum.MSG_STATUS_UNREAD);
            dataContentEvent.setMessage(message);
            dataContentEvent.setAction(msgActionEnum);
            applicationContext.publishEvent(dataContentEvent);
        }
    }
}
