package com.dkt.blogboot.service;

import com.dkt.blogboot.entity.ArticleTag;
import com.dkt.blogboot.entity.ResponseBean;
import com.dkt.blogboot.entity.Tag;
import com.dkt.blogboot.mapper.ArticleTagMapper;
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
@Transactional(rollbackFor = Exception.class)
public class TagService {

    @Autowired
    TagMapper tagMapper;
    @Autowired
    ArticleTagMapper articleTagMapper;

    public List<Tag> selectAll() {
        return tagMapper.selectAll();
    }

    public ResponseBean insert(Tag record) {
        if ("".equals(record.getTag()) || record.getTag() == null) {
            return new ResponseBean("error", "标签不能为空");
        }
        List<Tag> tags = tagMapper.selectByTag(record.getTag());
        if (tags.size() > 0) {
            return new ResponseBean("error", "此标签存在，添加失败");
        }
        int insert = tagMapper.insert(record);
        if (insert == 1) {
            return new ResponseBean("success", "添加标签成功");
        }
        return new ResponseBean("error", "添加标签失败");
    }

    public ResponseBean deleteTag(int id) {
        List<ArticleTag> articleTags = articleTagMapper.selectByTid(id);
        if (articleTags.size() <= 0) {
            int i = tagMapper.deleteByPrimaryKey(id);
            if (i > 0) {
                return new ResponseBean("success", "删除标签成功");
            } else {
                return new ResponseBean("error", "未知错误，失败");
            }
        }
        return new ResponseBean("error", "有使用此标签的文章，删除失败");
    }
}
