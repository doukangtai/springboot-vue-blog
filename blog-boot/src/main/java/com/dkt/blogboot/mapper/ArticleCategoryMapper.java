package com.dkt.blogboot.mapper;
import com.dkt.blogboot.entity.ArticleCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*@author 窦康泰
*@date 2021/07/04
*/
@Repository
public interface ArticleCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleCategory record);

    int insertSelective(ArticleCategory record);

    ArticleCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleCategory record);

    int updateByPrimaryKey(ArticleCategory record);

    int deleteByAid(@Param("aid")Integer aid);

    List<ArticleCategory> selectByCid(@Param("cid")Integer cid);


}