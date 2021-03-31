package com.trz.blog.dao;

import com.trz.blog.entity.Link;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/***
 * 链接
 *@Package_name:com.trz.blog.dao
 *@Class_name:LinkDao
 *@author:Trz
 *@date:2021/3/25 21:19
 *
 */
public interface LinkDao {

    /**
     * 删除
     * @param linkId 链接ID
     * @return 影响行数
     */
    @Delete("delete from link where link_id=#{linkId,jdbcType=INTEGER}")
    int deleteById(Integer linkId);

    /**
     * 添加
     * 
     * @param link 链接
     * @return 影响行数
     */
    @Insert("insert into link(link_id,link_url,link_name,link_image,link_description,link_owner_nickname,link_owner_contact,link_update_time,link_create_time,link_order,link_status) values(#{linkId,jdbcType=INTEGER},#{linkUrl,jdbcType=VARCHAR},#{linkName,jdbcType=VARCHAR},#{linkImage,jdbcType=VARCHAR},#{linkDescription,jdbcType=VARCHAR},#{linkOwnerNickname,jdbcType=VARCHAR},#{linkOwnerContact,jdbcType=VARCHAR},#{linkUpdateTime,jdbcType=TIMESTAMP},#{linkCreateTime,jdbcType=TIMESTAMP},#{linkOrder,jdbcType=INTEGER},#{linkStatus,jdbcType=INTEGER},)")
    int insert(Link link);

    /**
     * 根据ID查询
     * 
     * @param linkId 链接ID
     * @return 影响行数
     */
    @Select("select link_id,link_url,link_name,link_image,link_description,link_owner_nickname,link_owner_contact,link_update_time,link_create_time,link_order,link_status from link where link_id=#{linkId,jdbcType=INTEGER}")
    @Results({
            @Result(column="link_id",property="linkId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="link_url",property="linkUrl",jdbcType = JdbcType.VARCHAR),
            @Result(column="link_name",property="linkName",jdbcType = JdbcType.VARCHAR),
            @Result(column="link_image",property="linkImage",jdbcType = JdbcType.VARCHAR),
            @Result(column="link_description",property="linkDescription",jdbcType = JdbcType.VARCHAR),
            @Result(column="link_owner_nickname",property="linkOwnerNickname",jdbcType = JdbcType.VARCHAR),
            @Result(column="link_owner_contact",property="linkOwnerContact",jdbcType = JdbcType.VARCHAR),
            @Result(column="link_update_time",property="linkUpdateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="link_create_time",property="linkCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="link_order",property="linkOrder",jdbcType = JdbcType.INTEGER),
            @Result(column="link_status",property="linkStatus",jdbcType = JdbcType.INTEGER)
    })
    Link getLinkById(Integer linkId);

    /**
     * 更新
     * 
     * @param link 链接ID
     * @return 影响行数
     */
    @Update("<script>" +
            "update link set" +
            "<if test='linkId!=null'>link_id=#{linkId,jdbcType=INTEGER},</if>" +
            "<if test='linkUrl!=null'>link_url=#{linkUrl,jdbcType=INTEGER},</if>" +
            "<if test='linkName!=null'>link_name=#{linkName,jdbcType=INTEGER},</if>" +
            "<if test='linkImage!=null'>link_image=#{linkImage,jdbcType=INTEGER},</if>" +
            "<if test='linkDescription!=null'>link_description=#{linkDescription,jdbcType=INTEGER},</if>" +
            "<if test='linkOwnerNickname!=null'>link_owner_nickname=#{linkOwnerNickname,jdbcType=INTEGER},</if>" +
            "<if test='linkOwnerContact!=null'>link_owner_contact=#{linkOwnerContact,jdbcType=INTEGER},</if>" +
            "<if test='linkUpdateTime!=null'>link_update_time=#{linkUpdateTime,jdbcType=INTEGER},</if>" +
            "<if test='linkCreateTime!=null'>link_create_time=#{linkCreateTime,jdbcType=INTEGER},</if>" +
            "<if test='linkOrder!=null'>link_order=#{linkOrder,jdbcType=INTEGER},</if>" +
            "<if test='linkStatus!=null'>link_status=#{linkStatus,jdbcType=INTEGER},</if>" +
            "where link_id=#{linkId,jdbcType=INTEGER}" +
            "</script>")
    int update(Link link);

    /**
     * 获得链接总数
     * 
     * @param status 状态
     * @return 数量
     */
    @Select("select count(*) from link where link_status=#{status,jdbcType=INTEGER}")
    Integer countLink(@Param(value = "status") Integer status);

    /**
     * 获得链接列表
     * 
     * @param status 状态
     * @return  列表
     */
    @Select("<script>" +
            "select link_id,link_url,link_name,link_image,link_description,link_owner_nickname,link_owner_contact,link_update_time,link_create_time,link_order,link_status from link" +
            "where" +
            "<if test='status!=null'> link_status = #{status,jdbcType=INTEGER} and</if>" +
            "1 = 1" +
            "</script>")
    @Results({
            @Result(column="link_id",property="linkId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="link_url",property="linkUrl",jdbcType = JdbcType.VARCHAR),
            @Result(column="link_name",property="linkName",jdbcType = JdbcType.VARCHAR),
            @Result(column="link_image",property="linkImage",jdbcType = JdbcType.VARCHAR),
            @Result(column="link_description",property="linkDescription",jdbcType = JdbcType.VARCHAR),
            @Result(column="link_owner_nickname",property="linkOwnerNickname",jdbcType = JdbcType.VARCHAR),
            @Result(column="link_owner_contact",property="linkOwnerContact",jdbcType = JdbcType.VARCHAR),
            @Result(column="link_update_time",property="linkUpdateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="link_create_time",property="linkCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="link_order",property="linkOrder",jdbcType = JdbcType.INTEGER),
            @Result(column="link_status",property="linkStatus",jdbcType = JdbcType.INTEGER)
    })
    List<Link> listLink(@Param(value = "status") Integer status);
}