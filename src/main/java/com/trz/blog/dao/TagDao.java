package com.trz.blog.dao;

import com.trz.blog.entity.Tag;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/***
 * 标签
 *@Package_name:com.trz.blog.dao
 *@Class_name:TagDao
 *@author:Trz
 *@date:2021/3/25 21:19
 *
 */
public interface TagDao {

    /**
     * 根据ID删除
     * 
     * @param tagId 标签ID
     * @return 影响行数
     */
    @Delete("delete from tag where tag_id = #{tagId,jdbcType=INTEGER}")
    int deleteById(Integer tagId);

    /**
     * 添加
     * 
     * @param tag 标签
     * @return 影响行数
     */
    @Insert("insert into tag(tag_id,tag_name,tag_description) values(#{tagId,jdbcType=INTEGER},#{tagName,jdbcType=VARCHAR},#{tagDescription,jdbcType=VARCHAR})")
    int insert(Tag tag);

    /**
     * 根据ID查询
     *
     * @param tagId 标签ID
     * @return 标签
     */
    @Select("select tag_id,tag_name,tag_description from tag where tag_id = #{tagId,jdbcType=INTEGER}")
    @Results({
            @Result(column="tag_id",property="tagId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="tag_name",property="tagName",jdbcType = JdbcType.VARCHAR),
            @Result(column="tag_description",property="tagDescription",jdbcType = JdbcType.VARCHAR)
    })
    Tag getTagById(Integer tagId);

    /**
     * 更新
     * @param tag 标签
     * @return 影响行数
     */
    @Update("<script>" +
            "update tag set " +
            "<if test='tagName != null'>tag_name=#{tagName,jdbcType=VARCHAR},</if>" +
            "<if test='tagDescription != null'>tag_description=#{tagDescription,jdbcType=VARCHAR}</if>" +
            "where tag_id=#{tagId,jdbcType=INTEGER}" +
            "</script>")
    int update(Tag tag);

    /**
     * 获得标签总数
     * 
     * @return 数量
     */
    @Select("select count(*) from tag")
    Integer countTag() ;

    /**
     * 获得标签列表
     * 
     * @return 列表
     */
    @Select("select tag_id,tag_name,tag_description from tag")
    @Results({
            @Result(column="tag_id",property="tagId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="tag_name",property="tagName",jdbcType = JdbcType.VARCHAR),
            @Result(column="tag_description",property="tagDescription",jdbcType = JdbcType.VARCHAR)
    })
    List<Tag> listTag() ;


    /**
     * 根据标签名获取标签
     * 
     * @param name 名称
     * @return 标签
     */
    @Select("select tag_id,tag_name,tag_description from tag where tag_name=#{tagName,jdbcType=VARCHAR}")
    @Results({
            @Result(column="tag_id",property="tagId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="tag_name",property="tagName",jdbcType = JdbcType.VARCHAR),
            @Result(column="tag_description",property="tagDescription",jdbcType = JdbcType.VARCHAR)
    })
    Tag getTagByName(String name) ;
}