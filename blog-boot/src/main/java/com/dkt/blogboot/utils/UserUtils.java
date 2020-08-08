package com.dkt.blogboot.utils;

import com.dkt.blogboot.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author 窦康泰
 * @Date 2020-08-01 14:03
 */
public class UserUtils {

    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
