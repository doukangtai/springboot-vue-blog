package com.dkt.blogboot.service;

import com.dkt.blogboot.entity.ArticleCategory;
import com.dkt.blogboot.entity.Category;
import com.dkt.blogboot.mapper.ArticleCategoryMapper;
import com.dkt.blogboot.mapper.CategoryMapper;
import com.dkt.blogboot.req.CategoryInsertReq;
import com.dkt.blogboot.resp.CategoryQueryResp;
import com.dkt.blogboot.resp.CommonResp;
import com.dkt.blogboot.util.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

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

    public List<CategoryQueryResp> selectAll() {
        List<Category> categories = categoryMapper.selectAll();
        return CopyUtil.copyList(categories, CategoryQueryResp.class);
    }

    public void insert(CategoryInsertReq req) {
        Category category = CopyUtil.copy(req, Category.class);
        categoryMapper.insertSelective(category);
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
        return resp;
    }
//
//    public ResponseBean insert(Category record) {
//        if ("".equals(record.getCategory()) || record.getCategory() == null) {
//            return new ResponseBean("error", "分类不能为空");
//        }
//        List<Category> categories = categoryMapper.selectByCategory(record.getCategory());
//        if (categories.size() > 0) {
//            return new ResponseBean("error", "此分类存在，添加失败");
//        }
//        int insert = categoryMapper.insert(record);
//        if (insert == 1) {
//            return new ResponseBean("success", "添加分类成功");
//        }
//        return new ResponseBean("error", "添加分类失败");
//    }
//
//    public ResponseBean deleteCategory(int cid) {
//        List<ArticleCategory> articleCategories = articleCategoryMapper.selectByCid(cid);
//        if (articleCategories.size() <= 0) {
//            int i = categoryMapper.deleteByPrimaryKey(cid);
//            if (i > 0) {
//                return new ResponseBean("success", "删除分类成功");
//            } else {
//                return new ResponseBean("error", "未知错误，失败");
//            }
//        }
//        return new ResponseBean("error", "有使用此分类的文章，删除失败");
//    }
}
