//package com.dkt.blogboot.util;
//
//
//import com.jiawa.wiki.resp.UserLoginResp;
//
//import java.io.Serializable;
//
//public class LoginUserContext implements Serializable {
//
//    private static ThreadLocal<UserLoginResp> user = new ThreadLocal<>();
//
//    public static UserLoginResp getUser() {
//        return user.get();
//    }
//
//    public static void setUser(UserLoginResp user) {
//        LoginUserContext.user.set(user);
//    }
//
//}
