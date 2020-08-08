package com.dkt.blogboot.service;

import com.dkt.blogboot.entity.Tag;
import com.dkt.blogboot.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author 窦康泰
 * @Date 2020-08-04 19:06
 */
@Service
@Transactional
public class TagService {

    @Autowired
    TagMapper tagMapper;

    public List<Tag> selectAll() {
        return tagMapper.selectAll();
    }

    public int insert(Tag record) {
        return tagMapper.insert(record);
    }

}
