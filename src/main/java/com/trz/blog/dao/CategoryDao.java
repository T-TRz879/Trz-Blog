package com.trz.blog.dao;

import com.trz.blog.entity.Category;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/***
 * 分类
 *@Package_name:com.trz.blog.dao
 *@Class_name:CategoryDao
 *@author:Trz
 *@date:2021/3/25 21:19
 *
 */
public interface CategoryDao {

    /**
     * 添加
     *
     * @param category 分类
     * @return 影响行数
     */
    @Insert("insert into category(category_id,category_pid,category_name,category_description,category_order,category_icon) values(#{categoryId,jdbcType=INTEGER},#{categoryPid,jdbcType=INTEGER},#{categoryName,jdbcType=VARCHAR},#{categoryDescription,jdbcType=VARCHAR},#{categoryOrder,jdbcType=INTEGER},#{categoryIcon,jdbcType=VARCHAR})")
    int insert(Category category);


    /**
     * 更新
     *
     * @param category 分类
     * @return 影响行数
     */
    @Update("<script>" +
            "update category set" +
            "<if test='categoryId!=null'>category_id=#{categoryId,jdbcType=INTEGER},</if>" +
            "<if test='categoryPid!=null'>category_pid=#{categoryPid,jdbcType=INTEGER},</if>" +
            "<if test='categoryName!=null'>category_name=#{categoryName,jdbcType=VARCHAR},</if>" +
            "<if test='categoryDescription!=null'>category_description=#{categoryDescription,jdbcType=VARCHAR},</if>" +
            "<if test='categoryOrder!=null'>category_order=#{categoryOrder,jdbcType=INTEGER},</if>" +
            "<if test='categoryIcon!=null'>category_icon=#{categoryIcon,jdbcType=VARCHAR}</if>" +
            "where category_id=#{categoryId,jdbcType=INTEGER}" +
            "</script>")
    int update(Category category);

    /**
     * 根据分类id获得分类信息
     *
     * @param id ID
     * @return 分类
     */
    @Select("select category_id,category_pid,category_name,category_description,category_order,category_icon from category where category_id=#{id,jdbcType=INTEGER}")
    @Results({
            @Result(column="category_id",property="categoryId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="category_pid",property="categoryPid",jdbcType = JdbcType.INTEGER),
            @Result(column="category_name",property="categoryName",jdbcType = JdbcType.VARCHAR),
            @Result(column="category_description",property="categoryDescription",jdbcType = JdbcType.VARCHAR),
            @Result(column="category_order",property="categoryOrder",jdbcType = JdbcType.INTEGER),
            @Result(column="category_icon",property="categoryIcon",jdbcType = JdbcType.VARCHAR)
    })
    Category getCategoryById(Integer id);


    /**
     * 删除分类
     *
     * @param id 文章ID
     * @return 删除是否成功
     */
    @Delete("delete from category where category_id=#{id,jdbcType=INTEGER}")
    int deleteCategory(Integer id);

    /**
     * 查询分类总数
     *
     * @return 数量
     */
    @Select("select count(*) from category")
    Integer countCategory();

    /**
     * 获得分类列表
     *
     * @return 列表
     */
    @Select("select category_id,category_pid,category_name,category_description,category_order,category_icon from category")
    @Results({
            @Result(column="category_id",property="categoryId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="category_pid",property="categoryPid",jdbcType = JdbcType.INTEGER),
            @Result(column="category_name",property="categoryName",jdbcType = JdbcType.VARCHAR),
            @Result(column="category_description",property="categoryDescription",jdbcType = JdbcType.VARCHAR),
            @Result(column="category_order",property="categoryOrder",jdbcType = JdbcType.INTEGER),
            @Result(column="category_icon",property="categoryIcon",jdbcType = JdbcType.VARCHAR)
    })
    List<Category> listCategory();

    /**
     * 根据父分类找子分类
     *
     * @param id 分类ID
     * @return 列表
     */
    @Select("select category_id,category_pid,category_name,category_description,category_order,category_icon from category where category_pid=#{id,jdbcType=INTEGER}")
    @Results({
            @Result(column="category_id",property="categoryId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="category_pid",property="categoryPid",jdbcType = JdbcType.INTEGER),
            @Result(column="category_name",property="categoryName",jdbcType = JdbcType.VARCHAR),
            @Result(column="category_description",property="categoryDescription",jdbcType = JdbcType.VARCHAR),
            @Result(column="category_order",property="categoryOrder",jdbcType = JdbcType.INTEGER),
            @Result(column="category_icon",property="categoryIcon",jdbcType = JdbcType.VARCHAR)
    })
    List<Category> findChildCategory(@Param(value = "id") Integer id);

    /**
     * 根据标签名获取标签
     *
     * @param name 名称
     * @return 分类
     */
    @Select("select category_id,category_pid,category_name,category_description,category_order,category_icon from category where category_name=#{categoryName,jdbcType=VARCHAR} limit 1")
    @Results({
            @Result(column="category_id",property="categoryId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="category_pid",property="categoryPid",jdbcType = JdbcType.INTEGER),
            @Result(column="category_name",property="categoryName",jdbcType = JdbcType.VARCHAR),
            @Result(column="category_description",property="categoryDescription",jdbcType = JdbcType.VARCHAR),
            @Result(column="category_order",property="categoryOrder",jdbcType = JdbcType.INTEGER),
            @Result(column="category_icon",property="categoryIcon",jdbcType = JdbcType.VARCHAR)
    })
    Category getCategoryByName(String name);
}