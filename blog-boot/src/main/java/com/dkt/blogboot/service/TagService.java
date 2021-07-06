package com.dkt.blogboot.service;

import com.dkt.blogboot.entity.ArticleTag;
import com.dkt.blogboot.entity.Tag;
import com.dkt.blogboot.mapper.ArticleTagMapper;
import com.dkt.blogboot.mapper.TagMapper;
import com.dkt.blogboot.req.TagInsertReq;
import com.dkt.blogboot.resp.CommonResp;
import com.dkt.blogboot.resp.TagQueryResp;
import com.dkt.blogboot.util.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

    public List<TagQueryResp> selectAll() {
        List<Tag> tags = tagMapper.selectAll();
        return CopyUtil.copyList(tags, TagQueryResp.class);
    }

    public void insert(TagInsertReq req) {
        Tag tag = CopyUtil.copy(req, Tag.class);
        tagMapper.insertSelective(tag);
    }

    public CommonResp delete(Integer id) {
        CommonResp<Object> resp = new CommonResp<>();
        List<ArticleTag> articleTags = articleTagMapper.selectByTid(id);
        if (CollectionUtils.isEmpty(articleTags)) {
            tagMapper.deleteByPrimaryKey(id);
            resp.setMessage("删除标签成功");
        } else {
            resp.setSuccess(false);
            resp.setMessage("标签有使用，删除失败");
        }
        return resp;
    }
}
