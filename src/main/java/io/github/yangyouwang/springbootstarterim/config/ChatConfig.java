package io.github.yangyouwang.springbootstarterim.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author yangyouwang
 * @title: ChatConfig
 * @projectName springboot-starter-im
 * @description: 系统配置
 * @date 2021/3/173:38 PM
 */
@Configuration
@Import({NettyConfig.class,MsgConfig.class})
@EnableConfigurationProperties(NettyProperties.class)
@ComponentScan(basePackages = "io.github.yangyouwang.springbootstarterim")
public class ChatConfig {
}
