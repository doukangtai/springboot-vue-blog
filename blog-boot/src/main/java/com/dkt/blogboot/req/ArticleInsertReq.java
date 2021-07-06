package com.dkt.blogboot.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author 窦康泰
 * @date 2021/07/04
 */
public class ArticleInsertReq {
    private Integer id;

    @NotBlank(message = "文章标题不能为空")
    private String title;

    @NotBlank(message = "文章内容不能为空")
    private String content;

    @NotNull(message = "文章类别id不能为空")
    private Integer categoryId;

    @NotEmpty(message = "文章标签id数组不能为空")
    private Integer[] tagIdArr;

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer[] getTagIdArr() {
        return tagIdArr;
    }

    public void setTagIdArr(Integer[] tagIdArr) {
        this.tagIdArr = tagIdArr;
    }
}
