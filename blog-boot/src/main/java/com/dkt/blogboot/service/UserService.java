package com.dkt.blogboot.service;

import com.dkt.blogboot.entity.User;
import com.dkt.blogboot.mapper.UserMapper;
import com.dkt.blogboot.req.UserInsertReq;
import com.dkt.blogboot.req.UserLoginReq;
import com.dkt.blogboot.resp.CommonResp;
import com.dkt.blogboot.resp.UserLoginResp;
import com.dkt.blogboot.util.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @Author 窦康泰
 * @Date 2020-08-01 12:53
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void reg(UserInsertReq req) {
        User user = CopyUtil.copy(req, User.class);
        userMapper.insert(user);
    }

    public CommonResp login(UserLoginReq req) {
        List<User> users = userMapper.selectByUsername(req.getUsername());
        User user;
        CommonResp<Object> resp = new CommonResp<>();
        if (users.size() > 0) {
            user = users.get(0);
            if (req.getPassword().equals(user.getPassword())) {
                UserLoginResp userLoginResp = CopyUtil.copy(user, UserLoginResp.class);
                userLoginResp.setToken(UUID.randomUUID().toString());
                resp.setContent(userLoginResp);
                return resp;
            }
        }
        resp.setSuccess(false);
        resp.setMessage("用户不存在，或密码错误");
        return resp;
    }
}
