package com.example.demo;

import io.github.yangyouwang.springbootstarterim.NettyBooter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class DemoApplication implements ApplicationRunner {

	@Resource
	private NettyBooter nettyBooter;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		nettyBooter.start();
	}
}
