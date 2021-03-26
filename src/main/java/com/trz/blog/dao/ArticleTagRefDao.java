package com.trz.blog.dao;

import com.trz.blog.entity.Tag;
import com.trz.blog.entity.ArticleTagRef;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/***
 * 文章标签关联表
 *@Package_name:com.trz.blog.dao
 *@Class_name:ArticleTagRefDao
 *@author:Trz
 *@date:2021/3/25 21:19
 *
 */
public interface ArticleTagRefDao {
    
    /**
     * 添加文章和标签关联记录
     * @param record 关联对象
     * @return 影响行数
     */
    @Insert("insert into article_tag_rel(article_id,tag_id) values(#{articleId,jdbcType=INTEGER},#{tagId,jdbcType=INTEGER})")
    int insert(ArticleTagRef record);

    /**
     * 根据标签ID删除记录
     * @param tagId 标签ID
     * @return 影响行数
     */
    @Delete("delete from article_tag_rel where tag_id = #{tagId,jdbcType=INTEGER}")
    int deleteByTagId(Integer tagId);

    /**
     * 根据文章ID删除记录
     * @param articleId 文章ID
     * @return 影响行数
     */
    @Delete("delete from article_tag_rel where article_id = #{articleId,jdbcType=INTEGER}")
    int deleteByArticleId(Integer articleId);

    /**
     * 根据标签ID统计文章数
     * @param tagId 标签ID
     * @return 文章数量
     */
    @Select("select count(*) from article_tag_rel where tag_id = #{tagId,jdbcType=INTEGER}")
    int countArticleByTagId(Integer tagId);

    /**
     * 根据文章获得标签列表
     *
     * @param articleId 文章ID
     * @return 标签列表
     */
    @Select("select article_id,tag_id from article_tag_rel where article_id = #{articleId,jdbcType=INTEGER}")
    @Results({
            @Result(column="article_id",property="articleId",jdbcType = JdbcType.INTEGER),
            @Result(column="tag_id",property="tagId",jdbcType = JdbcType.INTEGER)
    })
    List<Tag> listTagByArticleId(Integer articleId);


}