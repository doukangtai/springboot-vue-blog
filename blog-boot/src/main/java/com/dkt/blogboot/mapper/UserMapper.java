package com.dkt.blogboot.mapper;

import com.dkt.blogboot.entity.User;
import org.springframework.stereotype.Repository;

/**
@Author 窦康泰
@Date 2020-08-01 12:59
*/
@Repository
public interface UserMapper {
    User loadUserByUsername(String username);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}