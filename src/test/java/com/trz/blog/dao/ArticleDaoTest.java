package com.trz.blog.dao;

import com.trz.blog.entity.Article;
import org.checkerframework.checker.units.qual.Area;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-mybatis.xml", "classpath:spring/spring-mvc.xml", "classpath:mybatis/mybatis-config.xml"})
public class ArticleDaoTest {

    @Autowired
    private ArticleDao dao;

    @Test
    public void deleteById() {
        Integer res = dao.deleteById(998);
        System.out.println(res);
    }

    @Test
    public void deleteByUserId() {
        Integer res = dao.deleteByUserId(11111);
        System.out.println(res);
    }

    @Test
    public void insert() {
        Integer insert = dao.insert(new Article());
        System.out.println(insert);
    }

    @Test
    public void update() {
        Article article = dao.getByArticleId(10000);
        article.setArticleContent("11111阿水");
        Integer update = dao.update(article);
        System.out.println(update);
    }

    @Test
    public void findAll() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("keywords","Java如何让程序");
        hashMap.put("status",1);
        List<Article> all = dao.findAll(hashMap);
        System.out.println(all);
    }

    @Test
    public void listAllNotWithContent() {
        List<Article> articles = dao.listAllNotWithContent();
        System.out.println(articles);
    }

    @Test
    public void countArticle() {
        Integer res = dao.countArticle(1);
        System.out.println(res);
    }

    @Test
    public void countArticleComment() {
        Integer res = dao.countArticleComment();
        System.out.println(res);
    }

    @Test
    public void countArticleView() {
        Integer res = dao.countArticleView();
        System.out.println(res);
    }

    @Test
    public void listArticle() {
        List<Article> articles = dao.listArticle();
        System.out.println(articles);
    }

    @Test
    public void getArticleByStatusAndId() {
        Article articleByStatusAndId = dao.getArticleByStatusAndId(0, 23);
        System.out.println(articleByStatusAndId);

    }

    @Test
    public void pageArticle() {
        List<Article> articles = dao.pageArticle(1, 2, 1);
        System.out.println(articles);
    }

    @Test
    public void listArticleByViewCount() {
        List<Article> articles = dao.listArticleByViewCount(2);
        System.out.println(articles);
    }

    @Test
    public void getAfterArticle() {
        Article afterArticle = dao.getAfterArticle(2);
        System.out.println(afterArticle);
    }

    @Test
    public void listRandomArticle() {
        List<Article> articles = dao.listRandomArticle(2);
        System.out.println(articles);
    }

    @Test
    public void listArticleByCommentCount() {
        List<Article> articles = dao.listArticleByCommentCount(2);
        System.out.println(articles);
    }

    @Test
    public void updateCommentCount() {
        Integer integer = dao.updateCommentCount(10);
        System.out.println(integer);
    }

    @Test
    public void getLastUpdateArticle() {
        Article lastUpdateArticle = dao.getLastUpdateArticle();
        System.out.println(lastUpdateArticle);
    }

    @Test
    public void countArticleByUser() {
        Integer integer = dao.countArticleByUser(11111);
        System.out.println(integer);
    }

    @Test
    public void findArticleByCategoryId() {
        List<Article> articleByCategoryId = dao.findArticleByCategoryId(2, 2);
        System.out.println(articleByCategoryId);
    }

    @Test
    public void findArticleByCategoryIds() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        List<Article> articleByCategoryIds = dao.findArticleByCategoryIds(list,3);
        System.out.println(articleByCategoryIds);
    }

    @Test
    public void listArticleByLimit() {
        List<Article> articles = dao.listArticleByLimit(11111, 4);
        System.out.println(articles);
    }

    @Test
    public void getByArticleId(){
        Article article = dao.getByArticleId(10000);
        System.out.println(article);
    }
}