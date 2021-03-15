package com.dkt.blogboot.controller;

import com.dkt.blogboot.entity.ResponseBean;
import com.dkt.blogboot.entity.Tag;
import com.dkt.blogboot.service.ArticleService;
import com.dkt.blogboot.service.CategoryService;
import com.dkt.blogboot.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return tagService.insert(tag);
    }

    @DeleteMapping("/deleteTag/{id}")
    public ResponseBean deleteTag(@PathVariable("id") int id) {
        return tagService.deleteTag(id);
    }
}
