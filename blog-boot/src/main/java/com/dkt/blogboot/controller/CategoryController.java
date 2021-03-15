package com.dkt.blogboot.controller;

import com.dkt.blogboot.entity.Category;
import com.dkt.blogboot.entity.ResponseBean;
import com.dkt.blogboot.service.ArticleService;
import com.dkt.blogboot.service.CategoryService;
import com.dkt.blogboot.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 窦康泰
 * @Date 2020-08-08 20:36
 */
@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;
    @Autowired
    ArticleService articleService;

    @GetMapping("/getAllCategory")
    public List<Category> selectAllCategory() {
        return categoryService.selectAll();
    }

    @PostMapping("/addCategory")
    public ResponseBean addCategory(Category category) {
        return categoryService.insert(category);
    }

    @DeleteMapping("/deleteCategory/{cid}")
    public ResponseBean deleteCategory(@PathVariable("cid") int cid) {
        return categoryService.deleteCategory(cid);
    }
}
