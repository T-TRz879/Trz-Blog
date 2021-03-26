package com.trz.blog.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name:com.trz.entity
 * @Class_name:Comment
 * @author:Trz
 * @date:2021/3/12 13:59
 */
@Data
public class Comment implements Serializable {

    private static final long serialVersionUID = -1038897351672911219L;
    /**
     * 评论ID
     */
    private Integer commentId;
    /**
     * 评论人id
     */
    private Integer commentPid;
    /**
     * 评论人名字
     */
    private String commentPname;
    /**
     * 被评论的文章id
     */
    private Integer commentArticleId;
    /**
     * 发表评论人的名字
     */
    private String commentAuthorName;
    /**
     * 发表评论人的邮箱
     */
    private String commentAuthorEmail;
    /**
     * 发表评论人地址
     */
    private String commentAuthorUrl;
    /**
     * 发表评论人头像
     */
    private String commentAuthorAvatar;
    /**
     * 发表评论的内容
     */
    private String commentContent;
    /**
     * 评论代理人
     */
    private String commentAgent;
    /**
     * 评论IP地址
     */
    private String commentIp;
    /**
     * 评论时间
     */
    private Date commentCreateTime;

    /**
     * 角色(管理员1，访客0)
     */
    private Integer commentRole;

    /**
     * 评论用户ID
     */
    private Integer commentUserId;

    /**
     * 非数据库字段
     */
    private Article article;
}