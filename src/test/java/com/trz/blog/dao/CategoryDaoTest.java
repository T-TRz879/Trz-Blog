package com.trz.blog.dao;

import com.trz.blog.entity.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-mybatis.xml", "classpath:spring/spring-mvc.xml", "classpath:mybatis/mybatis-config.xml"})
public class CategoryDaoTest {

    @Autowired
    private CategoryDao dao;

    @Test
    public void insert() {
        int res = dao.insert(new Category(100000009, 100000009, "test1", null, null, null, null));
        System.out.println(res);
    }

    @Test
    public void update() {
        Category category = dao.getCategoryById(100000009);
        category.setCategoryIcon("阿水");
        int update = dao.update(category);
        System.out.println(update);
    }

    @Test
    public void getCategoryById() {
        Category categoryById = dao.getCategoryById(100000009);
        System.out.println(categoryById);
    }

    @Test
    public void deleteCategory() {
        int res = dao.deleteCategory(100000009);
        System.out.println(res);
    }

    @Test
    public void countCategory() {
        Integer count = dao.countCategory();
        System.out.println(count);
    }

    @Test
    public void listCategory() {
        List<Category> categories = dao.listCategory();
        System.out.println(categories);
    }

    @Test
    public void findChildCategory() {
        List<Category> childCategory = dao.findChildCategory(0);
        System.out.println(childCategory);
    }

    @Test
    public void getCategoryByName() {
        Category category = dao.getCategoryByName("操作系统");
        System.out.println(category);
    }
}