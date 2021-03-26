package com.trz.blog.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Package_name:com.trz.entity
 * @Class_name:Tag
 * @author:Trz
 * @date:2021/3/12 13:59
 */
@Data
public class Tag implements Serializable {

    private static final long serialVersionUID = 4514489105686120433L;
    /**
     * 标签id
     */
    private Integer tagId;
    /**
     * 标签名
     */
    private String tagName;
    /**
     * 标签描述
     */
    private String tagDescription;
    /**
     * 文章数量(不是数据库字段)
     */
    private Integer articleCount;

    public Tag() {
    }

    public Tag(Integer tagId) {
        this.tagId = tagId;
    }

    public Tag(Integer tagId, String tagName, String tagDescription, Integer articleCount) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.tagDescription = tagDescription;
        this.articleCount = articleCount;
    }
}