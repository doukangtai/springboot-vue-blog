package com.dkt.blogboot.service;

import com.dkt.blogboot.entity.ArticleCategory;
import com.dkt.blogboot.entity.Category;
import com.dkt.blogboot.mapper.ArticleCategoryMapper;
import com.dkt.blogboot.mapper.CategoryMapper;
import com.dkt.blogboot.req.CategoryInsertReq;
import com.dkt.blogboot.resp.CategoryQueryResp;
import com.dkt.blogboot.resp.CommonResp;
import com.dkt.blogboot.util.CopyUtil;
import com.dkt.blogboot.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author 窦康泰
 * @Date 2020-08-04 18:38
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CategoryService {

    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ArticleCategoryMapper articleCategoryMapper;
    @Autowired
    RedisTemplate redisTemplate;

    public List<CategoryQueryResp> selectAll() {
        String key = RedisUtil.PREFIX + "selectAll::List<CategoryQueryResp>";
        List<CategoryQueryResp> result = (List<CategoryQueryResp>) redisTemplate.opsForValue().get(key);
        if (result != null) {
            return result;
        }
        List<Category> categories = categoryMapper.selectAll();
        List<CategoryQueryResp> categoryQueryResps = CopyUtil.copyList(categories, CategoryQueryResp.class);
        redisTemplate.opsForValue().set(key, categoryQueryResps, 30, TimeUnit.MINUTES);
        return categoryQueryResps;
    }

    public void insert(CategoryInsertReq req) {
        Category category = CopyUtil.copy(req, Category.class);
        categoryMapper.insertSelective(category);
        RedisUtil.invalidation(redisTemplate);
    }

    public CommonResp delete(Integer id) {
        CommonResp<Object> resp = new CommonResp<>();
        List<ArticleCategory> articleCategories = articleCategoryMapper.selectByCid(id);
        if (CollectionUtils.isEmpty(articleCategories)) {
            categoryMapper.deleteByPrimaryKey(id);
            resp.setMessage("删除分类成功");
        } else {
            resp.setSuccess(false);
            resp.setMessage("分类有使用，删除失败");
        }
        RedisUtil.invalidation(redisTemplate);
        return resp;
    }
}
