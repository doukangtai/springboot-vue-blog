//package com.dkt.blogboot.controller;
//
//import com.dkt.blogboot.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//
///**
// * @Author 窦康泰
// * @Date 2020-08-01 13:17
// */
//@RestController
//public class UserController {
//
//    @Autowired
//    UserService userService;
//
//    @GetMapping("/reg")
//    public String reg(String username, String password, String nickname) {
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setNickname(nickname);
//        int reg = userService.reg(user);
//        if (reg == -1) {
//            return "此用户已存在";
//        } else if (reg == 1){
//            return "注册成功";
//        } else {
//            return "注册失败";
//        }
//    }
//
//    @RequestMapping("/loginPage")
//    public void loginPage(HttpServletResponse httpServletResponse) throws IOException {
//        httpServletResponse.setContentType("application/json;charset=utf-8");
//        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        PrintWriter writer = httpServletResponse.getWriter();
//        writer.write("{\"status\":\"error\",\"msg\":\"请先登录\"}");
//        writer.flush();
//        writer.close();
//    }
//
//}
