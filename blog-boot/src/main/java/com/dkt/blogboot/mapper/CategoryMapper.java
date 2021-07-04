package com.dkt.blogboot.mapper;

import com.dkt.blogboot.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
@Author 窦康泰
@Date 2020-08-04 18:33
*/
@Repository
public interface CategoryMapper {
    List<Category> selectAll();

    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    List<Category> selectByCategory(String category);
}