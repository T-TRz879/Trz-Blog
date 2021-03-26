package com.trz.blog.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Package_name:com.trz.entity
 * @Class_name:ArticleCategoryRef
 * @author:Trz
 * @date:2021/3/12 13:59
 */
@Data
public class ArticleCategoryRef implements Serializable {

    private static final long serialVersionUID = -3325414627992255433L;
    /**
     * 文章ID
     */
    private Integer articleId;
    /**
     * 分类ID
     */
    private Integer categoryId;

    public ArticleCategoryRef() {
    }

    public ArticleCategoryRef(Integer articleId, Integer categoryId) {
        this.articleId = articleId;
        this.categoryId = categoryId;
    }
}