package com.dkt.blogboot.req;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author 窦康泰
 * @date 2021/07/04
 */
public class ArticleDeleteReq {
    @NotNull(message = "文章id不能为空")
    private Integer id;

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
