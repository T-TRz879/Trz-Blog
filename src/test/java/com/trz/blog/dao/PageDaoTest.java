package com.trz.blog.dao;

import com.trz.blog.entity.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-mybatis.xml", "classpath:spring/spring-mvc.xml", "classpath:mybatis/mybatis-config.xml"})
public class PageDaoTest {

    @Autowired
    private PageDao dao;
    @Test
    public void deleteById() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void getPageById() {
        Page pageById = dao.getPageById(5);
        System.out.println(pageById);
    }

    @Test
    public void update() {
    }

    @Test
    public void listPage() {
        List<Page> pages = dao.listPage(2);
        System.out.println(pages);
    }

    @Test
    public void getPageByKey() {
        Page hello = dao.getPageByKey(1, "hello");
        System.out.println(hello);
    }
}