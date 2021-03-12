package io.github.yangyouwang.springbootstarterim.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yangyouwang
 * @title: NettyProperties
 * @projectName springboot-starter-im
 * @description:
 * @date 2021/3/92:39 PM
 */
@Data
@ConfigurationProperties(prefix = "im.netty")
public class NettyProperties {

    /**
     * 端口
     */
    private Integer port;
}
