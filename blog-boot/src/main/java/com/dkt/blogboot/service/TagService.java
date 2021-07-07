package com.dkt.blogboot.service;

import com.dkt.blogboot.entity.ArticleTag;
import com.dkt.blogboot.entity.Tag;
import com.dkt.blogboot.mapper.ArticleTagMapper;
import com.dkt.blogboot.mapper.TagMapper;
import com.dkt.blogboot.req.TagInsertReq;
import com.dkt.blogboot.resp.CommonResp;
import com.dkt.blogboot.resp.TagQueryResp;
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
 * @Date 2020-08-04 19:06
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TagService {

    @Autowired
    TagMapper tagMapper;
    @Autowired
    ArticleTagMapper articleTagMapper;
    @Autowired
    RedisTemplate redisTemplate;

    public List<TagQueryResp> selectAll() {
        String key = RedisUtil.PREFIX + "selectAll::List<TagQueryResp>";
        List<TagQueryResp> result = (List<TagQueryResp>) redisTemplate.opsForValue().get(key);
        if (result != null) {
            return result;
        }
        List<Tag> tags = tagMapper.selectAll();
        List<TagQueryResp> tagQueryResps = CopyUtil.copyList(tags, TagQueryResp.class);
        redisTemplate.opsForValue().set(key, tagQueryResps, 30, TimeUnit.MINUTES);
        return tagQueryResps;
    }

    public void insert(TagInsertReq req) {
        Tag tag = CopyUtil.copy(req, Tag.class);
        tagMapper.insertSelective(tag);
        RedisUtil.invalidation(redisTemplate);
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
        RedisUtil.invalidation(redisTemplate);
        return resp;
    }
}
