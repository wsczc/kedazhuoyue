package com.icis;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

import java.sql.PreparedStatement;
//服务器生产方法  应用启动类
@SpringBootApplication
//开启springBoot对dubbo支持
@EnableDubbo
//开启mapper扫描  通用mapper的
@MapperScan("com.icis.mapper")
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class,args);
    }
}
