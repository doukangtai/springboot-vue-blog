package com.dkt.blogboot.service;

import com.dkt.blogboot.entity.Article;
import com.dkt.blogboot.entity.ArticleCategory;
import com.dkt.blogboot.entity.ArticleTag;
import com.dkt.blogboot.mapper.ArticleCategoryMapper;
import com.dkt.blogboot.mapper.ArticleMapper;
import com.dkt.blogboot.mapper.ArticleTagMapper;
import com.dkt.blogboot.req.ArticleInsertReq;
import com.dkt.blogboot.req.ArticleQueryReq;
import com.dkt.blogboot.resp.ArticleQueryResp;
import com.dkt.blogboot.resp.CommonResp;
import com.dkt.blogboot.resp.PageResp;
import com.dkt.blogboot.util.CopyUtil;
import com.dkt.blogboot.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author 窦康泰
 * @Date 2020-08-05 15:22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ArticleService {
    private final static Logger log = LoggerFactory.getLogger(ArticleService.class);

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ArticleCategoryMapper articleCategoryMapper;
    @Autowired
    ArticleTagMapper articleTagMapper;
    @Autowired
    RedisTemplate redisTemplate;

    private Integer getViewCountFromRedis(Integer id) {
        Integer viewCount = (Integer) redisTemplate.opsForValue().get(RedisUtil.VIEW + id);
        return viewCount == null ? 0 : viewCount;
    }

    private long getPraiseCountFromRedis(Integer id) {
        Long size = redisTemplate.opsForSet().size(RedisUtil.PRAISE + id);
        return size == null ? 0 : size;
    }

    public PageResp<ArticleQueryResp> listSubstringContent(ArticleQueryReq req) {
        String key = getKeyListSubstringContent(req);
        PageResp<ArticleQueryResp> result = (PageResp<ArticleQueryResp>) redisTemplate.opsForValue().get(key);
        if (result != null) {
            for (ArticleQueryResp articleQueryResp : result.getList()) {
                addViewAndPraiseCount(articleQueryResp);
            }
            return result;
        }
        int page = req.getPage();
        int size = req.getSize();
        List<ArticleQueryResp> articles = articleMapper.select(page * size, size);
        for (ArticleQueryResp article : articles) {
            if (article.getContent().length() > 300) {
                article.setContent(article.getContent().substring(0, 300));
            }
        }
        PageResp<ArticleQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(articleMapper.count());
        pageResp.setList(articles);
        redisTemplate.opsForValue().set(key, pageResp, 30, TimeUnit.MINUTES);
        for (ArticleQueryResp article : articles) {
            addViewAndPraiseCount(article);
        }
        return pageResp;
    }

    private void addViewAndPraiseCount(ArticleQueryResp articleQueryResp) {
        Integer articleId = articleQueryResp.getId();
        Integer viewCountFromRedis = getViewCountFromRedis(articleId);
        long praiseCountFromRedis = getPraiseCountFromRedis(articleId);
        articleQueryResp.setView(articleQueryResp.getView() + viewCountFromRedis);
        articleQueryResp.setPraise((int) (articleQueryResp.getPraise() + praiseCountFromRedis));
    }

    private String getKeyListSubstringContent(ArticleQueryReq req) {
        return RedisUtil.PREFIX + req.getTitle() + "::" + req.getSize() + "::" + req.getPage();
    }

    public ArticleQueryResp getArticleById(Integer id) {
        long timeBlock = System.currentTimeMillis() / (1000 * 60);
//        long timeBlock = System.currentTimeMillis() / 1000;
        Map<Integer, Integer> map = RedisUtil.VIEW_MAP.get(timeBlock);
        if (CollectionUtils.isEmpty(map)) {
            map = new ConcurrentHashMap<>();
            map.put(id, new Integer(1));
            RedisUtil.VIEW_MAP.put(timeBlock, map);
        } else {
            Integer val = map.get(id);
            if (val == null) {
                map.put(id, new Integer(1));
            } else {
                map.put(id, val + 1);
            }
        }
        String key = RedisUtil.PREFIX + "getArticleById::" + id;
        ArticleQueryResp result = (ArticleQueryResp) redisTemplate.opsForValue().get(key);
        if (result != null) {
            addViewAndPraiseCount(result);
            return result;
        }
        ArticleQueryResp articleQueryResp = articleMapper.selectOneById(id);
        redisTemplate.opsForValue().set(key, articleQueryResp, 30, TimeUnit.MINUTES);
        if (articleQueryResp != null) {
            addViewAndPraiseCount(articleQueryResp);
        }
        return articleQueryResp;
    }

    public List<ArticleQueryResp> getArticleByCategoryId(Integer id) {
        String key = RedisUtil.PREFIX + "getArticleByCategoryId::" + id;
        List<ArticleQueryResp> result = (List<ArticleQueryResp>) redisTemplate.opsForValue().get(key);
        if (result != null) {
            return result;
        }
        List<Article> articles = articleMapper.selectAllByCategoryId(id);
        List<ArticleQueryResp> articleQueryResps = CopyUtil.copyList(articles, ArticleQueryResp.class);
        redisTemplate.opsForValue().set(key, articleQueryResps, 30, TimeUnit.MINUTES);
        return articleQueryResps;
    }

    public List<ArticleQueryResp> getArticleByTagId(Integer id) {
        String key = RedisUtil.PREFIX + "getArticleByTagId::" + id;
        List<ArticleQueryResp> result = (List<ArticleQueryResp>) redisTemplate.opsForValue().get(key);
        if (result != null) {
            return result;
        }
        List<Article> articles = articleMapper.selectAllByTagId(id);
        List<ArticleQueryResp> articleQueryResps = CopyUtil.copyList(articles, ArticleQueryResp.class);
        redisTemplate.opsForValue().set(key, articleQueryResps, 30, TimeUnit.MINUTES);
        return articleQueryResps;
    }


    public List<ArticleQueryResp> list() {
        String key = RedisUtil.PREFIX + "list::List<ArticleQueryResp>";
        List<ArticleQueryResp> result = (List<ArticleQueryResp>) redisTemplate.opsForValue().get(key);
        if (result != null) {
            return result;
        }
        List<Article> articles = articleMapper.selectAll();
        List<ArticleQueryResp> articleQueryResps = CopyUtil.copyList(articles, ArticleQueryResp.class);
        redisTemplate.opsForValue().set(key, articleQueryResps, 30, TimeUnit.MINUTES);
        return articleQueryResps;
    }

    public PageResp<ArticleQueryResp> getAllArticleByTitle(ArticleQueryReq req) {
        String key = getKeyListSubstringContent(req);
        PageResp<ArticleQueryResp> result = (PageResp<ArticleQueryResp>) redisTemplate.opsForValue().get(key);
        if (result != null) {
            return result;
        }
        int page = req.getPage();
        int size = req.getSize();
        List<ArticleQueryResp> articleQueryResps = articleMapper.selectByTitle(page * size, size, req.getTitle());
        PageResp<ArticleQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(articleMapper.countByTitleLike(req.getTitle()));
        pageResp.setList(articleQueryResps);
        redisTemplate.opsForValue().set(key, pageResp, 30, TimeUnit.MINUTES);
        return pageResp;
    }

    public CommonResp save(ArticleInsertReq req) {
        CommonResp resp = new CommonResp();
        if (req.getId() == -1) {
            insert(req);
            resp.setMessage("新增文章成功");
        } else {
            update(req);
            resp.setMessage("修改文章成功");
        }
        RedisUtil.invalidation(redisTemplate);
        return resp;
    }

    private void update(ArticleInsertReq req) {
        Article article = new Article();
        article.setId(req.getId());
        article.setTitle(req.getTitle());
        article.setContent(req.getContent());
        articleMapper.updateByPrimaryKeySelective(article);
        articleCategoryMapper.deleteByAid(req.getId());
        ArticleCategory articleCategory = new ArticleCategory();
        articleCategory.setAid(req.getId());
        articleCategory.setCid(req.getCategoryId());
        articleCategoryMapper.insertSelective(articleCategory);
        articleTagMapper.deleteByAid(req.getId());
        for (int i = 0; i < req.getTagIdArr().length; i++) {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setAid(req.getId());
            articleTag.setTid(req.getTagIdArr()[i]);
            articleTagMapper.insertSelective(articleTag);
        }
    }

    public void insert(ArticleInsertReq req) {
        Article article = new Article();
        article.setTitle(req.getTitle());
        article.setContent(req.getContent());
        article.setDate(new Date());
        article.setView(0);
        article.setPraise(0);
        articleMapper.insertSelective(article);
        ArticleCategory articleCategory = new ArticleCategory();
        articleCategory.setAid(article.getId());
        articleCategory.setCid(req.getCategoryId());
        articleCategoryMapper.insertSelective(articleCategory);
        for (int i = 0; i < req.getTagIdArr().length; i++) {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setAid(article.getId());
            articleTag.setTid(req.getTagIdArr()[i]);
            articleTagMapper.insertSelective(articleTag);
        }
    }

    public void delete(Integer id) {
        articleCategoryMapper.deleteByAid(id);
        articleTagMapper.deleteByAid(id);
        articleMapper.deleteByPrimaryKey(id);
        RedisUtil.invalidation(redisTemplate);
    }

    private String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public CommonResp praiseArticle(HttpServletRequest request, @PathVariable("id") Integer id) {
        CommonResp<Object> resp = new CommonResp<>();
        String remoteIp = getRemoteIp(request);
        String key = RedisUtil.PRAISE + id;
        Long add = redisTemplate.opsForSet().add(key, remoteIp);
        if (add == 1) {
            resp.setMessage("点赞成功");
        } else {
            resp.setSuccess(false);
            resp.setMessage("已经点过赞了，请明天再来");
        }
        return resp;
    }
}
