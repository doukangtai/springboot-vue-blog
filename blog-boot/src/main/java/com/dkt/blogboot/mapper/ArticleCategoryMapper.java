package com.dkt.blogboot.mapper;

import com.dkt.blogboot.entity.ArticleCategory;
import org.apache.ibatis.annotations.Mapper;

/**
@Author 窦康泰
@Date 2020-08-05 15:37
*/
@Mapper
public interface ArticleCategoryMapper {
    int deleteByAid(int aid);

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleCategory record);

    int insertSelective(ArticleCategory record);

    ArticleCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleCategory record);

    int updateByPrimaryKey(ArticleCategory record);
}