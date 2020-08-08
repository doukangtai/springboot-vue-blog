package com.dkt.blogboot.mapper;

import com.dkt.blogboot.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
@Author 窦康泰
@Date 2020-08-04 18:33
*/
@Mapper
public interface TagMapper {
    List<Tag> selectAll();

    int deleteByPrimaryKey(Integer id);

    int insert(Tag record);

    int insertSelective(Tag record);

    Tag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);
}