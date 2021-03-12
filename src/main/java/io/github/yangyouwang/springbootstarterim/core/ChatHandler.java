package io.github.yangyouwang.springbootstarterim.core;

import com.alibaba.fastjson.JSONObject;
import io.github.yangyouwang.springbootstarterim.constant.MsgActionEnum;
import io.github.yangyouwang.springbootstarterim.bean.ChatMsg;
import io.github.yangyouwang.springbootstarterim.bean.DataContent;
import io.github.yangyouwang.springbootstarterim.utils.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;

import java.util.Objects;

/**
 * 用于处理消息的handler
 * @author yangyouwang
 */
@Log4j2
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    /**
     * 用于记录和管理所有客户端的channel
     */
    public static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static final ApplicationContext applicationContext;

    private static final DataContentEvent dataContentEvent;

    static {
        applicationContext = SpringUtil.getApplicationContext();
        dataContentEvent = new DataContentEvent(applicationContext);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        String content = msg.text();
        // 获取客户端发来的消息
        DataContent dataContent = JSONObject.parseObject(content,DataContent.class);
        // 异步消息
        dataContentEvent.setDataContent(dataContent);
        applicationContext.publishEvent(dataContentEvent);
        // 业务操作
        Integer action = dataContent.getAction();
        Channel channel =  ctx.channel();
        // 判断消息类型，根据不同的类型来处理不同的业务
        if(action == MsgActionEnum.CONNECT.TYPE){
            // 初始化channel，把用的channel 和 userid 关联起来
            Long fromUserId = dataContent.getChatMsg().getFromUserId();
            UserChanelRel.put(fromUserId,channel);
            log.info("用户id为【"+fromUserId+"】初始化成功");
        } else if(action == MsgActionEnum.CHAT.TYPE){
            // 聊天类型的消息
            ChatMsg chatMsg = dataContent.getChatMsg();
            Long toUserId = chatMsg.getToUserId();
            //发送消息
            Channel receiverChannel = UserChanelRel.get(toUserId);
            if(Objects.nonNull(receiverChannel)){
                //当receiverChannel 不为空的时候，从ChannelGroup 去查找对应的channel 是否存在
                Channel findChanel = users.find(receiverChannel.id());
                if(findChanel!=null){
                    //用户在线
                    receiverChannel.writeAndFlush(
                            new TextWebSocketFrame(
                                    JSONObject.toJSONString(dataContent)
                            )
                    );
                }
            }
            log.info("发送消息用户id【"+chatMsg.getFromUserId()+"】接收消息用户id：【"+chatMsg.getToUserId()+"】聊天数据包：【"+chatMsg.toString()+"】");
        } else if(action == MsgActionEnum.SIGNED.TYPE){
            // 签收消息类型
            ChatMsg chatMsg = dataContent.getChatMsg();
            log.info("接收消息用户id【"+chatMsg.getFromUserId()+"】签收消息");
        } else if(action == MsgActionEnum.KEEP_ALIVE.TYPE){
            // 心跳类型的消息
            log.info("收到来自channel 为【"+channel+"】的心跳包");
        }

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        users.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        String chanelId = ctx.channel().id().asShortText();
        log.info("客户端被移除：channel id 为："+chanelId);
        users.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        //发生了异常后关闭连接，同时从channelgroup移除
        ctx.channel().close();
        users.remove(ctx.channel());
    }
}