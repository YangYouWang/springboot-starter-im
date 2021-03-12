package io.github.yangyouwang.springbootstarterim.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;

/**
 * @author yangyouwang
 * @title: AutoConfigrutionClass
 * @projectName springboot-starter-im
 * @description: 配置类
 * @date 2021/3/92:39 PM
 */
@Log4j2
@Configuration
@Import({AutoConfigureImport.class})
@EnableConfigurationProperties(NettyProperties.class)
public class AutoConfigure implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private NettyProperties nettyProperties;

    @Resource
    private ServerBootstrap server;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (null == nettyProperties.getPort()) {
            throw new RuntimeException("请配置netty端口～");
        }
        if (null == event.getApplicationContext().getParent()) {
            try {
                ChannelFuture future = server.bind(nettyProperties.getPort()).sync();
                if (future.isSuccess()) {
                    log.info("启动 Netty 成功");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
