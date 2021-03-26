package com.trz.blog.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Package_name:com.trz.entity
 * @Class_name:Article
 * @author:Trz
 * @date:2021/3/12 13:59
 */
@Data
public class Article implements Serializable {

    private static final long serialVersionUID = 5207865247400761539L;
    /**
     * 文章ID
     */
    private Integer articleId;
    /**
     * 文章作者ID
     */
    private Integer articleUserId;
    /**
     * 文章标题
     */
    private String articleTitle;
    /**
     * 文章读者人数
     */
    private Integer articleViewCount;
    /**
     * 文章评论人数
     */
    private Integer articleCommentCount;
    /**
     * 文章喜欢人数
     */
    private Integer articleLikeCount;
    /**
     * 文章撰写时间
     */
    private Date articleCreateTime;
    /**
     * 文章更新时间
     */
    private Date articleUpdateTime;
    /**
     * 文章允许评论 1允许 0 不允许
     */
    private Integer articleIsComment;
    /**
     * 文章状态 1已发布 0草稿
     */
    private Integer articleStatus;
    /**
     * 文章热度
     */
    private Integer articleOrder;
    /**
     * 文章内容
     */
    private String articleContent;
    /**
     * 文章总结
     */
    private String articleSummary;
    /**
     * 文章的用户
     */
    private User user;
    /**
     * 文章所属标签
     */
    private List<Tag> tagList;
    /**
     * 文章所属类别
     */
    private List<Category> categoryList;

}