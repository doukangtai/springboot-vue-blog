package com.dkt.blogboot.util;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RedisUtil {
    public static final String PRAISE = "praise::";

    public static final String VIEW = "view::";

    public static final Map<Long, Map<Integer, Integer>> VIEW_MAP = new ConcurrentHashMap<>();
}
