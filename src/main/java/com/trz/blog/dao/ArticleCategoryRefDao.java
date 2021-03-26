package com.trz.blog.dao;

import com.trz.blog.entity.ArticleCategoryRef;
import com.trz.blog.entity.Category;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/***
 * 文章分类关联表
 *@Package_name:com.trz.blog.dao
 *@Class_name:ArticleCategoryRefDao
 *@author:Trz
 *@date:2021/3/25 21:19
 *
 */
public interface ArticleCategoryRefDao {
    
    /**
     * 添加文章和分类关联记录
     * @param record 关联对象
     * @return 影响行数
     */
    @Insert("insert into article_category_ref(article_id,category_id) values(#{articleId,jdbcType=INTEGER},#{categoryId,jdbcType=INTEGER})")
    int insert(ArticleCategoryRef record);

    /**
     * 根据分类ID删除记录
     * @param categoryId 分类ID
     * @return 影响行数
     */
    @Delete("delete from article_category_ref where category_id = #{categoryId,jdbcType=INTEGER}")
    int deleteByCategoryId(Integer categoryId);

    /**
     * 根据文章ID删除记录
     * @param articleId 文章ID
     * @return 影响行数
     */
    @Delete("delete from article_category_ref where article_id = #{articleId,jdbcType=INTEGER}")
    int deleteByArticleId(Integer articleId);

    /**
     * 根据分类ID统计文章数
     * @param categoryId 分类ID
     * @return 文章数量
     */
    @Select("select count(*) from article_category_ref where category_id = #{categoryId,jdbcType=INTEGER}")
    int countArticleByCategoryId(Integer categoryId);


    /**
     * 根据文章ID查询分类ID
     *
     * @param articleId 文章ID
     * @return 分类ID列表
     */
    @Select("select category_id from article_category_ref where article_id = #{articleId,jdbcType=INTEGER}")
    @Results({
            @Result(column="article_id",property="articleId",jdbcType = JdbcType.INTEGER),
            @Result(column="category_id",property="categoryId",jdbcType = JdbcType.INTEGER)
    })
    List<Integer> selectCategoryIdByArticleId(Integer articleId);

    /**
     * 根据分类ID查询文章ID
     *
     * @param categoryId 分类ID
     * @return 文章ID列表
     */
    @Select("select article_id from article_category_ref where category_id = #{categoryId,jdbcType=INTEGER}")
    @Results({
            @Result(column="article_id",property="articleId",jdbcType = JdbcType.INTEGER),
            @Result(column="category_id",property="categoryId",jdbcType = JdbcType.INTEGER)
    })
    List<Integer> selectArticleIdByCategoryId(Integer categoryId);

    /**
     * 根据文章ID获得分类列表
     *
     * @param articleId 文章ID
     * @return 分类列表
     */
    @Select("select category.category_id, category.category_pid, category.category_name FROM category, article_category_ref WHERE article_category_ref.article_id = #{value} AND article_category_ref.category_id = category.category_id ORDER BY category.category_pid asc")
    @Results({
            @Result(column="article_id",property="articleId",jdbcType = JdbcType.INTEGER),
            @Result(column="category_id",property="categoryId",jdbcType = JdbcType.INTEGER)
    })
    List<Category> listCategoryByArticleId(Integer articleId);

}