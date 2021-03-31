package com.trz.blog.dao;

import com.trz.blog.entity.Notice;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
/***
 * 提示
 *@Package_name:com.trz.blog.dao
 *@Class_name:NoticeDao
 *@author:Trz
 *@date:2021/3/25 21:19
 *
 */
public interface NoticeDao {

    /**
     * 根据ID删除
     *
     * @param noticeId 公告ID
     * @return 影响行数
     */
    @Delete("delete from notice where notice_id=#{noticeId,jdbcType=INTEGER}")
    int deleteById(Integer noticeId);

    /**
     * 添加
     *
     * @param notice 公告
     * @return 影响行数
     */
    @Insert("insert into notice(notice_id,notice_title,notice_content,notice_create_time,notice_update_time,notice_status,notice_order) values(#{noticeId,jdbcType=INTEGER},#{noticeTitle,jdbcType=VARCHAR},#{noticeContent,jdbcType=VARCHAR},#{noticeCreateTime,jdbcType=TIMESTAMP},#{noticeUpdateTime,jdbcType=TIMESTAMP},#{noticeStatus,jdbcType=INTEGER},#{noticeOrder,jdbcType=INTEGER})")
    int insert(Notice notice);

    /**
     * 根据ID查询
     *
     * @param noticeId 公告ID
     * @return 公告
     */
    @Select("select notice_id,notice_title,notice_content,notice_create_time,notice_update_time,notice_status,notice_order from notice where notice_id=#{noticeId,jdbcType=INTEGER}")
    @Results({
            @Result(column="notice_id",property="noticeId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="notice_title",property="noticeTitle",jdbcType = JdbcType.VARCHAR),
            @Result(column="notice_content",property="noticeContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="notice_create_time",property="noticeCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="notice_update_time",property="noticeUpdateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="notice_status",property="noticeStatus",jdbcType = JdbcType.INTEGER),
            @Result(column="notice_order",property="noticeOrder",jdbcType = JdbcType.INTEGER)
    })
    Notice getNoticeById(Integer noticeId);

    /**
     * 获得公告列表
     *
     * @param notice 公告
     * @return 影响行数
     */
    @Update("<script>" +
            "update notice set" +
            "<if test='noticeId!=null'>notice_id=#{noticeId,jdbcType=INTEGER},</if>" +
            "<if test='noticeTitle!=null'>notice_title=#{noticeTitle,jdbcType=VARCHAR},</if>" +
            "<if test='noticeContent!=null'>notice_content=#{noticeContent,jdbcType=VARCHAR},</if>" +
            "<if test='noticeCreateTime!=null'>notice_create_time=#{noticeCreateTime,jdbcType=TIMESTAMP},</if>" +
            "<if test='noticeUpdateTime!=null'>notice_update_time=#{noticeUpdateTime,jdbcType=TIMESTAMP},</if>" +
            "<if test='noticeStatus!=null'>notice_status=#{noticeStatus,jdbcType=INTEGER},</if>" +
            "<if test='noticeOrder!=null'>notice_order=#{noticeOrder,jdbcType=INTEGER}</if>" +
            "where notice_id = #{noticeId,jdbcType=INTEGER}" +
            "</script>")
    int update(Notice notice);

    /**
     * 获得公告列表
     *
     * @param notice 公告
     * @return 影响行数
     */
    @Update("update notice set" +
            "notice_id=#{noticeId,jdbcType=INTEGER}," +
            "notice_title=#{noticeTitle,jdbcType=VARCHAR}" +
            "notice_content=#{noticeContent,jdbcType=VARCHAR}" +
            "notice_create_time=#{noticeCreate,jdbcType=TIMESTAMP}" +
            "notice_update_time=#{noticeUpdate,jdbcType=TIMESTAMP}" +
            "notice_status=#{noticeStatus,jdbcType=INTEGER}" +
            "notice_order=#{noticeOrder,jdbcType=INTEGER}" +
            "where notice_id=#{noticeId,jdbcType=INTEGER}")
    int updateByPrimaryKey(Notice notice);

    /**
     * 获得公告总数
     *
     * @param status 状态
     * @return 影响行数
     */
    @Select("select count(*) from notice where notice_status=#{status,jdbcType=INTEGER}")
    Integer countNotice(@Param(value = "status") Integer status);

    /**
     * 获得公告列表
     *
     * @param status 状态
     * @return 公告列表
     */
    @Select("<script>" +
            "select notice_id,notice_title,notice_content,notice_create_time,notice_update_time,notice_status,notice_order from notice " +
            "where " +
            "<if test='status!=null'>notice_status=#{status,jdbcType=INTEGER} and </if>" +
            "1 = 1" +
            "" +
            "</script>")
    @Results({
            @Result(column="notice_id",property="noticeId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="notice_title",property="noticeTitle",jdbcType = JdbcType.VARCHAR),
            @Result(column="notice_content",property="noticeContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="notice_create_time",property="noticeCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="notice_update_time",property="noticeUpdateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="notice_status",property="noticeStatus",jdbcType = JdbcType.INTEGER),
            @Result(column="notice_order",property="noticeOrder",jdbcType = JdbcType.INTEGER)
    })
    List<Notice> listNotice(@Param(value = "status") Integer status);
}