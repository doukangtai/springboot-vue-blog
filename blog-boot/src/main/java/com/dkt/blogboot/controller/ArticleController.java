package com.dkt.blogboot.controller;

import com.dkt.blogboot.req.ArticleInsertReq;
import com.dkt.blogboot.req.ArticleQueryReq;
import com.dkt.blogboot.resp.ArticleQueryResp;
import com.dkt.blogboot.resp.CommonResp;
import com.dkt.blogboot.resp.PageResp;
import com.dkt.blogboot.service.ArticleService;
import com.dkt.blogboot.service.CategoryService;
import com.dkt.blogboot.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @Author 窦康泰
 * @Date 2020-08-04 18:13
 */
@RestController
@RequestMapping("/article")
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
    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/list/substring")
    public CommonResp listSubstringContent(@Valid ArticleQueryReq req) {
        CommonResp<PageResp<ArticleQueryResp>> resp = new CommonResp<>();
        PageResp<ArticleQueryResp> pageResp = articleService.listSubstringContent(req);
        resp.setContent(pageResp);
        return resp;
    }

    @GetMapping("/{id}")
    public CommonResp getArticleById(@PathVariable("id") Integer id) {
        CommonResp<ArticleQueryResp> resp = new CommonResp<>();
        resp.setContent(articleService.getArticleById(id));
        return resp;
    }

    @GetMapping("/category/{id}")
    public CommonResp getArticleByCategoryId(@PathVariable("id") int id) {
        List<ArticleQueryResp> articleQueryResps = articleService.getArticleByCategoryId(id);
        CommonResp<List<ArticleQueryResp>> resp = new CommonResp<>();
        resp.setContent(articleQueryResps);
        return resp;
    }

    @GetMapping("/tag/{id}")
    public CommonResp getArticleByTagId(@PathVariable("id") int id) {
        CommonResp<List<ArticleQueryResp>> resp = new CommonResp<>();
        List<ArticleQueryResp> articleQueryResps = articleService.getArticleByTagId(id);
        resp.setContent(articleQueryResps);
        return resp;
    }

    @GetMapping("/list")
    public CommonResp getAllArticle() {
        List<ArticleQueryResp> articleQueryResps = articleService.list();
        CommonResp<List<ArticleQueryResp>> resp = new CommonResp<>();
        resp.setContent(articleQueryResps);
        return resp;
    }

    @GetMapping("/title")
    public CommonResp getAllArticleByTitle(@Valid ArticleQueryReq req) {
        PageResp<ArticleQueryResp> pageResp = articleService.getAllArticleByTitle(req);
        CommonResp<PageResp<ArticleQueryResp>> resp = new CommonResp<>();
        resp.setContent(pageResp);
        return resp;
    }

    @PostMapping("/insert")
    public CommonResp insert(@Valid @RequestBody ArticleInsertReq req) {
        return articleService.save(req);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable("id") Integer id) {
        CommonResp<Object> resp = new CommonResp<>();
        articleService.delete(id);
        return resp;
    }


    @PostMapping("/upload/image")
    public CommonResp upload(HttpServletRequest request, MultipartFile image) {
        String originalFilename = image.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String newImageName = UUID.randomUUID().toString() + suffix;
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
        CommonResp<Object> resp = new CommonResp<>();
        try {
            image.transferTo(imageFile);
            resp.setContent(accessUrl);
            return resp;
        } catch (IOException e) {
            resp.setSuccess(false);
            return resp;
        }
    }

    /**
     * 点赞
     * @param request
     * @param id
     * @return
     */
    @GetMapping("/praise/{id}")
    public CommonResp praiseArticle(HttpServletRequest request, @PathVariable("id") Integer id) {
        return articleService.praiseArticle(request, id);
    }
}
