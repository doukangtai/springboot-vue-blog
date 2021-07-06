package com.dkt.blogboot.mapper;
import com.dkt.blogboot.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
*@author 窦康泰
*@date 2021/07/04
*/
@Repository
public interface TagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Tag record);

    int insertSelective(Tag record);

    Tag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);

    List<Tag> selectAll();


}