package io.github.yangyouwang.springbootstarterim;

import io.github.yangyouwang.springbootstarterim.config.NettyProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author yangyouwang
 * @title: NettyBooter
 * @projectName springboot-starter-im
 * @description: 启动类
 * @date 2021/3/123:02 PM
 */
@Component
public class NettyBooter {

    @Resource
    private ServerBootstrap server;

    @Resource
    private NettyProperties properties;

    public void start() {
        try {
            if (null == properties.getPort()) {
                throw new RuntimeException("未设置启动端口～");
            }
            if (null == properties.getPath()) {
                throw new RuntimeException("未设置url路径口～");
            }
            ChannelFuture future = server.bind(properties.getPort()).sync();
            if (future.isSuccess()) {
                System.out.println("启动 Netty 成功");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
