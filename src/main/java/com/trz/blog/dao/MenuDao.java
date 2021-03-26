package com.trz.blog.dao;

import com.trz.blog.entity.Menu;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/***
 * 菜单
 *@Package_name:com.trz.blog.dao
 *@Class_name:MenuDao
 *@author:Trz
 *@date:2021/3/25 21:19
 *
 */
public interface MenuDao {

    /**
     * 删除
     *
     * @param menuId 菜单ID
     * @return 影响行数
     */
    @Delete("delete from menu where menu_id=#{menuId,jdbcType=INTEGER}")
    int deleteById(Integer menuId);

    /**
     * 添加
     * @param menu 菜单
     * @return 影响行数
     */
    @Insert("insert into menu(menu_id,menu_name,menu_url,menu_level,menu_icon,menu_order) values(#{menuId,jdbcType=INTEGER},#{menuName,jdbcType=VARCHAR},#{menuUrl,jdbcType=VARCHAR},#{menuLevel,jdbcType=INTEGER},#{menuIcon,jdbcType=VARCHAR},#{menuOrder,jdbcType=INTEGER})")
    int insert(Menu menu);

    /**
     * 根据ID查询
     *
     * @param menuId 菜单ID
     * @return 菜单
     */
    @Select("select menu_id,menu_name,menu_url,menu_level,menu_icon,menu_order from menu where menu_id=#{menuId,jdbcType=INTEGER}")
    @Results({
            @Result(column="menu_id",property="menuId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="menu_name",property="menuName",jdbcType = JdbcType.VARCHAR),
            @Result(column="menu_url",property="menuUrl",jdbcType = JdbcType.VARCHAR),
            @Result(column="menu_level",property="menuLevel",jdbcType = JdbcType.INTEGER),
            @Result(column="menu_icon",property="menuIcon",jdbcType = JdbcType.VARCHAR),
            @Result(column="menu_order",property="menuOrder",jdbcType = JdbcType.INTEGER)
    })
    Menu getMenuById(Integer menuId);

    /**
     * 更新
     * 
     * @param menu 菜单
     * @return 影响行数
     */
    @Update("<script>" +
            "update menu set" +
            "<if test='menuId!=null'>menu_id=#{menuId},</if>" +
            "<if test='menuName!=null'>menu_name=#{menuName},</if>" +
            "<if test='menuUrl!=null'>menu_url=#{menuUrl},</if>" +
            "<if test='menuLevel!=null'>menu_level=#{menuLevel},</if>" +
            "<if test='menuIcon!=null'>menu_icon=#{menuIcon},</if>" +
            "<if test='menuOrder!=null'>menu_order=#{menuOrder},</if>" +
            "where menu_id=#{menuId,jdbcType=INTEGER}" +
            "</script>")
    int update(Menu menu);

    /**
     * 获得菜单列表
     * 
     * @return 列表
     */
    @Select("select menu_id,menu_name,menu_url,menu_level,menu_icon,menu_order from menu")
    @Results({
            @Result(column="menu_id",property="menuId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="menu_name",property="menuName",jdbcType = JdbcType.VARCHAR),
            @Result(column="menu_url",property="menuUrl",jdbcType = JdbcType.VARCHAR),
            @Result(column="menu_level",property="menuLevel",jdbcType = JdbcType.INTEGER),
            @Result(column="menu_icon",property="menuIcon",jdbcType = JdbcType.VARCHAR),
            @Result(column="menu_order",property="menuOrder",jdbcType = JdbcType.INTEGER)
    })
    List<Menu> listMenu() ;
}