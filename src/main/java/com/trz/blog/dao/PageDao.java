package com.trz.blog.dao;


import com.trz.blog.entity.Page;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/***
 * 页面
 *@Package_name:com.trz.blog.dao
 *@Class_name:PageDao
 *@author:Trz
 *@date:2021/3/25 21:19
 *
 */
public interface PageDao {
    /**
     * 根据ID删除
     * 
     * @param pageId 页面ID
     * @return 影响行数
     */
    @Delete("delete from page where page_id = #{pageId,jdbcType=INTEGER}")
    int deleteById(Integer pageId);

    /**
     * 添加
     * 
     * @param page 页面
     * @return 影响行数
     */
    @Insert("insert into page(page_id,page_key,page_title,page_content,page_create_time,page_update_time,page_view_count,page_comment_count,page_status) values(#{pageId,jdbcType=INTEGER},#{pageKey,jdbcType=VARCHAR},#{pageTitle,jdbcType=VARCHAR},#{pageContent,jdbcType=VARCHAR},#{pageCreateTime,jdbcType=TIMESTAMP},#{pageUpdateTime,jdbcType=TIMESTAMP},#{pageViewCount,jdbcType=INTEGER},#{pageCommentCount,jdbcType=INTEGER},#{pageStatus,jdbcType=INTEGER})")
    int insert(Page page);

    /**
     * 根据ID查询
     * 
     * @param pageId 页面ID
     * @return 页面
     */
    @Select("select page_id,page_key,page_title,page_content,page_create_time,page_update_time,page_view_count,page_comment_count,page_status from page where page_id = #{pageId,jdbcType=INTEGER}")
    @Results({
            @Result(column="page_id",property="pageId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="page_key",property="pageKey",jdbcType = JdbcType.VARCHAR),
            @Result(column="page_title",property="pageTitle",jdbcType = JdbcType.VARCHAR),
            @Result(column="page_content",property="pageContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="page_create_time",property="pageCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="page_update_time",property="pageUpdateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="page_view_count",property="pageViewCount",jdbcType = JdbcType.INTEGER),
            @Result(column="page_comment_count",property="pageCommentCount",jdbcType = JdbcType.INTEGER),
            @Result(column="page_status",property="pageStatus",jdbcType = JdbcType.INTEGER)
    })
    Page getPageById(Integer pageId);

    /**
     * 更新
     * 
     * @param page 页面
     * @return 影响行数
     */
    @Update("<script>" +
            "update page set" +
            "<if test='pageId != null'>page_id=#{pageId,jdbcType=INTEGER},</if>" +
            "<if test='page_key != null'>page_key=#{pageId,jdbcType=VARCHAR},</if>" +
            "<if test='pageTitle != null'>page_title=#{pageTitle,jdbcType=VARCHAR},</if>" +
            "<if test='pageContent != null'>page_content=#{pageContent,jdbcType=VARCHAR},</if>" +
            "<if test='pageCreateTime != null'>page_create_time=#{pageCreateTime,jdbcType=TIMESTAMP},</if>" +
            "<if test='pageUpdateTime != null'>page_update_time=#{pageUpdateTime,jdbcType=TIMESTAMP},</if>" +
            "<if test='pageViewCount != null'>page_view_count=#{pageViewCount,jdbcType=INTEGER},</if>" +
            "<if test='pageCommentCount != null'>page_comment_count=#{pageCommentCount,jdbcType=INTEGER},</if>" +
            "<if test='pageStatus != null'>page_status=#{pageStatus,jdbcType=INTEGER}</if>" +
            "where page_id = #{pageId,jdbcType=INTEGER}" +
            "</script>")
    int update(Page page);

    /**
     * 获得页面列表
     * 
     * @param status 状态
     * @return 页面列表
     */
    @Select("<script>" +
            "select page_id,page_key,page_title,page_content,page_create_time,page_update_time,page_view_count,page_comment_count,page_status from page where " +
            "<if test='status!=null'>page_status=#{status,jdbcType=INTEGER} and </if>" +
            "1 = 1" +
            "</script>")
    @Results({
            @Result(column="page_id",property="pageId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="page_key",property="pageKey",jdbcType = JdbcType.VARCHAR),
            @Result(column="page_title",property="pageTitle",jdbcType = JdbcType.VARCHAR),
            @Result(column="page_content",property="pageContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="page_create_time",property="pageCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="page_update_time",property="pageUpdateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="page_view_count",property="pageViewCount",jdbcType = JdbcType.INTEGER),
            @Result(column="page_comment_count",property="pageCommentCount",jdbcType = JdbcType.INTEGER),
            @Result(column="page_status",property="pageStatus",jdbcType = JdbcType.INTEGER)
    })
    List<Page> listPage(@Param(value = "status") Integer status);

    /**
     * 根据key获得页面
     * 
     * @param status 状态
     * @param key 别名
     * @return 页面
     */
    @Select("select page_id,page_key,page_title,page_content,page_create_time,page_update_time,page_view_count,page_comment_count,page_status from page where page_status=#{status,jdbcType=INTEGER} and page_key=#{key,jdbcType=VARCHAR} limit 1")
    @Results({
            @Result(column="page_id",property="pageId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="page_key",property="pageKey",jdbcType = JdbcType.VARCHAR),
            @Result(column="page_title",property="pageTitle",jdbcType = JdbcType.VARCHAR),
            @Result(column="page_content",property="pageContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="page_create_time",property="pageCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="page_update_time",property="pageUpdateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="page_view_count",property="pageViewCount",jdbcType = JdbcType.INTEGER),
            @Result(column="page_comment_count",property="pageCommentCount",jdbcType = JdbcType.INTEGER),
            @Result(column="page_status",property="pageStatus",jdbcType = JdbcType.INTEGER)
    })
    Page getPageByKey(@Param(value = "status") Integer status,
                      @Param(value = "key") String key);
}