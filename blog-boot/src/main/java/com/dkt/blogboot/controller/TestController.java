package com.dkt.blogboot.controller;

import com.dkt.blogboot.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

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
        Set keys = redisTemplate.keys(RedisUtil.PREFIX + "*");
        System.out.println(keys);
        redisTemplate.delete(keys);
    }

    @GetMapping("/test2")
    public void redisTest2() {
        redisTemplate.opsForValue().set(RedisUtil.PREFIX + "aaa::AAA", "aaa");
        redisTemplate.opsForValue().set(RedisUtil.PREFIX + "bbb::BBB", "bbb");
        redisTemplate.opsForValue().set(RedisUtil.PREFIX + "ccc::CCC", "ccc");
    }
}
