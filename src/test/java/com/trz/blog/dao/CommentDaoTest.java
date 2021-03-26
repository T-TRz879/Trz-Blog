package com.trz.blog.dao;

import com.trz.blog.entity.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-mybatis.xml", "classpath:spring/spring-mvc.xml", "classpath:mybatis/mybatis-config.xml"})
public class CommentDaoTest {
    @Autowired
    private CommentDao dao;

    @Test
    public void deleteById() {
        int i = dao.deleteById(49);
        System.out.println(i);
    }

    @Test
    public void insert() {
        int insert = dao.insert(new Comment());
        System.out.println(insert);
    }

    @Test
    public void getCommentById() {
        Comment commentById = dao.getCommentById(35);
        System.out.println(commentById);
    }

    @Test
    public void update() {
        Comment commentById = dao.getCommentById(48);
        commentById.setCommentIp("111");
        int update = dao.update(commentById);
        System.out.println(update);
    }

    @Test
    public void listCommentByArticleId() {
        List<Comment> comments = dao.listCommentByArticleId(6);
        System.out.println(comments);
    }

    @Test
    public void listComment() {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("userId",7);
        List<Comment> comments = dao.listComment(hashMap);
        System.out.println(comments);
    }

    @Test
    public void getReceiveComment() {
        List<Integer> list = new ArrayList<>();
        list.add(17);
        List<Comment> receiveComment = dao.getReceiveComment(list);
        System.out.println(receiveComment);
    }

    @Test
    public void countComment() {
        Integer integer = dao.countComment();
        System.out.println(integer);
    }

    @Test
    public void listRecentComment() {
        List<Comment> comments = dao.listRecentComment(1, 1);
        System.out.println(comments);
    }

    @Test
    public void listChildComment() {
        List<Comment> comments = dao.listChildComment(12);
        System.out.println(comments);
    }

    @Test
    public void deleteByUserId() {
        Integer integer = dao.deleteByUserId(10000);
        System.out.println(integer);
    }

    @Test
    public void deleteByArticleId() {
        Integer integer = dao.deleteByArticleId(9999);
        System.out.println(integer);
    }
}