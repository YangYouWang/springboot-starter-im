package io.github.yangyouwang.springbootstarterim.config;

import io.github.yangyouwang.springbootstarterim.core.WSServerInitialzer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author yangyouwang
 * @title: AutoConfigureImport
 * @projectName springboot-starter-im
 * @description: 主配置
 * @date 2021/3/94:05 PM
 */
@Configuration
@ConditionalOnClass({NioEventLoopGroup.class,ServerBootstrap.class})
public class AutoConfigureImport {

    @Bean("mainGroup")
    @ConditionalOnMissingBean
    public NioEventLoopGroup mainGroup() {
        return new NioEventLoopGroup();
    }

    @Bean("subGroup")
    @ConditionalOnMissingBean
    public NioEventLoopGroup subGroup() {
        return new NioEventLoopGroup();
    }

    @Bean("server")
    @ConditionalOnMissingBean
    public ServerBootstrap server(NioEventLoopGroup mainGroup,NioEventLoopGroup subGroup) {
        return new ServerBootstrap().group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WSServerInitialzer());
    }
}
