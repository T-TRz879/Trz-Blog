package com.trz.blog.dao;

import com.trz.blog.entity.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-mybatis.xml", "classpath:spring/spring-mvc.xml", "classpath:mybatis/mybatis-config.xml"})
public class TagDaoTest {
    @Autowired
    private TagDao dao;

    @Test
    public void getTagById() {
        Tag tagById = dao.getTagById(1);
        System.out.println(tagById);
    }
}