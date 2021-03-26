package com.trz.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.trz.blog.dao.ArticleDao;
import com.trz.blog.dao.CommentDao;
import com.trz.blog.entity.Article;
import com.trz.blog.entity.Comment;
import com.trz.blog.enums.ArticleStatus;
import com.trz.blog.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/***
 *@Package_name:com.trz.blog.service.impl
 *@Class_name:CommentServiceImpl
 *@author:Trz
 *@date:2021/3/25 21:20
 *
 */
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private ArticleDao articleDao;

    @Override
    public void insertComment(Comment comment) {
        try {
            commentDao.insert(comment);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加评论失败，comment:{}，cause:{}", comment, e);
        }
    }

    @Override
    public List<Comment> listCommentByArticleId(Integer articleId) {
        List<Comment> comments = null;
        try {
            comments = commentDao.listCommentByArticleId(articleId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据文章id获取评论列表失败，articleId:{}，cause:{}", articleId, e);
        }
        return comments;
    }

    @Override
    public Comment getCommentById(Integer id) {
        Comment comment = null;
        try {
            comment = commentDao.getCommentById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据id获取评论失败，id:{}，cause:{}", id, e);
        }
        return null;
    }

    @Override
    public PageInfo<Comment> listCommentByPage(Integer pageIndex, Integer pageSize, HashMap<String, Object> criteria) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Comment> comments = null;
        try {
            //1、根据条件查询评论 criteria 只有【userId】一个键值对
            comments = commentDao.listComment(criteria);
            for (Comment comment : comments) {
                //2、填充 文章属性【根据文章状态和文章id查询 文章】
                Article article = articleDao.getArticleByStatusAndId(ArticleStatus.PUBLISH.getValue(), comment.getCommentArticleId());
                comment.setArticle(article);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取所有评论列表失败，pageIndex:{}，pageIndex:{}，criteria:{}，cause:{}", pageIndex, pageSize,criteria, e);
        }
        return new PageInfo<>(comments);
    }

    @Override
    public PageInfo<Comment> listReceiveCommentByPage(Integer pageIndex, Integer pageSize, Integer userId) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Comment> comments = new ArrayList<>();
        try {
            //1、查询出 某个用户的所有文章id
            List<Integer> articleIds = articleDao.listArticleIdsByUserId(userId);
            //2、用户所有文章id数不为null 且 不为0
            if(articleIds != null && articleIds.size() > 0){
                //3、查询出 某个用户收到的所有评论 【根据文章所有id】
                comments = commentDao.getReceiveComment(articleIds);
                //4、填充所有拿到的评论的 文章属性
                for (Comment comment : comments) {
                    Article article = articleDao.getArticleByStatusAndId(ArticleStatus.PUBLISH.getValue(), comment.getCommentArticleId());
                    comment.setArticle(article);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取某个用户收到的评论，pageIndex:{}，pageIndex:{}，userId:{}，cause:{}", pageIndex, pageSize,userId, e);
        }
        return new PageInfo<>(comments);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(Integer id) {
        try {
            commentDao.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除评论失败，id:{}，cause:{}", id, e);
        }
    }

    @Override
    public void updateComment(Comment comment) {
        try {
            commentDao.update(comment);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新评论失败，comment:{}，cause:{}", comment, e);
        }
    }

    @Override
    public Integer countComment() {
        Integer res = null;
        try {
            res = commentDao.countComment();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据文章id获取评论列表失败，cause:{}", e);
        }
        return res;
    }

    @Override
    public List<Comment> listRecentComment(Integer userId, Integer limit) {
        List<Comment> comments = null;
        try {
            //1、根据条件查询出最新评论
            comments = commentDao.listRecentComment(userId, limit);
            //2、填充 文章属性
            for (Comment comment : comments) {
                Article article = articleDao.getArticleByStatusAndId(ArticleStatus.PUBLISH.getValue(), comment.getCommentArticleId());
                comment.setArticle(article);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获得最近评论，userId:{}，limit:{}，cause:{}", userId, limit, e);
        }
        return comments;
    }

    @Override
    public List<Comment> listChildComment(Integer id) {
        List<Comment> comments = null;
        try {
            comments = commentDao.listChildComment(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获得子评论，id，cause:{}", id, e);
        }
        return comments;
    }
}
