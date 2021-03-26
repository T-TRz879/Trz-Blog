package com.trz.blog.service;

import com.trz.blog.entity.Set;

/***
 *@Package_name:com.trz.blog.service
 *@Class_name:SetsService
 *@author:Trz
 *@date:2021/3/25 21:21
 *
 */
public interface SetsService {
    /**
     * 获得基本信息
     *
     * @return 系统设置
     */
    Set getSets();

    /**
     * 新建基本信息
     * 
     * @param options 系统设置
     */
    void insertSets(Set options);

    /**
     * 更新基本信息
     * 
     * @param options 系统设置
     */
    void updateSets(Set options);
}
