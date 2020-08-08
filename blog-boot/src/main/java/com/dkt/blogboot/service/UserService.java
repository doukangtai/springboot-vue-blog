package com.dkt.blogboot.service;

import com.dkt.blogboot.entity.User;
import com.dkt.blogboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author 窦康泰
 * @Date 2020-08-01 12:53
 */
@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("当前用户不存在");
        }
        return user;
    }

    public int reg(User user) {
        User user1 = userMapper.loadUserByUsername(user.getUsername());
        if (user1 == null) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            int insert = userMapper.insert(user);
            return insert;
        }
        return -1;
    }
}
