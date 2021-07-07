package com.dkt.blogboot.controller;

import com.dkt.blogboot.req.UserInsertReq;
import com.dkt.blogboot.req.UserLoginReq;
import com.dkt.blogboot.resp.CommonResp;
import com.dkt.blogboot.resp.UserLoginResp;
import com.dkt.blogboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @Author 窦康泰
 * @Date 2020-08-01 13:17
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    RedisTemplate redisTemplate;

    @PostMapping("/reg")
    public CommonResp reg(@Valid @RequestBody UserInsertReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes(StandardCharsets.UTF_8)));
        CommonResp<Object> resp = new CommonResp<>();
        userService.reg(req);
        return resp;
    }

    @PostMapping("/login")
    public CommonResp login(@Valid @RequestBody UserLoginReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes(StandardCharsets.UTF_8)));
        CommonResp resp = userService.login(req);
        UserLoginResp userLoginResp = (UserLoginResp) resp.getContent();
        redisTemplate.opsForValue().set(userLoginResp.getToken(), userLoginResp, 3600 * 24, TimeUnit.SECONDS);
        return resp;
    }

    @GetMapping("/logout/{token}")
    public CommonResp logout(@PathVariable("token") String token) {
        CommonResp resp = new CommonResp<>();
        redisTemplate.delete(token);
        return resp;
    }
}
