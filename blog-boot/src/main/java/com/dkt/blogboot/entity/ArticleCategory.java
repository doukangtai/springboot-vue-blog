package com.dkt.blogboot.entity;

/**
@Author 窦康泰
@Date 2020-08-05 15:37
*/
public class ArticleCategory {
    private Integer id;

    private Integer aid;

    private Integer cid;

    public ArticleCategory() {
    }

    public ArticleCategory(Integer aid, Integer cid) {
        this.aid = aid;
        this.cid = cid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }
}