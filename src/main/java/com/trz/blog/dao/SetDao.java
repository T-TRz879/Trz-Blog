package com.trz.blog.dao;

import com.trz.blog.entity.Set;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/***
 * 设置
 *@Package_name:com.trz.blog.dao
 *@Class_name:SetDao
 *@author:Trz
 *@date:2021/3/25 21:19
 *
 */
public interface SetDao {

    /**
     * 根据ID删除
     * @param setId 系统设置ID
     * @return 影响行数
     */
    @Delete("delete from set where set_id = #{setId,jdbcType=INTEGER}")
    int deleteById(Integer setId);

    /**
     * 添加
     * @param sets 系统设置
     * @return 影响行数
     */
    @Insert("insert into sets(set_id,set_site_title,set_site_description,set_meta_description,set_meta_keyword,set_absoutsite_avatar,set_aboutsite_title," +
            "set_aboutsite_content,set_aboutsite_wechat,set_aboutsite_qq,set_aboutsite_github,set_aboutsite_weibo,set_tongji,set_status) " +
            "values(#{setId,jdbcType=INTEGER},#{setSiteTitle,jdbcType=VARCHAR},#{setSiteDescription,jdbcType=VARCHAR},#{setMetaDescription,jdbcType=VARCHAR}," +
            "#{setMetaKeyword,jdbcType=VARCHAR},#{setAboutsiteAvatar,jdbcType=VARCHAR},#{setAboutsiteTitle,jdbcType=VARCHAR},#{setAboutsiteContent,jdbcType=VARCHAR}," +
            "#{setAboutsiteWechat,jdbcType=VARCHAR},#{setAboutsiteQq,jdbcType=VARCHAR},#{setAboutsiteGithub,jdbcType=VARCHAR},#{setAboutsiteWeibo,jdbcType=VARCHAR}," +
            "#{setTongji,jdbcType=VARCHAR},#{setStatus,jdbcType=INTEGER})")
    int insert(Set sets);

    /**
     * 根据ID查询
     *
     * @param setId 系统设置ID
     * @return 系统设置
     */
    @Select("select * from sets where set_id=#{setId,jdbcType=INTEGER} limit 1")
    @Results({
            @Result(column="set_id",property="setId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="set_site_title",property="setSiteTitle",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_site_description",property="setSiteDescription",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_meta_description",property="setMetaDescription",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_meta_keyword",property="setMetaKeyword",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_aboutsite_avatar",property="setAboutsiteAvatar",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_aboutsite_title",property="setAboutsiteTitle",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_aboutsite_content",property="setAboutsiteContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_aboutsite_wechat",property="setAboutsiteWechat",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_aboutsite_qq",property="setAboutsiteQq",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_aboutsite_github",property="setAboutsiteGithub",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_aboutsite_weibo",property="setAboutsiteWeibo",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_tongji",property="setTongji",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_status",property="setStatus",jdbcType = JdbcType.VARCHAR)
    })
    Set getSetsById(Integer setId);

    /**
     * 更新
     *
     * @param sets 系统信息
     * @return 影响行数
     */
    @Update("<script>" +
            "update sets set" +
            "<if test='setId!=null'>set_id=#{set_id,jdbcType=INTEGER},</if>" +
            "<if test='setSiteTitle!=null'>set_site_title=#{set_site_title,jdbcType=VARCHAR},</if>" +
            "<if test='setSiteDescription!=null'>set_site_description=#{set_site_description,jdbcType=VARCHAR},</if>" +
            "<if test='setMetaDescription!=null'>set_meta_description=#{set_meta_description,jdbcType=VARCHAR},</if>" +
            "<if test='setMetaKeyword!=null'>set_meta_keyword=#{set_meta_keyword,jdbcType=VARCHAR},</if>" +
            "<if test='setAboutsiteAvatar!=null'>set_aboutsite_avatar=#{set_aboutsite_avatar,jdbcType=VARCHAR},</if>" +
            "<if test='setAboutsiteTitle!=null'>set_aboutsite_title=#{set_aboutsite_title,jdbcType=VARCHAR},</if>" +
            "<if test='setAboutsiteContent!=null'>set_aboutsite_content=#{set_aboutsite_content,jdbcType=VARCHAR},</if>" +
            "<if test='setAboutsiteWechat!=null'>set_aboutsite_wechat=#{set_aboutsite_wechat,jdbcType=VARCHAR},</if>" +
            "<if test='setAboutsiteQq!=null'>set_aboutsite_qq=#{set_aboutsite_qq,jdbcType=VARCHAR},</if>" +
            "<if test='setAboutsiteGithub!=null'>set_aboutsite_github=#{set_aboutsite_github,jdbcType=VARCHAR},</if>" +
            "<if test='setAboutsiteWeibo!=null'>set_aboutsite_weibo=#{set_aboutsite_weibo,jdbcType=VARCHAR},</if>" +
            "<if test='setTongji!=null'>set_tongji=#{set_tongji,jdbcType=VARCHAR},</if>" +
            "<if test='setStatus!=null'>set_status=#{set_status,jdbcType=VARCHAR},</if>" +
            "</script>")
    int update(Set sets);

    /**
     * 获得记录
     *
     * @return 系统信息
     */
    @Select("select * from sets")
    @Results({
            @Result(column="set_id",property="setId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="set_site_title",property="setSiteTitle",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_site_description",property="setSiteDescription",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_meta_description",property="setMetaDescription",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_meta_keyword",property="setMetaKeyword",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_aboutsite_avatar",property="setAboutsiteAvatar",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_aboutsite_title",property="setAboutsiteTitle",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_aboutsite_content",property="setAboutsiteContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_aboutsite_wechat",property="setAboutsiteWechat",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_aboutsite_qq",property="setAboutsiteQq",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_aboutsite_github",property="setAboutsiteGithub",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_aboutsite_weibo",property="setAboutsiteWeibo",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_tongji",property="setTongji",jdbcType = JdbcType.VARCHAR),
            @Result(column="set_status",property="setStatus",jdbcType = JdbcType.VARCHAR)
    })
    List<Set> getSets();
}