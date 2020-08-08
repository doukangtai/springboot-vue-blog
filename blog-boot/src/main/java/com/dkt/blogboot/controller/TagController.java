package com.dkt.blogboot.controller;

import com.dkt.blogboot.entity.ResponseBean;
import com.dkt.blogboot.entity.Tag;
import com.dkt.blogboot.service.ArticleService;
import com.dkt.blogboot.service.CategoryService;
import com.dkt.blogboot.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 窦康泰
 * @Date 2020-08-08 20:37
 */
@RestController
public class TagController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;
    @Autowired
    ArticleService articleService;

    @GetMapping("/getAllTag")
    public List<Tag> selectAllTag() {
        return tagService.selectAll();
    }

    @PostMapping("/addTag")
    public ResponseBean addTag(Tag tag) {
        int insert = tagService.insert(tag);
        if (insert == 1) {
            return new ResponseBean("success", "添加标签成功");
        }
        return new ResponseBean("error", "添加标签失败");
    }

}
