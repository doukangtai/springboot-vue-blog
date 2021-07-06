//package com.dkt.blogboot.service;
//
//import com.dkt.blogboot.mapper.UserMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * @Author 窦康泰
// * @Date 2020-08-01 12:53
// */
//@Service
//@Transactional(rollbackFor = Exception.class)
//public class UserService {
//
//    @Autowired
//    UserMapper userMapper;
//
//    public int reg(User user) {
//        User user1 = userMapper.loadUserByUsername(user.getUsername());
//        if (user1 == null) {
//            int insert = userMapper.insert(user);
//            return insert;
//        }
//        return -1;
//    }
//}
