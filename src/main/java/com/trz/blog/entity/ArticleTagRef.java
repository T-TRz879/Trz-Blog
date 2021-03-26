package com.trz.blog.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Package_name:com.trz.entity
 * @Class_name:ArticleTagRef
 * @author:Trz
 * @date:2021/3/12 13:59
 */
@Data
public class ArticleTagRef implements Serializable {

    private static final long serialVersionUID = -5816783232020910492L;
    /**
     * 文章ID
     */
    private Integer articleId;
    /**
     * 标签ID
     */
    private Integer tagId;

    public ArticleTagRef() {
    }

    public ArticleTagRef(Integer articleId, Integer tagId) {
        this.articleId = articleId;
        this.tagId = tagId;
    }
}
