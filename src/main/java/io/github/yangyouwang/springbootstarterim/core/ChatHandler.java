package io.github.yangyouwang.springbootstarterim.core;

import com.alibaba.fastjson.JSONObject;
import io.github.yangyouwang.springbootstarterim.constant.MsgActionEnum;
import io.github.yangyouwang.springbootstarterim.bean.DataContent;
import io.github.yangyouwang.springbootstarterim.factory.MsgContextFactory;
import io.github.yangyouwang.springbootstarterim.utils.SpringUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;


/**
 * 用于处理消息的handler
 * @author yangyouwang
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    /**
     * 用于记录和管理所有客户端的channel
     */
    public static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static ChannelHandlerContext ctx;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        String content = msg.text();
        this.ctx = ctx;
        // 获取客户端发来的消息
        DataContent dataContent = JSONObject.parseObject(content,DataContent.class);
        // 业务操作
        MsgActionEnum msgActionEnum = MsgActionEnum.getEnum(dataContent.getAction());
        MsgContextFactory msgContextFactory = SpringUtil.getBean(MsgContextFactory.class);
        msgContextFactory.getMsgStrategy(msgActionEnum).doAction(dataContent);
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
