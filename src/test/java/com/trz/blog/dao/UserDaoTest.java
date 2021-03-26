package com.trz.blog.dao;

import com.trz.blog.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-mybatis.xml", "classpath:spring/spring-mvc.xml", "classpath:mybatis/mybatis-config.xml"})
public class UserDaoTest {
    @Autowired
    UserDao dao;

    @Test
    public void getUserById() {
        User user = dao.getUserById(2);
        System.out.println(user);
    }

    @Test
    public void listUser(){
        List<User> users = dao.listUser();
        System.out.println(users);
    }
}