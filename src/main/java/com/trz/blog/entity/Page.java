package com.trz.blog.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name:com.trz.entity
 * @Class_name:Page
 * @author:Trz
 * @date:2021/3/12 13:59
 */
@Data
public class Page implements Serializable {

    private static final long serialVersionUID = 3927496662110298634L;
    /**
     * 页面ID
     */
    private Integer pageId;

    /**
     * 页面关键字
     */
    private String pageKey;

    /**
     * 页面标题
     */
    private String pageTitle;

    /**
     * 页面内容
     */
    private String pageContent;

    /**
     * 页面创建时间
     */
    private Date pageCreateTime;

    /**
     * 页面更新时间
     */
    private Date pageUpdateTime;

    /**
     * 页面观看人数量
     */
    private Integer pageViewCount;

    /**
     * 页面评论人数量
     */
    private Integer pageCommentCount;

    /**
     * 页面状态
     */
    private Integer pageStatus;

}