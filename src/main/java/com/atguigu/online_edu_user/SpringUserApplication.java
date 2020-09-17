package com.atguigu.online_edu_user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@MapperScan(basePackages = "com.atguigu.online_edu_user.edu.mapper")
public class SpringUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringUserApplication.class, args);
    }
}
