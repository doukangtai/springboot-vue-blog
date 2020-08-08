package com.dkt.blogboot.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
@Author 窦康泰
@Date 2020-08-05 14:53
*/
public class Article {
    private Integer id;

    private String title;

    private String content;

    private Date date;

    private int categoryId;

    private int[] tagIdArr;

    private Category category;

    private List<Tag> tags;

    private String time;

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", categoryId=" + categoryId +
                ", tagIdArr=" + Arrays.toString(tagIdArr) +
                ", category=" + category +
                ", tags=" + tags +
                ", time='" + time + '\'' +
                '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int[] getTagIdArr() {
        return tagIdArr;
    }

    public void setTagIdArr(int[] tagIdArr) {
        this.tagIdArr = tagIdArr;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}