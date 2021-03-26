package com.trz.blog.service.impl;

import com.trz.blog.dao.SetDao;
import com.trz.blog.entity.Set;
import com.trz.blog.service.SetsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 *@Package_name:com.trz.blog.service.impl
 *@Class_name:OptionsServiceImpl
 *@author:Trz
 *@date:2021/3/25 21:20
 *
 */
@Service
@Slf4j
public class SetsServiceImpl implements SetsService {
    @Autowired
    private SetDao setDao;

    @Override
    @Cacheable(value = "default", key = "'options'")
    public Set getSets() {
        List<Set> sets =null;
        try {
            sets = setDao.getSets();
        }catch (Exception e){
            e.printStackTrace();
            log.error("获得基本信息失败，cause:{}",e);
        }
        return null;
    }

    @Override
    @CacheEvict(value = "default", key = "'options'")
    public void insertSets(Set sets) {
        try{
            setDao.insert(sets);
        }catch (Exception e){
            e.printStackTrace();
            log.error("插入基本信息失败，status:{}，cause:{}",sets,e);
        }
    }

    @Override
    @CacheEvict(value = "default", key = "'options'")
    public void updateSets(Set sets) {
        try{
            setDao.update(sets);
        }catch (Exception e){
            e.printStackTrace();
            log.error("更新基本信息失败，status:{}，cause:{}",sets,e);
        }
    }
}
