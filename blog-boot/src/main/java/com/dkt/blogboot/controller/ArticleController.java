package com.dkt.blogboot.controller;

import com.dkt.blogboot.entity.Article;
import com.dkt.blogboot.entity.ResponseBean;
import com.dkt.blogboot.service.ArticleService;
import com.dkt.blogboot.service.CategoryService;
import com.dkt.blogboot.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${upload-img-path.windows}")
    String uploadImgPathWindows;
    @Value("${upload-img-path.linux}")
    String uploadImgPathLinux;

    @PostMapping("/uploadImg")
    public ResponseBean upload(HttpServletRequest request, MultipartFile image) {
        String originalFilename = image.getOriginalFilename();
        String newImageName = UUID.randomUUID().toString() + originalFilename;
        String accessUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/upload/image/" + newImageName;
        String newImagePath = "";
        String osName = System.getProperty("os.name");
        if ("Windows 10".equals(osName)) {
            newImagePath = uploadImgPathWindows + newImageName;
        } else {
            newImagePath = uploadImgPathLinux + newImageName;
        }
        File imageFile = new File(newImagePath);
        if (!imageFile.getParentFile().exists()) {
            imageFile.getParentFile().mkdirs();
        }
        try {
            image.transferTo(imageFile);
            return new ResponseBean("success", accessUrl);
        } catch (IOException e) {
            return new ResponseBean("error", "IO异常，上传失败");
        }
    }

    @PostMapping("/addArticle")
    public ResponseBean addArticle(Article article) {
        return articleService.addArticle(article);
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
        return articleService.getArticleById(id);
    }

    @DeleteMapping("/deleteArticleById/{id}")
    public ResponseBean deleteArticleById(@PathVariable int id) {
        return articleService.deleteArticleById(id);
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
