package com.trz.blog.service.impl;

import com.trz.blog.dao.NoticeDao;
import com.trz.blog.entity.Notice;
import com.trz.blog.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 *@Package_name:com.trz.blog.service.impl
 *@Class_name:NoticeServiceImpl
 *@author:Trz
 *@date:2021/3/25 21:20
 *
 */
@Slf4j
@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeDao noticeDao;

    @Override
    public List<Notice> listNotice(Integer status) {
        List<Notice> list = null;
        try{
            list = noticeDao.listNotice(status);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取公告列表失败，status:{}，cause:{}",status,e);
        }
        return list;
    }

    @Override
    public void insertNotice(Notice notice) {
        try{
           noticeDao.insert(notice);
        }catch (Exception e){
            e.printStackTrace();
            log.error("插入公告失败，status:{}，cause:{}",notice,e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteNotice(Integer id) {
        try{
            noticeDao.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除公告失败，status:{}，cause:{}",id,e);
        }
    }

    @Override
    public void updateNotice(Notice notice) {
        try{
            noticeDao.update(notice);
        }catch (Exception e){
            e.printStackTrace();
            log.error("更新公告失败，status:{}，cause:{}",notice,e);
        }
    }

    @Override
    public Notice getNoticeById(Integer id) {
        Notice notice = null;
        try{
            notice = noticeDao.getNoticeById(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("根据id查询公告失败，status:{}，cause:{}",id,e);
        }
        return notice;
    }
}
