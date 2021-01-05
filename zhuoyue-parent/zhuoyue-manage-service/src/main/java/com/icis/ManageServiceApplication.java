package com.icis;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.icis.service.ManageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

//管理后台 服务提供方启动类
@SpringBootApplication
@EnableDubbo
@MapperScan("com.icis.mapper")
//开启平台事务管理
@EnableTransactionManagement
public class ManageServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageServiceApplication.class,args);
    }

}
