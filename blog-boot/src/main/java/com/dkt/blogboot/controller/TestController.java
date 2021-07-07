package com.dkt.blogboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 窦康泰
 * @date 2021/07/06
 */
@RestController
public class TestController {
    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/test")
    public void redisTest() {
        redisTemplate.delete("mset2");
    }
}
