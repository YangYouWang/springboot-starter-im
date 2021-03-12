package io.github.yangyouwang.springbootstarterim.core;

import com.alibaba.fastjson.JSONObject;
import io.github.yangyouwang.springbootstarterim.constant.MsgActionEnum;
import io.github.yangyouwang.springbootstarterim.bean.ChatMsg;
import io.github.yangyouwang.springbootstarterim.bean.DataContent;
import io.github.yangyouwang.springbootstarterim.constant.MsgStatusEnum;
import io.github.yangyouwang.springbootstarterim.constant.MsgTypeEnum;
import io.github.yangyouwang.springbootstarterim.utils.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.context.ApplicationContext;

/**
 * 用于处理消息的handler
 * @author yangyouwang
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    /**
     * 用于记录和管理所有客户端的channel
     */
    public static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 获取容器
     */
    private static final ApplicationContext applicationContext;

    static {
        applicationContext = SpringUtil.getApplicationContext();

    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        String content = msg.text();
        // 获取客户端发来的消息
        DataContent dataContent = JSONObject.parseObject(content,DataContent.class);
        // 业务操作
        DataContentEvent dataContentEvent = DataContentEvent.getInstance();
        MsgActionEnum msgActionEnum = MsgActionEnum.getEnum(dataContent.getAction());
        dataContentEvent.setAction(msgActionEnum);
        // 判断消息类型，根据不同的类型来处理不同的业务
        if (msgActionEnum == MsgActionEnum.CONNECT) {
            Long fromUserId = dataContent.getChatMsg().getFromUserId();
            Channel channel =  ctx.channel();
            UserChanelRel.put(fromUserId,channel);
            // 初始化
            dataContentEvent.setFromUserId(fromUserId);
            applicationContext.publishEvent(dataContentEvent);
        }
        if (msgActionEnum == MsgActionEnum.CHAT) {
            // 聊天类型的消息
            ChatMsg chatMsg = dataContent.getChatMsg();
            Long toUserId = chatMsg.getToUserId();
            Long fromUserId = chatMsg.getFromUserId();
            //发送消息
            Channel receiverChannel = UserChanelRel.get(toUserId);
            if(receiverChannel!= null){
                Channel findChanel = users.find(receiverChannel.id());
                if(findChanel!=null){
                    //用户在线
                    receiverChannel.writeAndFlush(
                            new TextWebSocketFrame(
                                    JSONObject.toJSONString(dataContent)
                            )
                    );
                }
                // 用户离线
                dataContentEvent.setFromUserId(fromUserId);
                dataContentEvent.setToUserId(toUserId);
                MsgTypeEnum msgTypeEnum = MsgTypeEnum.getEnum(chatMsg.getType());
                dataContentEvent.setType(msgTypeEnum);
                dataContentEvent.setStatus(MsgStatusEnum.MSG_STATUS_UNREAD);
                dataContentEvent.setMessage(chatMsg.getMessage());
                applicationContext.publishEvent(dataContentEvent);
            }
        }
        if (msgActionEnum == MsgActionEnum.SIGNED) {
            ChatMsg chatMsg = dataContent.getChatMsg();
            Long toUserId = chatMsg.getToUserId();
            Long fromUserId = chatMsg.getFromUserId();
            // 签收消息类型
            dataContentEvent.setStatus(MsgStatusEnum.MSG_STATUS_HAVE_READ);
            dataContentEvent.setFromUserId(fromUserId);
            dataContentEvent.setToUserId(toUserId);
            applicationContext.publishEvent(dataContentEvent);
        }
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
