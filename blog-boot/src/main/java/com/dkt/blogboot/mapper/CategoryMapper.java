package com.dkt.blogboot.mapper;
import com.dkt.blogboot.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*@author 窦康泰
*@date 2021/07/04
*/
@Repository
public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    List<Category> selectAll();


}