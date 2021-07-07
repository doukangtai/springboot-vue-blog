package com.dkt.blogboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = {"com.dkt.blogboot.mapper"})
@EnableScheduling
public class BlogBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogBootApplication.class, args);
    }

}
