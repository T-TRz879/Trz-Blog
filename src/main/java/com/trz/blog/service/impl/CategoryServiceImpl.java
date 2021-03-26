package com.trz.blog.service.impl;

import com.trz.blog.dao.ArticleCategoryRefDao;
import com.trz.blog.dao.ArticleDao;
import com.trz.blog.dao.CategoryDao;
import com.trz.blog.entity.Category;
import com.trz.blog.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 *@Package_name:com.trz.blog.service.impl
 *@Class_name:CategoryServiceImpl
 *@author:Trz
 *@date:2021/3/25 21:19
 *
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private ArticleCategoryRefDao articleCategoryRefDao;
    @Override
    public Integer countCategory() {
        Integer res = null;
        try {
            res = categoryDao.countCategory();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加评论失败，cause:{}", e);
        }
        return res;
    }

    @Override
    public List<Category> listCategory() {
        List<Category> categories = null;
        try {
            categories = categoryDao.listCategory();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加评论失败，cause:{}", e);
        }
        return categories;
    }

    @Override
    public List<Category> listCategoryWithCount() {
        List<Category> categories = null;
        try {
            //1、详细查询分类
            categories = categoryDao.listCategory();
            //2、填充 文章数量属性
            for (Category category : categories) {
                Integer count = articleCategoryRefDao.countArticleByCategoryId(category.getCategoryId());
                category.setArticleCount(count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加评论失败，cause:{}", e);
        }
        return categories;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(Integer id) {
        try{
            //1、删除分类标签
            categoryDao.deleteCategory(id);
            //2、删除 文章与分类对应 的关系
            articleCategoryRefDao.deleteByCategoryId(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除分类列表失败，id:{}，cause:{}",id,e);
        }
    }

    @Override
    public Category getCategoryById(Integer id) {
        Category category = null;
        try{
            category = categoryDao.getCategoryById(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("根据id查询分类信息失败，id:{}，cause:{}",id,e);
        }
        return category;
    }

    @Override
    public Category insertCategory(Category category) {
        try{
            categoryDao.insert(category);
        }catch (Exception e){
            e.printStackTrace();
            log.error("添加分类失败，category:{}，cause:{}",category,e);
        }
        return category;
    }

    @Override
    public void updateCategory(Category category) {
        try{
            categoryDao.update(category);
        }catch (Exception e){
            e.printStackTrace();
            log.error("更新分类失败，category:{}，cause:{}",category,e);
        }
    }

    @Override
    public Category getCategoryByName(String name) {
        Category category = null;
        try{
            category = categoryDao.getCategoryByName(name);
        }catch (Exception e){
            e.printStackTrace();
            log.error("根据分类名获取分类，name:{}，cause:{}",name,e);
        }
        return category;
    }
}
