package com.dkt.blogboot.job;

import com.dkt.blogboot.mapper.ArticleMapper;
import com.dkt.blogboot.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

/**
 * @author 窦康泰
 * @date 2021/07/07
 */
@Component
public class ViewJob {
    private final static Logger log = LoggerFactory.getLogger(ViewJob.class);

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    RedisTemplate redisTemplate;

//    @Scheduled(cron = "0 5/5 * * * ?")
    @Scheduled(cron = "0/3 * * * * ?")
    public void transToDb() {
//        long timeBlock = System.currentTimeMillis() / (1000 * 60 * 5);
        long timeBlock = System.currentTimeMillis() / 1000;
        Iterator<Long> iterator = RedisUtil.VIEW_MAP.keySet().iterator();
        while (iterator.hasNext()) {
            Long key = iterator.next();
            if (key < timeBlock) {
                Map<Integer, Integer> map = RedisUtil.VIEW_MAP.remove(key);
                for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                    String k = RedisUtil.VIEW + entry.getKey();
                    redisTemplate.opsForValue().increment(k, entry.getValue());
                }
            }
        }
    }
}
