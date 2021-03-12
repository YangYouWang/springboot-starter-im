package com.example.demo;

import io.github.yangyouwang.springbootstarterim.config.NettyBooter;
import io.github.yangyouwang.springbootstarterim.utils.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		NettyBooter nettyBooter = SpringUtil.getBean(NettyBooter.class);
		nettyBooter.start();
	}

}
