package com.dkt.blogboot.req;

/**
 * @author 窦康泰
 * @date 2021/07/04
 */
public class ArticleQueryReq extends PageReq {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
