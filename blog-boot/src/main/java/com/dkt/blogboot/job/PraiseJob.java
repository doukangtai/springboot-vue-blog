package com.dkt.blogboot.job;

import com.dkt.blogboot.entity.Article;
import com.dkt.blogboot.mapper.ArticleMapper;
import com.dkt.blogboot.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 窦康泰
 * @date 2021/07/07
 */
@Component
public class PraiseJob {
    private final static Logger log = LoggerFactory.getLogger(PraiseJob.class);

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    RedisTemplate redisTemplate;

//    @Scheduled(cron = "0 0 2 1/1 * ?")
    @Scheduled(cron = "0 0 1/3 * * ?")
    public void transToDb() {
        List<Article> articles = articleMapper.selectAll();
        for (Article article : articles) {
            String praiseKey = RedisUtil.PRAISE + article.getId();
            Long size = redisTemplate.opsForSet().size(praiseKey);
            Integer viewNum = (Integer) redisTemplate.opsForValue().get(RedisUtil.VIEW + article.getId());
            if (viewNum != null) {
                article.setView(article.getView() + viewNum);
            }
            article.setTitle(null);
            article.setDate(null);
            article.setContent(null);
            article.setPraise((int) (article.getPraise() + size));
            articleMapper.updateByPrimaryKeySelective(article);
            redisTemplate.delete(praiseKey);
            redisTemplate.delete(RedisUtil.VIEW + article.getId());
        }
        RedisUtil.invalidation(redisTemplate);
    }
}
