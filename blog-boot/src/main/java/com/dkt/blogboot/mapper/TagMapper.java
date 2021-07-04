package com.dkt.blogboot.mapper;

import com.dkt.blogboot.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
@Author 窦康泰
@Date 2020-08-04 18:33
*/
@Repository
public interface TagMapper {
    List<Tag> selectAll();

    int deleteByPrimaryKey(Integer id);

    int insert(Tag record);

    int insertSelective(Tag record);

    Tag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);

    List<Tag> selectByTag(String tag);
}