package com.dkt.blogboot.controller;

import com.dkt.blogboot.req.CategoryInsertReq;
import com.dkt.blogboot.resp.CategoryQueryResp;
import com.dkt.blogboot.resp.CommonResp;
import com.dkt.blogboot.service.ArticleService;
import com.dkt.blogboot.service.CategoryService;
import com.dkt.blogboot.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author 窦康泰
 * @Date 2020-08-08 20:36
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;
    @Autowired
    ArticleService articleService;

    @GetMapping("/list")
    public CommonResp selectAllCategory() {
        List<CategoryQueryResp> categoryQueryResps = categoryService.selectAll();
        CommonResp<List<CategoryQueryResp>> resp = new CommonResp<>();
        resp.setContent(categoryQueryResps);
        return resp;
    }

    @PostMapping("/add")
    public CommonResp addTag(@Valid @RequestBody CategoryInsertReq req) {
        CommonResp<Object> resp = new CommonResp<>();
        categoryService.insert(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp deleteTag(@PathVariable("id") Integer id) {
        return categoryService.delete(id);
    }
}
