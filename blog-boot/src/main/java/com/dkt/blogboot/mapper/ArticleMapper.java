package com.dkt.blogboot.mapper;

import com.dkt.blogboot.entity.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
@Author 窦康泰
@Date 2020-08-05 14:53
*/
@Mapper
public interface ArticleMapper {
    List<Article> getArticleByTagId(int id);

    List<Article> getArticleByCategoryId(int id);

    Article getArticleById(int id);

    List<Article> getArticleByTitle(String title);

    List<Article> getAllArticle();

    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);
}