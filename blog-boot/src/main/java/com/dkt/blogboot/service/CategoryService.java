package com.dkt.blogboot.service;

import com.dkt.blogboot.entity.Category;
import com.dkt.blogboot.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author 窦康泰
 * @Date 2020-08-04 18:38
 */
@Service
@Transactional
public class CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    public List<Category> selectAll() {
        return categoryMapper.selectAll();
    }

    public int insert(Category record) {
        return categoryMapper.insert(record);
    }

}
