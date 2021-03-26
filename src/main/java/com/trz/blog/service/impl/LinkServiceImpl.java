package com.trz.blog.service.impl;

import com.trz.blog.dao.ArticleTagRefDao;
import com.trz.blog.dao.LinkDao;
import com.trz.blog.entity.Link;
import com.trz.blog.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 *@Package_name:com.trz.blog.service.impl
 *@Class_name:LinkServiceImpl
 *@author:Trz
 *@date:2021/3/25 21:20
 *
 */
@Service
@Slf4j
public class LinkServiceImpl implements LinkService {
    @Autowired
    private LinkDao linkDao;

    @Override
    public Integer countLink(Integer status) {
        Integer res = null;
        try {
            res = linkDao.countLink(status);
        }catch (Exception e){
            e.printStackTrace();
            log.error("根据状态获取链接数量失败，status:{},cause:{}",status,e);
        }
        return res;
    }

    @Override
    public List<Link> listLink(Integer status) {
        List<Link> list = null;
        try{
            list = linkDao.listLink(status);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取链接列表失败，status:{}，cause:{}",status,e);
        }
        return null;
    }

    @Override
    public void insertLink(Link link) {
        try{
            linkDao.insert(link);
        }catch (Exception e){
            e.printStackTrace();
            log.error("添加链接失败，status:{}，cause:{}",link,e);
        }
    }

    @Override
    public void deleteLink(Integer id) {
        try{
            linkDao.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("根据链接id删除链接失败，status:{}，cause:{}",id,e);
        }
    }

    @Override
    public void updateLink(Link link) {
        try{
            linkDao.update(link);
        }catch (Exception e){
            e.printStackTrace();
            log.error("更新链接失败，status:{}，cause:{}",link,e);
        }
    }

    @Override
    public Link getLinkById(Integer id) {
        Link link = null;
        try{
            link = linkDao.getLinkById(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("根据id查询链接失败，status:{}，cause:{}",id,e);
        }
        return null;
    }
}
