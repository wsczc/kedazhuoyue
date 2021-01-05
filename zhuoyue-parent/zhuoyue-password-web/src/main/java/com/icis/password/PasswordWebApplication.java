package com.icis.password;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//认证中心启动类
@SpringBootApplication
@EnableDubbo
public class PasswordWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(PasswordWebApplication.class,args);
    }
}
