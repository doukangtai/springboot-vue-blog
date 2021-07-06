package com.dkt.blogboot.req;

import javax.validation.constraints.NotBlank;

/**
*@author 窦康泰
*@date 2021/07/04
*/
public class TagInsertReq {
    private Integer id;

    @NotBlank(message = "标签名不能为空")
    private String tag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}