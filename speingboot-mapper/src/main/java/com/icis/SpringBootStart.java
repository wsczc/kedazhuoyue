package com.icis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//开启包扫描
@MapperScan(basePackages = "com.icis.mapper")
public class SpringBootStart {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootStart.class,args);
    }
}
