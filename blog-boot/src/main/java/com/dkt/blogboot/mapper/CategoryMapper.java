package com.dkt.blogboot.mapper;

import com.dkt.blogboot.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
@Author 窦康泰
@Date 2020-08-04 18:33
*/
@Mapper
public interface CategoryMapper {
    List<Category> selectAll();

    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}