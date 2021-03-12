package io.github.yangyouwang.springbootstarterim.core;

import com.alibaba.fastjson.JSONObject;
import io.github.yangyouwang.springbootstarterim.constant.MsgActionEnum;
import io.github.yangyouwang.springbootstarterim.bean.ChatMsg;
import io.github.yangyouwang.springbootstarterim.bean.DataContent;
import io.github.yangyouwang.springbootstarterim.constant.MsgStatusEnum;
import io.github.yangyouwang.springbootstarterim.utils.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.context.ApplicationContext;

import java.util.Objects;

/**
 * 用于处理消息的handler
 * @author yangyouwang
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    /**
     * 用于记录和管理所有客户端的channel
     */
    public static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        String content = msg.text();
        // 获取客户端发来的消息
        DataContent dataContent = JSONObject.parseObject(content,DataContent.class);
        // 业务操作
        Integer action = dataContent.getAction();
        Channel channel =  ctx.channel();
        // 判断消息类型，根据不同的类型来处理不同的业务
        if(action == MsgActionEnum.CONNECT.TYPE){
            // 初始化channel，把用的channel 和 userid 关联起来
            Long fromUserId = dataContent.getChatMsg().getFromUserId();
            UserChanelRel.put(fromUserId,channel);
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
        } else if(action == MsgActionEnum.SIGNED.TYPE){
            // 签收消息类型
            ChatMsg chatMsg = dataContent.getChatMsg();
            chatMsg.setStatus(MsgStatusEnum.MSG_STATUS_HAVE_READ.getCODE());
        }
        // 异步消息
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        DataContentEvent dataContentEvent = new DataContentEvent(applicationContext);
        dataContentEvent.setDataContent(dataContent);
        applicationContext.publishEvent(dataContentEvent);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        users.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
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
