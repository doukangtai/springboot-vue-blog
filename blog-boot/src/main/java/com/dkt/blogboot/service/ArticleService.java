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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author 窦康泰
 * @Date 2020-08-05 15:22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ArticleService {

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ArticleCategoryMapper articleCategoryMapper;
    @Autowired
    ArticleTagMapper articleTagMapper;

    public PageResp<ArticleQueryResp> listSubstringContent(ArticleQueryReq req) {
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
        return pageResp;
    }

    public ArticleQueryResp getArticleById(Integer id) {
        return articleMapper.selectOneById(id);
    }

    public List<ArticleQueryResp> getArticleByCategoryId(Integer id) {
        List<Article> articles = articleMapper.selectAllByCategoryId(id);
        return CopyUtil.copyList(articles, ArticleQueryResp.class);
    }

    public List<ArticleQueryResp> getArticleByTagId(Integer id) {
        List<Article> articles = articleMapper.selectAllByTagId(id);
        return CopyUtil.copyList(articles, ArticleQueryResp.class);
    }


    public List<ArticleQueryResp> list() {
        List<Article> articles = articleMapper.selectAll();
        return CopyUtil.copyList(articles, ArticleQueryResp.class);
    }

    public PageResp<ArticleQueryResp> getAllArticleByTitle(ArticleQueryReq req) {
        int page = req.getPage();
        int size = req.getSize();
        List<ArticleQueryResp> articleQueryResps = articleMapper.selectByTitle(page * size, size, req.getTitle());
        PageResp<ArticleQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(articleMapper.countByTitleLike(req.getTitle()));
        pageResp.setList(articleQueryResps);
        return pageResp;
    }

    public CommonResp save(ArticleInsertReq req) {
        CommonResp resp = new CommonResp();
        if (req.getId() == -1) {
            insert(req);
            resp.setMessage("新增文章成功");
            return resp;
        } else {
            update(req);
            resp.setMessage("修改文章成功");
            return resp;
        }
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
    }
}
