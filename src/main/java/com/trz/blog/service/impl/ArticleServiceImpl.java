package com.trz.blog.service.impl;

import com.github.pagehelper.PageInfo;
import com.trz.blog.dao.ArticleCategoryRefDao;
import com.trz.blog.dao.ArticleDao;
import com.trz.blog.dao.ArticleTagRefDao;
import com.trz.blog.dao.UserDao;
import com.trz.blog.entity.*;
import com.trz.blog.enums.ArticleCommentStatus;
import com.trz.blog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Tainted;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/***
 *@Package_name:com.trz.blog.service.impl
 *@Class_name:ArticleServiceImpl
 *@author:Trz
 *@date:2021/3/25 21:18
 *
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ArticleCategoryRefDao articleCategoryRefDao;
    @Autowired
    private ArticleTagRefDao articleTagRefDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Integer countArticle(Integer status) {
        Integer res = null;
        try{
            res = articleDao.countArticle(status);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取文章总数失败，status:{}，cause:{}",status,e);
        }
        return res;
    }

    @Override
    public Integer countArticleComment() {
        Integer res = null;
        try{
            res = articleDao.countArticleComment();
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取评论总数失败，cause:{}",e);
        }
        return res;
    }

    @Override
    public Integer countArticleView() {
        Integer res = null;
        try{
            res = articleDao.countArticleView();
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取浏览总数失败，cause:{}",e);
        }
        return res;
    }

    @Override
    public Integer countArticleByCategoryId(Integer categoryId) {
        Integer res = null;
        try{
            res = articleCategoryRefDao.countArticleByCategoryId(categoryId);
        }catch (Exception e){
            e.printStackTrace();
            log.error("统计有分类的文章数失败，categoryId:{}，cause:{}",categoryId,e);
        }
        return res;
    }

    @Override
    public Integer countArticleByTagId(Integer tagId) {
        Integer res = null;
        try{
            res = articleTagRefDao.countArticleByTagId(tagId);
        }catch (Exception e){
            e.printStackTrace();
            log.error("统计有这个表情的文章数失败，tagId:{}，cause:{}",tagId,e);
        }
        return res;
    }

    @Override
    public List<Article> listArticle(HashMap<String, Object> criteria) {
        List<Article> articleList = null;
        try{
            articleList = articleDao.findAll(criteria);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获得所有文章不分页失败，criteria:{}，cause:{}",criteria,e);
        }
        return articleList;
    }

    @Override
    public List<Article> listRecentArticle(Integer userId, Integer limit) {
        List<Article> articleList = null;
        try{
            articleList = articleDao.listArticleByLimit(userId,limit);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获得最新文章失败，userId:{}，limit:{}，cause:{}",userId,limit,e);
        }
        return articleList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArticleDetail(Article article) {
        try{
            //文章更新日期
            article.setArticleUpdateTime(new Date());
            //判断文章中的分类列表是否为空 不为空 那么就更新它的分类列表
            if(article.getCategoryList() != null){
                //先删除分类列表和文章的对应
                articleCategoryRefDao.deleteByArticleId(article.getArticleId());
                //遍历分类列表 将文章和分类列表的对应添加进去
                for (Category category : article.getCategoryList()) {
                    ArticleCategoryRef articleCategoryRef = new ArticleCategoryRef(article.getArticleId(),category.getCategoryId());
                    articleCategoryRefDao.insert(articleCategoryRef);
                }
            }
            //判断文章中的标签列表是否为空 不为空 那么就更新它的标签列表
            if(article.getTagList() != null){
                //先删除标签列表和文章的对应
                articleTagRefDao.deleteByArticleId(article.getArticleId());
                //遍历标签列表 将文章和标签列表的对应添加进去
                for (Tag tag : article.getTagList()) {
                    ArticleTagRef articleTagRef = new ArticleTagRef(article.getArticleId(),tag.getTagId());
                    articleTagRefDao.insert(articleTagRef);
                }
            }
            articleDao.update(article);
        }catch (Exception e){
            e.printStackTrace();
            log.error("更新文章失败，userId:{}，cause:{}",article,e);
        }
    }

    @Override
    public void updateArticle(Article article) {
        try{
            articleDao.update(article);
        }catch (Exception e){
            e.printStackTrace();
            log.error("更新文章失败，article:{}，cause:{}",article,e);
        }
    }

    @Override
    public void deleteArticleBatch(List<Integer> ids) {
        try{
            articleDao.deleteBatch(ids);
        }catch (Exception e){
            e.printStackTrace();
            log.error("批量删除文章失败，ids:{}，cause:{}",ids,e);
        }
    }

    @Override
    public void deleteArticle(Integer id) {
        try{
            //删除文章
            articleDao.deleteById(id);
            //根据文章id删除文章与标签的对应
            articleTagRefDao.deleteByArticleId(id);
            //根据文章id删除文章与分类的对应
            articleCategoryRefDao.deleteByArticleId(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除文章失败，id:{}，cause:{}",id,e);
        }
    }

    @Override
    public PageInfo<Article> pageArticle(Integer pageIndex, Integer pageSize, HashMap<String, Object> criteria) {
        //1、根据条件查询到所有的文章
        List<Article> articleList = articleDao.findAll(criteria);
        for (Article article : articleList) {
            //2、拿到文章所对应的分类列表
            List<Category> categoryList = articleCategoryRefDao.listCategoryByArticleId(article.getArticleId());
            //3、如果文章分类列表为空 就给其一个未分类的默认值
            if(categoryList == null && categoryList.size() == 0){
                categoryList = new ArrayList<>();
                categoryList.add(Category.Default());
            }
            //4、将文章分类添加到文章对象中
            article.setCategoryList(categoryList);
            //5、将文章的作者信息添加到文章对象中
            article.setUser(userDao.getUserById(article.getArticleUserId()));
            //6、将标签分类添加到文章对象中
            List<Tag> tagList = articleTagRefDao.listTagByArticleId(article.getArticleId());
            article.setTagList(tagList);
        }
        return new PageInfo<>(articleList);
    }

    @Override
    public Article getArticleByStatusAndId(Integer status, Integer id) {
        Article article = null;
        try{
            //1、根据文章状态和文章id查询文章
            article = articleDao.getArticleByStatusAndId(status,id);
            //2、如果查询出的文章不为空 则进入一下处理
            if(article != null){
                //3、填充分类属性
                List<Category> categories = articleCategoryRefDao.listCategoryByArticleId(article.getArticleId());
                //4、填充标签属性
                List<Tag> tags = articleTagRefDao.listTagByArticleId(article.getArticleId());
                //5、填充作者属性
                User user = userDao.getUserById(article.getArticleUserId());
                article.setTagList(tags);
                article.setCategoryList(categories);
                article.setUser(user);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取文章详细失败，status:{}，id:{}，cause:{}",status,id,e);
        }
        return article;
    }

    @Override
    public List<Article> listArticleByViewCount(Integer limit) {
        List<Article> articleList = null;
        try{
            articleList = articleDao.listArticleByViewCount(limit);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取访问量最多的文章失败，limit:{}，cause:{}",limit,e);
        }
        return articleList;
    }

    @Override
    public Article getAfterArticle(Integer id) {
        Article article = null;
        try{
            article = articleDao.getAfterArticle(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取下一篇文章失败，id:{}，cause:{}",id,e);
        }
        return article;
    }

    @Override
    public Article getPreArticle(Integer id) {
        Article article = null;
        try{
            article = articleDao.getPreArticle(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取上一篇文章失败，id:{}，cause:{}",id,e);
        }
        return article;
    }

    @Override
    public List<Article> listRandomArticle(Integer limit) {
        List<Article> articleList = null;
        try{
            articleList = articleDao.listRandomArticle(limit);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取随机文章失败，limit:{}，cause:{}",limit,e);
        }
        return articleList;
    }

    @Override
    public List<Article> listArticleByCommentCount(Integer limit) {
        List<Article> articleList = null;
        try{
            articleList = articleDao.listArticleByCommentCount(limit);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取评论最多的文章失败，limit:{}，cause:{}",limit,e);
        }
        return articleList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertArticle(Article article) {
        try{
            //1、添加创造时间
            article.setArticleCreateTime(new Date());
            //2、添加更改时间
            article.setArticleUpdateTime(new Date());
            //3、允许评论
            article.setArticleIsComment(ArticleCommentStatus.ALLOW.getValue());
            //4、观看人数
            article.setArticleViewCount(0);
            //5、赞的人数
            article.setArticleLikeCount(0);
            //6、评论人数
            article.setArticleCommentCount(0);
            //7、热度
            article.setArticleOrder(1);
            articleDao.insert(article);
            //8、将文章与标签关联
            if(article.getTagList() != null){
                for (Tag tag : article.getTagList()) {
                    ArticleTagRef articleTagRef = new ArticleTagRef(article.getArticleId(),tag.getTagId());
                    articleTagRefDao.insert(articleTagRef);
                }
            }
            //9、将文章与分类关联
            if(article.getCategoryList() != null){
                for (Category category : article.getCategoryList()) {
                    ArticleCategoryRef articleCategoryRef = new ArticleCategoryRef(article.getArticleId(),category.getCategoryId());
                    articleCategoryRefDao.insert(articleCategoryRef);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取下一篇文章失败，article:{}，cause:{}",article,e);
        }

    }

    @Override
    public void updateCommentCount(Integer articleId) {
        try{
            articleDao.updateCommentCount(articleId);
        }catch (Exception e){
            e.printStackTrace();
            log.error("更新文章评论数失败，articleId:{}，cause:{}",articleId,e);
        }
    }

    @Override
    public Article getLastUpdateArticle() {
        Article article = null;
        try{
            article = articleDao.getLastUpdateArticle();
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取最后更新的文章失败，cause:{}",e);
        }
        return article;
    }

    @Override
    public List<Article> listArticleByCategoryId(Integer cateId, Integer limit) {
        List<Article> articleList = null;
        try{
            articleList = articleDao.findArticleByCategoryId(cateId,limit);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取相关文章失败，cateId:{}，limit:{}，cause:{}",cateId,limit,e);
        }
        return articleList;
    }

    @Override
    public List<Article> listArticleByCategoryIds(List<Integer> cateIds, Integer limit) {
        List<Article> articleList = null;
        try{
            articleList = articleDao.findArticleByCategoryIds(cateIds,limit);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取相关文章失败，cateIds:{}，limit:{}，cause:{}",cateIds,limit,e);
        }
        return articleList;
    }

    @Override
    public List<Integer> listCategoryIdByArticleId(Integer articleId) {
        List<Integer> integers = null;
        try{
            integers = articleCategoryRefDao.selectCategoryIdByArticleId(articleId);
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取相关文章失败，articleId:{}，cause:{}",articleId,e);
        }
        return integers;
    }

    @Override
    public List<Article> listAllNotWithContent() {
        List<Article> articleList = null;
        try{
            articleList = articleDao.listAllNotWithContent();
        }catch (Exception e){
            e.printStackTrace();
            log.error("获取所有文章失败，cause:{}",e);
        }
        return articleList;
    }
}
