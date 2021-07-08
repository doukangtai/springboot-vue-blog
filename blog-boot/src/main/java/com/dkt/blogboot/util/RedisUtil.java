package com.dkt.blogboot.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RedisUtil {
    public static final String PREFIX = "BOOTBLOG::";

    public static final String PRAISE = "praise::" + PREFIX;

    public static final String VIEW = "view::" + PREFIX;

    public static final Map<Long, Map<Integer, Integer>> VIEW_MAP = new ConcurrentHashMap<>();

    public static void invalidation(RedisTemplate redisTemplate) {
        Set keys = redisTemplate.keys(PREFIX + "*");
        redisTemplate.delete(keys);
    }
}
