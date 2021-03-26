package com.trz.blog.dao;

import com.trz.blog.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import javax.annotation.Resource;
import java.util.List;

/***
 * 用户
 *@Package_name:com.trz.blog.dao
 *@Class_name:UserDao
 *@author:Trz
 *@date:2021/3/25 21:19
 *
 */
public interface UserDao {

    /**
     * 根据ID删除
     * @param userId 用户ID
     * @return 影响行数
     */
    @Delete("delete from user where user_id = #{userId,jdbcType=INTEGER} ")
    int deleteById(Integer userId);

    /**
     * 添加
     * 
     * @param user 用户
     * @return 影响行数
     */
    @Insert("insert into user(user_id, user_name, user_pass,user_nickname, user_email, user_url,user_avatar, user_last_login_ip, user_register_time,user_last_login_time, user_status, user_role) values(#{userId,jdbcType=INTEGER},#{userName,jdbcType=VARCHAR},#{userPass,jdbcType=VARCHAR}, #{userNickname,jdbcType=VARCHAR}, #{userEmail,jdbcType=VARCHAR}, #{userUrl,jdbcType=VARCHAR},#{userAvatar,jdbcType=VARCHAR}, #{userLastLoginIp,jdbcType=VARCHAR}, #{userRegisterTime,jdbcType=TIMESTAMP},#{userLastLoginTime,jdbcType=TIMESTAMP}, #{userStatus,jdbcType=INTEGER}, #{userRole,jdbcType=VARCHAR})")
    int insert(User user);

    /**
     * 根据ID查询
     *
     * @param userId 用户ID
     * @return 用户
     */
    @Select("select user_id, user_name, user_pass, user_nickname, user_email, user_url, user_avatar,user_last_login_ip, user_register_time, user_last_login_time, user_status, user_role from user where user_id = #{userId,jdbcType = INTEGER}")
    @Results({
            @Result(column="user_id",property="userId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="user_name",property="userName",jdbcType = JdbcType.VARCHAR),
            @Result(column="user_pass",property="userPass",jdbcType = JdbcType.VARCHAR),
            @Result( column="user_nickname",property="userNickname",jdbcType = JdbcType.VARCHAR),
            @Result(column="user_email",property="userEmail",jdbcType = JdbcType.VARCHAR),
            @Result( column="user_url",property="userUrl",jdbcType = JdbcType.VARCHAR),
            @Result( column="user_avatar",property="userAvatar",jdbcType = JdbcType.VARCHAR),
            @Result( column="user_last_login_ip",property="userLastLoginIp",jdbcType = JdbcType.VARCHAR),
            @Result( column="user_register_time",property="userRegisterTime",jdbcType = JdbcType.TIMESTAMP),
            @Result( column="user_last_login_time",property="userLastLoginTime",jdbcType = JdbcType.TIMESTAMP),
            @Result( column="user_status",property="userStatus",jdbcType = JdbcType.INTEGER),
            @Result( column="user_role",property="userRole",jdbcType = JdbcType.VARCHAR)
    })
    User getUserById(Integer userId);

    /**
     * 更新
     * 
     * @param user 用户
     * @return 影响行数
     */
    @Update("<script> update user set " +
            "<if test='userName != null'>user_name = #{userName,jdbcType=VARCHAR},</if>" +
            "<if test='userPass != null'>user_pass = #{userPass,jdbcType=VARCHAR},</if>" +
            "<if test='userNickname != null'>user_nickname = #{userNickname,jdbcType=VARCHAR},</if>" +
            "<if test='userEmail != null'>user_email = #{userEmail,jdbcType=VARCHAR},</if>" +
            "<if test='userUrl != null'>user_url = #{userUrl,jdbcType=VARCHAR},</if>" +
            "<if test='userAvatar != null'>user_avatar = #{userAvatar,jdbcType=VARCHAR},</if>" +
            "<if test='userLastLoginIp != null'>user_last_login_ip = #{userLastLoginIp,jdbcType=VARCHAR},</if>" +
            "<if test='userRegisterTime != null'>user_register_time = #{userRegisterTime,jdbcType=VARCHAR},</if>" +
            "<if test='userStatus != null'>user_status = #{userStatus,jdbcType=VARCHAR},</if>" +
            "<if test='userRole != null'>user_role = #{userRole,jdbcType=VARCHAR}</if>" +
            "where user_id = #{userId,jdbcType=INTEGER}" +
            "</script> ")
    int update(User user);


    /**
     * 获得用户列表
     * 
     * @return  用户列表
     */
    @Select("select * from user")
    List<User> listUser();


    /**
     * 根据用户名或Email获得用户
     * 用户是没有注销的用户 且只有一个
     * @param str 用户名或Email
     * @return 用户
     */
    @Select("select * from user where user_name = #{str} or user_email = #{str} limit 1")
    User getUserByNameOrEmail(String str);

    /**
     * 根据用户名查用户
     * 
     * @param name 用户名
     * @return 用户
     */
    @Select("select * from user where user_name = #{name} limit 1")
    User getUserByName(String name);

    /**
     * 根据Email查询用户
     * 
     * @param email 邮箱
     * @return 用户
     */
    @Select("select * from user where user_email = #{email} limit 1")
    User getUserByEmail(String email);

}