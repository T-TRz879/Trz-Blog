package com.trz.blog.entity;


import lombok.Data;

import java.io.Serializable;

/**
 * @Package_name:com.trz.entity
 * @Class_name:Category
 * @author:Trz
 * @date:2021/3/12 13:59
 */
@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 6687286913317513141L;
    /**
     * 分类ID
     */
    private Integer categoryId;
    /**
     * 子分类
     */
    private Integer categoryPid;
    /**
     * 分类名
     */
    private String categoryName;
    /**
     * 分类描述
     */
    private String categoryDescription;
    /**
     * 分类热度
     */
    private Integer categoryOrder;
    /**
     * 分类图标
     */
    private String categoryIcon;

    /**
     * 文章数量(非数据库字段)
     */
    private Integer articleCount;

    public Category(Integer categoryId, Integer categoryPid, String categoryName, String categoryDescription, Integer categoryOrder, String categoryIcon, Integer articleCount) {
        this.categoryId = categoryId;
        this.categoryPid = categoryPid;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.categoryOrder = categoryOrder;
        this.categoryIcon = categoryIcon;
        this.articleCount = articleCount;
    }

    public Category(Integer categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Category(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Category() {
    }

    /**
     * 未分类
     *
     * @return 分类
     */
    public static Category Default() {
        return new Category(100000000, "未分类");
    }


}
