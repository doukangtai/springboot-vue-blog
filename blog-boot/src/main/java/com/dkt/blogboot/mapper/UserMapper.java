package com.dkt.blogboot.mapper;

import com.dkt.blogboot.entity.User;
import org.springframework.stereotype.Repository;

/**
*@author 窦康泰
*@date 2021/07/04
*/
@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}