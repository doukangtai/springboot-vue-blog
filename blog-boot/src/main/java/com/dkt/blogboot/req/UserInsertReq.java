package com.dkt.blogboot.req;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
*@author 窦康泰
*@date 2021/07/04
*/
public class UserInsertReq {
    private Integer id;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @Length(min = 6, max = 32, message = "密码长度不符")
    private String password;

    @NotBlank(message = "昵称不能为空")
    private String nickname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}