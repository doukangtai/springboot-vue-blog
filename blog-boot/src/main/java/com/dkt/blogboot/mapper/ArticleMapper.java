package com.dkt.blogboot.mapper;
import com.dkt.blogboot.entity.Article;
import com.dkt.blogboot.resp.ArticleQueryResp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*@author 窦康泰
*@date 2021/07/04
*/
@Repository
public interface ArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

    List<ArticleQueryResp> select(@Param("offset") Integer offset, @Param("size") Integer size);

    Integer count();

    ArticleQueryResp selectOneById(@Param("id")Integer id);

    List<Article> selectAllByCategoryId(Integer id);

    List<Article> selectAllByTagId(Integer id);

    List<Article> selectAll();

    List<ArticleQueryResp> selectByTitle(@Param("offset") Integer offset, @Param("size") Integer size, @Param("title") String title);

    Integer countByTitleLike(@Param("likeTitle")String likeTitle);


}