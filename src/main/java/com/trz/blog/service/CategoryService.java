package com.trz.blog.service;

import com.trz.blog.entity.Category;

import java.util.List;

/***
 *@Package_name:com.trz.blog.service
 *@Class_name:CategoryService
 *@author:Trz
 *@date:2021/3/25 21:19
 *
 */
public interface CategoryService {
    /**
     * 获得分类总数
     *
     * @return
     */
    Integer countCategory();


    /**
     * 获得分类列表
     *
     * @return 分类列表
     */
    List<Category> listCategory();

    /**
     * 获得分类列表
     * 把非数据库字段也查询到
     *
     * @return 分类列表
     */
    List<Category> listCategoryWithCount();

    /**
     * 删除分类
     *
     * @param id ID
     */

    void deleteCategory(Integer id);

    /**
     * 根据id查询分类信息
     *
     * @param id     ID
     * @return 分类
     */
    Category getCategoryById(Integer id);

    /**
     * 添加分类
     *
     * @param category 分类
     * @return 分类
     */
    Category insertCategory(Category category);

    /**
     * 更新分类
     *
     * @param category 分类
     */
    void updateCategory(Category category);

    /**
     * 根据分类名获取分类
     *
     * @param name 名称
     * @return 分类
     */
    Category getCategoryByName(String name);


}
