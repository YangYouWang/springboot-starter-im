package io.github.yangyouwang.springbootstarterim.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yangyouwang
 * @title: NettyProperties
 * @projectName springboot-starter-im
 * @description:
 * @date 2021/3/92:39 PM
 */
@ConfigurationProperties(prefix = "im.netty")
public class NettyProperties {

    /**
     * 端口
     */
    private Integer port;
    /**
     * 路径
     */
    private String path;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
