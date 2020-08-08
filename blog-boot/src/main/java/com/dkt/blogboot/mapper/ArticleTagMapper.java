package com.dkt.blogboot.mapper;

import com.dkt.blogboot.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
@Author 窦康泰
@Date 2020-08-05 15:37
*/
@Mapper
public interface ArticleTagMapper {
    int deleteByAid(int aid);

    int inserts(@Param("aid") int aid, @Param("tid") int[] tid);

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleTag record);

    int insertSelective(ArticleTag record);

    ArticleTag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleTag record);

    int updateByPrimaryKey(ArticleTag record);
}