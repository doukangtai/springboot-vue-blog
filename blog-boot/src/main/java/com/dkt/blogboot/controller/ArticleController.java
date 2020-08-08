package com.dkt.blogboot.controller;

import com.dkt.blogboot.entity.Article;
import com.dkt.blogboot.entity.ResponseBean;
import com.dkt.blogboot.service.ArticleService;
import com.dkt.blogboot.service.CategoryService;
import com.dkt.blogboot.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @Author 窦康泰
 * @Date 2020-08-04 18:13
 */
@RestController
public class ArticleController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;
    @Autowired
    ArticleService articleService;

    @PostMapping("/uploadImg")
    public ResponseBean upload(HttpServletRequest httpServletRequest, MultipartFile image) {
        String filePath = httpServletRequest.getServletContext().getRealPath("/uploadImg");
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        String fileUrl = httpServletRequest.getScheme()
                + "://"
                + httpServletRequest.getServerName()
                + ":"
                + httpServletRequest.getServerPort()
                + httpServletRequest.getContextPath()
                + "/uploadImg/"
                + fileName;
        try {
            File file1 = new File(file, fileName);
            image.transferTo(file1);
            return new ResponseBean("success", fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseBean("error", "上传失败");
    }

    @PostMapping("/addArticle")
    public ResponseBean addArticle(Article article) {
        int insert = articleService.addArticle(article);
        if (insert == 1) {
            return new ResponseBean("success", "添加文章成功");
        } else {
            return new ResponseBean("error", "添加文章失败");
        }
    }

    @GetMapping("/getAllArticle")
    public List<Article> getAllArticle() {
        return articleService.getAllArticle();
    }

    @GetMapping("/getArticleByTitle/{title}")
    public List<Article> getArticleByTitle(@PathVariable String title) {
        return articleService.getArticleByTitle(title);
    }

    @GetMapping("/getArticleById/{id}")
    public Article getArticleById(@PathVariable int id) {
        Article article = articleService.getArticleById(id);
        System.out.println(article);
        return article;
    }

    @DeleteMapping("/deleteArticleById/{id}")
    public ResponseBean deleteArticleById(@PathVariable int id) {
        int i = articleService.deleteArticleById(id);
        if (i == 1) {
            return new ResponseBean("success", "删除成功");
        } else {
            return new ResponseBean("error", "删除失败");
        }
    }

    @GetMapping("/getAllArticleSubstringContent")
    public List<Article> getAllArticleSubstringContent() {
        return articleService.getAllArticleSubstringContent();
    }

    @GetMapping("/getArticleByCategoryId/{categoryId}")
    public List<Article> getArticleByCategoryId(@PathVariable int categoryId) {
        return articleService.getArticleByCategoryId(categoryId);
    }

    @GetMapping("/getArticleByTagId/{tagId}")
    public List<Article> getArticleByTagId(@PathVariable int tagId) {
        return articleService.getArticleByTagId(tagId);
    }

}
