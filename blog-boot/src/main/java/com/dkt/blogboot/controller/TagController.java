package com.dkt.blogboot.controller;

import com.dkt.blogboot.req.TagInsertReq;
import com.dkt.blogboot.resp.CommonResp;
import com.dkt.blogboot.resp.TagQueryResp;
import com.dkt.blogboot.service.ArticleService;
import com.dkt.blogboot.service.CategoryService;
import com.dkt.blogboot.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author 窦康泰
 * @Date 2020-08-08 20:37
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;
    @Autowired
    ArticleService articleService;

    @GetMapping("/list")
    public CommonResp selectAllTag() {
        List<TagQueryResp> tagQueryResps = tagService.selectAll();
        CommonResp<List<TagQueryResp>> resp = new CommonResp<>();
        resp.setContent(tagQueryResps);
        return resp;
    }

    @PostMapping("/add")
    public CommonResp addTag(@Valid @RequestBody TagInsertReq req) {
        CommonResp<Object> resp = new CommonResp<>();
        tagService.insert(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp deleteTag(@PathVariable("id") Integer id) {
        return tagService.delete(id);
    }
}
