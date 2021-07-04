package com.dkt.blogboot.mapper;

import com.dkt.blogboot.entity.ArticleCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
@Author 窦康泰
@Date 2020-08-05 15:37
*/
@Repository
public interface ArticleCategoryMapper {
    int deleteByAid(int aid);

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleCategory record);

    int insertSelective(ArticleCategory record);

    ArticleCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleCategory record);

    int updateByPrimaryKey(ArticleCategory record);

    List<ArticleCategory> selectByCid(int cid);
}