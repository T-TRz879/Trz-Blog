package com.trz.blog.service.impl;

import com.trz.blog.dao.ArticleTagRefDao;
import com.trz.blog.dao.TagDao;
import com.trz.blog.entity.Tag;
import com.trz.blog.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 *@Package_name:com.trz.blog.service.impl
 *@Class_name:TagServiceImpl
 *@author:Trz
 *@date:2021/3/25 21:21
 *
 */
@Service
@Slf4j
public class TagServiceImpl implements TagService {
    @Autowired
    private TagDao tagDao;
    @Autowired
    private ArticleTagRefDao articleTagRefDao;

    @Override
    public Integer countTag() {
        return tagDao.countTag();
    }

    @Override
    public List<Tag> listTag() {
        List<Tag> tags = null;
        try{
            tags = tagDao.listTag();
        }catch (Exception e){
            e.printStackTrace();
            log.error("获得所有标签失败，cause:{}",e);
        }
        return tags;
    }

    /**
     * Tag里面有一个属性是 该属性文章的数量
     * 我们需要再文章dao中使用 文章的一个 根据文章的标签id查询文章的数量 来填充这个属性
     * 查到之后 将其赋值到相应的属性上
     * @return
     */
    @Override
    public List<Tag> listTagWithCount() {
        List<Tag> list = null;
        try{
            list = tagDao.listTag();
            for (Tag tag : list) {
                Integer count = articleTagRefDao.countArticleByTagId(tag.getTagId());
                tag.setArticleCount(count);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取所有标签失败，cause:{}",e);
        }
        return list;
    }

    @Override
    public Tag getTagById(Integer id) {
        Tag tag = null;
        try{
            tag = tagDao.getTagById(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("根据ID获取标签失败，id:{}，cause:{}",id,e);
        }
        return tag;
    }

    @Override
    public Tag insertTag(Tag tag) {
        try {
            tagDao.insert(tag);
        }catch (Exception e){
            e.printStackTrace();
            log.error("添加标签失败，tag:{}，cause:{}",tag,e);
        }
        return tag;
    }

    @Override
    public void updateTag(Tag tag) {
        try{
            tagDao.update(tag);
        }catch (Exception e){
            e.printStackTrace();
            log.error("更新标签失败，tag:{},cause:{}",tag,e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTag(Integer id) {
        try{
            tagDao.deleteById(id);
            articleTagRefDao.deleteByTagId(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除标签失败，id:{},cause:{}",id,e);
        }
    }

    @Override
    public Tag getTagByName(String name) {
        Tag tag = null;
        try{
            tag = tagDao.getTagByName(name);
        }catch (Exception e){
            e.printStackTrace();
            log.error("根据标签名称获得标签失败，name:{},cause:{}",name,e);
        }
        return tag;
    }

    @Override
    public List<Tag> listTagByArticleId(Integer articleId) {
        List<Tag> tags = null;
        try{
            tags = articleTagRefDao.listTagByArticleId(articleId);
            for (Tag tag : tags) {
                Integer count = articleTagRefDao.countArticleByTagId(tag.getTagId());
                tag.setArticleCount(count);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("根据文章ID获得标签失败，articleId:{},cause:{}",articleId,e);
        }
        return tags;
    }
}
