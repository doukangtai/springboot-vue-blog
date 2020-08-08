package com.dkt.blogboot.service;

import com.dkt.blogboot.entity.Article;
import com.dkt.blogboot.entity.ArticleCategory;
import com.dkt.blogboot.mapper.ArticleCategoryMapper;
import com.dkt.blogboot.mapper.ArticleMapper;
import com.dkt.blogboot.mapper.ArticleTagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Author 窦康泰
 * @Date 2020-08-05 15:22
 */
@Service
@Transactional
public class ArticleService {

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ArticleCategoryMapper articleCategoryMapper;
    @Autowired
    ArticleTagMapper articleTagMapper;

    public int addArticle(Article article) {
        if (article.getId() == -1) {
            article.setDate(new Date(new java.util.Date().getTime()));
            int insert = articleMapper.insert(article);
            ArticleCategory articleCategory = new ArticleCategory(article.getId(), article.getCategoryId());
            int insert1 = articleCategoryMapper.insert(articleCategory);
            int inserts = articleTagMapper.inserts(article.getId(), article.getTagIdArr());
            return insert;
        } else {
            int updateArticle = articleMapper.updateByPrimaryKey(article);
            int deleteByAid = articleCategoryMapper.deleteByAid(article.getId());
            ArticleCategory articleCategory = new ArticleCategory(article.getId(), article.getCategoryId());
            int insert = articleCategoryMapper.insert(articleCategory);
            int deleteByAid1 = articleTagMapper.deleteByAid(article.getId());
            int inserts = articleTagMapper.inserts(article.getId(), article.getTagIdArr());
            return updateArticle;
        }
    }

    public List<Article> getAllArticle() {
        List<Article> allArticle = articleMapper.getAllArticle();
        return formatDate(allArticle);
    }

    public List<Article> getArticleByTitle(String title) {
        List<Article> articleList = articleMapper.getArticleByTitle(title);
        return formatDate(articleList);
    }

    public List<Article> formatDate(List<Article> articleList) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Article article : articleList) {
            article.setTime(sdf.format(article.getDate()));
        }
        return articleList;
    }

    public Article formatDate(Article article) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        article.setTime(sdf.format(article.getDate()));
        return article;
    }

    public Article getArticleById(int id) {
        Article article = articleMapper.getArticleById(id);
        return formatDate(article);
    }

    public int deleteArticleById(int id) {
        int deleteByAid = articleCategoryMapper.deleteByAid(id);
        int deleteByAid1 = articleTagMapper.deleteByAid(id);
        int deleteByPrimaryKey = articleMapper.deleteByPrimaryKey(id);
        return deleteByPrimaryKey;
    }

    public List<Article> getAllArticleSubstringContent() {
        List<Article> allArticle = articleMapper.getAllArticle();
        for (Article article : allArticle) {
            if (article.getContent().length() > 300) {
                article.setContent(article.getContent().substring(0, 300));
            } else {
                article.setContent(article.getContent().substring(0, article.getContent().length()));
            }
        }
        return formatDate(allArticle);
    }

    public List<Article> getArticleByCategoryId(int id) {
        List<Article> articleList = articleMapper.getArticleByCategoryId(id);
        return formatDate(articleList);
    }

    public List<Article> getArticleByTagId(int id) {
        List<Article> articleList = articleMapper.getArticleByTagId(id);
        return formatDate(articleList);
    }

}
