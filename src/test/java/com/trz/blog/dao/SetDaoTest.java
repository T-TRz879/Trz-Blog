package com.trz.blog.dao;

import com.trz.blog.entity.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-mybatis.xml", "classpath:spring/spring-mvc.xml", "classpath:mybatis/mybatis-config.xml"})
public class SetDaoTest {
    @Autowired
    private SetDao dao;
    @Test
    public void getSetsById() {
        Set setsById = dao.getSetsById(1);
        System.out.println(setsById);
    }

    @Test
    public void getSets() {
        List<Set> sets = dao.getSets();
        System.out.println(sets);
    }
}