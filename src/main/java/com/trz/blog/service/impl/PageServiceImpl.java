package com.trz.blog.service.impl;

import com.github.pagehelper.PageInfo;
import com.trz.blog.dao.PageDao;
import com.trz.blog.entity.Page;
import com.trz.blog.service.PageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 *@Package_name:com.trz.blog.service.impl
 *@Class_name:PageServiceImpl
 *@author:Trz
 *@date:2021/3/25 21:21
 *
 */
@Slf4j
@Service
public class PageServiceImpl implements PageService {
    @Autowired
    private PageDao pageDao;

    @Override
    public List<Page> listPage(Integer status) {
        List<Page> pages = null;
        try {
            pages = pageDao.listPage(status);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获得页面列表失败，status:{}，cause:{}", status, e);
        }
        return pages;
    }

    @Override
    public Page getPageByKey(Integer status, String key) {
        Page page = null;
        try {
            page = pageDao.getPageByKey(status,key);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据页面key获得页面失败，status:{}，cause:{}", status,key, e);
        }
        return page;
    }

    @Override
    public Page getPageById(Integer id) {
        Page page = null;
        try {
            page = pageDao.getPageById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据页面id获得页面失败，status:{}，cause:{}", id,e);
        }
        return page;
    }

    @Override
    public void insertPage(Page page) {
        try {
            pageDao.insert(page);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("插入页面失败，status:{}，cause:{}", page,e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePage(Integer id) {
        try {
            pageDao.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除页面失败，status:{}，cause:{}", id,e);
        }
    }

    @Override
    public void updatePage(Page page) {
        try {
            pageDao.update(page);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新页面失败，status:{}，cause:{}", page,e);
        }
    }
}
