package com.trz.blog.dto;

import lombok.Data;

import java.util.List;

/***
 *@Package_name:com.trz.blog.dto
 *@Class_name:ArticleParam
 *@author:Trz
 *@date:2021/3/27 18:29
 *  数据传输对象
 */
@Data
public class ArticleParam {
    /**
     * 文章id
     */
    private Integer articleId;
    /**
     * 文章标题
     */
    private String articleTitle;
    /**
     * 文章内容
     */
    private String articleContent;
    /**
     * 文章父分类id
     */
    private Integer articleParentCategoryId;
    /**
     * 文章子分类id
     */
    private Integer articleChildCategoryId;
    /**
     * 文章热度
     */
    private Integer articleOrder;
    /**
     * 文章状态
     */
    private Integer articleStatus;
    /**
     * 文章标签
     */
    private List<Integer> articleTagIds;

}
