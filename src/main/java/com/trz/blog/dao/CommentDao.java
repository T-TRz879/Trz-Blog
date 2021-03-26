package com.trz.blog.dao;


import com.trz.blog.entity.Comment;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.HashMap;
import java.util.List;

/***
 * 评论
 *@Package_name:com.trz.blog.dao
 *@Class_name:CommentDao
 *@author:Trz
 *@date:2021/3/25 21:19
 *
 */
public interface CommentDao {

    /**
     * 根据ID删除
     *
     * @param commentId 评论ID
     * @return 影响行数
     */
    @Delete("delete from comment where comment_id=#{commentId,jdbcType=INTEGER}")
    int deleteById(Integer commentId);

    /**
     * 添加
     *
     * @param comment 评论
     * @return 影响行数
     */
    @Insert("insert into comment(comment_id, comment_pid, comment_pname, comment_article_id, comment_author_name,comment_author_email, comment_author_url, comment_author_avatar, comment_content, comment_agent,comment_ip,comment_create_time, comment_role,comment_user_id) values(#{commentId,jdbcType=INTEGER},#{commentPid,jdbcType=INTEGER},#{commentPname,jdbcType=VARCHAR},#{commentArticleId,jdbcType=INTEGER},#{commentAuthorName,jdbcType=VARCHAR},#{commentAuthorEmail,jdbcType=VARCHAR},#{commentAuthorUrl,jdbcType=VARCHAR},#{commentAuthorAvatar,jdbcType=VARCHAR},#{commentContent,jdbcType=VARCHAR},#{commentAgent,jdbcType=VARCHAR},#{commentIp,jdbcType=VARCHAR},#{commentCreateTime,jdbcType=TIMESTAMP},#{commentRole,jdbcType=INTEGER},#{commentUserId,jdbcType=INTEGER})")
    int insert(Comment comment);

    /**
     * 根据ID查询
     *
     * @param commentId 评论ID
     * @return 评论
     */
    @Select("select comment_id, comment_pid, comment_pname, comment_article_id, comment_author_name,comment_author_email, comment_author_url, comment_author_avatar, comment_content, comment_agent,comment_ip,comment_create_time, comment_role,comment_user_id from comment where comment_id=#{commentId,jdbcType=INTEGER}")
    @Results({
            @Result(column="comment_id",property="commentId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="comment_pid",property="commentPid",jdbcType = JdbcType.INTEGER),
            @Result(column="comment_pname",property="commentPname",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_article_id",property="commentArticleId",jdbcType = JdbcType.INTEGER),
            @Result(column="comment_author_name",property="commentAuthorName",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_author_email",property="commentAuthorEmail",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_author_url",property="commentAuthorUrl",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_author_avatar",property="commentAuthorAvatar",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_content",property="commentContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_agent",property="commentAgent",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_ip",property="commentIp",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_create_time",property="commentCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="comment_role",property="commentRole",jdbcType = JdbcType.INTEGER),
            @Result(column="comment_user_id",property="commentUserId",jdbcType = JdbcType.INTEGER),

    })
    Comment getCommentById(Integer commentId);

    /**
     * 更新
     *
     * @param comment 评论
     * @return 影响行数
     */
    @Update("<script>" +
            "update comment set" +
            "<if test='commentId!=null'>comment_id=#{commentId,jdbcType=INTEGER},</if>" +
            "<if test='commentPid!=null'>comment_pid=#{commentPid,jdbcType=INTEGER},</if>" +
            "<if test='commentPname!=null'>comment_pname=#{commentPname,jdbcType=VARCHAR},</if>" +
            "<if test='commentArticleId!=null'>comment_article_id=#{commentArticleId,jdbcType=INTEGER},</if>" +
            "<if test='commentAuthorName!=null'>comment_author_name=#{commentAuthorName,jdbcType=VARCHAR},</if>" +
            "<if test='commentAuthorEmail!=null'>comment_author_email=#{commentAuthorEmail,jdbcType=VARCHAR},</if>" +
            "<if test='commentAuthorUrl!=null'>comment_author_url=#{commentAuthorUrl,jdbcType=VARCHAR},</if>" +
            "<if test='commentAuthorAvatar!=null'>comment_author_avatar=#{commentAuthorAvatar,jdbcType=VARCHAR},</if>" +
            "<if test='commentContent!=null'>comment_content=#{commentContent,jdbcType=VARCHAR},</if>" +
            "<if test='commentAgent!=null'>comment_agent=#{commentAgent,jdbcType=VARCHAR},</if>" +
            "<if test='commentIp!=null'>comment_ip=#{commentIp,jdbcType=VARCHAR},</if>" +
            "<if test='commentCreateTime!=null'>comment_create_time=#{commentCreateTime,jdbcType=TIMESTAMP},</if>" +
            "<if test='commentRole!=null'>comment_role=#{commentRole,jdbcType=VARCHAR},</if>" +
            "<if test='commentUserId!=null'>comment_user_id=#{commentUserId,jdbcType=INTEGER}</if>" +
            "where comment_id=#{commentId,jdbcType=INTEGER}" +
            "</script>")
    int update(Comment comment);

    /**
     * 根据文章id获取评论列表
     *
     * @param id ID
     * @return 列表
     */
    @Select("select comment_id, comment_pid, comment_pname, comment_article_id, comment_author_name,comment_author_email, comment_author_url, comment_author_avatar, comment_content, comment_agent,comment_ip,comment_create_time, comment_role,comment_user_id from comment where comment_article_id=#{id,jdbcType=INTEGER}")
    @Results({
            @Result(column="comment_id",property="commentId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="comment_pid",property="commentPid",jdbcType = JdbcType.INTEGER),
            @Result(column="comment_pname",property="commentPname",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_article_id",property="commentArticleId",jdbcType = JdbcType.INTEGER),
            @Result(column="comment_author_name",property="commentAuthorName",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_author_email",property="commentAuthorEmail",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_author_url",property="commentAuthorUrl",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_author_avatar",property="commentAuthorAvatar",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_content",property="commentContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_agent",property="commentAgent",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_ip",property="commentIp",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_create_time",property="commentCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="comment_role",property="commentRole",jdbcType = JdbcType.INTEGER),
            @Result(column="comment_user_id",property="commentUserId",jdbcType = JdbcType.INTEGER),

    })
    List<Comment> listCommentByArticleId(@Param(value = "id") Integer id);


    /**
     * 获得评论列表
     * @param criteria  这里是一个键值对
     *                  userId 用户id
     * @return 列表
     */
    @Select("<script>" +
            "select comment_id, comment_pid, comment_pname, comment_article_id, comment_author_name,comment_author_email, comment_author_url, comment_author_avatar, comment_content, comment_agent,comment_ip,comment_create_time, comment_role,comment_user_id from comment where " +
            "<if test='userId!=null'>comment_user_id=#{userId,jdbcType=INTEGER}</if>" +
            "</script>")
    @Results({
            @Result(column="comment_id",property="commentId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="comment_pid",property="commentPid",jdbcType = JdbcType.INTEGER),
            @Result(column="comment_pname",property="commentPname",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_article_id",property="commentArticleId",jdbcType = JdbcType.INTEGER),
            @Result(column="comment_author_name",property="commentAuthorName",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_author_email",property="commentAuthorEmail",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_author_url",property="commentAuthorUrl",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_author_avatar",property="commentAuthorAvatar",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_content",property="commentContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_agent",property="commentAgent",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_ip",property="commentIp",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_create_time",property="commentCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="comment_role",property="commentRole",jdbcType = JdbcType.INTEGER),
            @Result(column="comment_user_id",property="commentUserId",jdbcType = JdbcType.INTEGER),

    })
    List<Comment> listComment(HashMap<String, Object> criteria);


    /**
     * 获得某个用户收到的评论
     * @param articleIds
     * @return 列表
     */
    @Select("<script>" +
            "select comment_id, comment_pid, comment_pname, comment_article_id, comment_author_name,comment_author_email, comment_author_url, comment_author_avatar, comment_content, comment_agent,comment_ip,comment_create_time, comment_role,comment_user_id from comment where " +
            "comment_article_id in(" +
            "<foreach collection=\"list\" item=\"item\" separator=\",\">" +
            "#{item}" +
            "</foreach>" +
            ")" +
            "</script>")
    @Results({
            @Result(column="comment_id",property="commentId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="comment_pid",property="commentPid",jdbcType = JdbcType.INTEGER),
            @Result(column="comment_pname",property="commentPname",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_article_id",property="commentArticleId",jdbcType = JdbcType.INTEGER),
            @Result(column="comment_author_name",property="commentAuthorName",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_author_email",property="commentAuthorEmail",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_author_url",property="commentAuthorUrl",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_author_avatar",property="commentAuthorAvatar",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_content",property="commentContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_agent",property="commentAgent",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_ip",property="commentIp",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_create_time",property="commentCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="comment_role",property="commentRole",jdbcType = JdbcType.INTEGER),
            @Result(column="comment_user_id",property="commentUserId",jdbcType = JdbcType.INTEGER),

    })
    List<Comment> getReceiveComment(List<Integer> articleIds);


    /**
     * 统计评论数
     *
     * @return 数量
     */
    @Select("select count(*) from comment")
    Integer countComment();

    /**
     * 获得最近评论，访客的评论
     *
     * @param limit 查询数量
     * @param userId 用户id
     * @return 列表
     */
    @Select("<script>" +
            "select comment_id, comment_pid, comment_pname, comment_article_id, comment_author_name,comment_author_email, comment_author_url, comment_author_avatar, comment_content, comment_agent,comment_ip,comment_create_time, comment_role,comment_user_id from comment where" +
            "<if test='userId!=null'>comment_user_id=#{userId,jdbcType=INTEGER}</if>" +
            "order by comment_id desc " +
            "limit #{limit}" +
            "</script>")
    @Results({
            @Result(column="comment_id",property="commentId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="comment_pid",property="commentPid",jdbcType = JdbcType.INTEGER),
            @Result(column="comment_pname",property="commentPname",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_article_id",property="commentArticleId",jdbcType = JdbcType.INTEGER),
            @Result(column="comment_author_name",property="commentAuthorName",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_author_email",property="commentAuthorEmail",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_author_url",property="commentAuthorUrl",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_author_avatar",property="commentAuthorAvatar",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_content",property="commentContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_agent",property="commentAgent",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_ip",property="commentIp",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_create_time",property="commentCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="comment_role",property="commentRole",jdbcType = JdbcType.INTEGER),
            @Result(column="comment_user_id",property="commentUserId",jdbcType = JdbcType.INTEGER),

    })
    List<Comment> listRecentComment(@Param(value = "userId") Integer userId,
                                    @Param(value = "limit") Integer limit);

    /**
     * 获得评论的子评论
     *
     * @param id 评论ID
     * @return 列表
     */
    @Select("select comment_id, comment_pid, comment_pname, comment_article_id, comment_author_name,comment_author_email, comment_author_url, comment_author_avatar, comment_content, comment_agent,comment_ip,comment_create_time, comment_role,comment_user_id from comment where comment_pid=#{id,jdbcType=INTEGER}")
    @Results({
            @Result(column="comment_id",property="commentId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="comment_pid",property="commentPid",jdbcType = JdbcType.INTEGER),
            @Result(column="comment_pname",property="commentPname",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_article_id",property="commentArticleId",jdbcType = JdbcType.INTEGER),
            @Result(column="comment_author_name",property="commentAuthorName",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_author_email",property="commentAuthorEmail",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_author_url",property="commentAuthorUrl",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_author_avatar",property="commentAuthorAvatar",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_content",property="commentContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_agent",property="commentAgent",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_ip",property="commentIp",jdbcType = JdbcType.VARCHAR),
            @Result(column="comment_create_time",property="commentCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="comment_role",property="commentRole",jdbcType = JdbcType.INTEGER),
            @Result(column="comment_user_id",property="commentUserId",jdbcType = JdbcType.INTEGER),

    })
    List<Comment> listChildComment(@Param(value = "id") Integer id);


    /**
     * 根据用户ID删除
     *
     * @param userId 用户ID
     * @return 影响函数
     */
    @Delete("delete from comment where comment_user_id=#{userId,jdbcType=INTEGER}")
    Integer deleteByUserId(Integer userId);


    /**
     * 根据文章ID删除
     *
     * @param articleId 文章ID
     * @return 影响函数
     */
    @Delete("delete from comment where comment_article_id=#{article,jdbcType=INTEGER}")
    Integer deleteByArticleId(Integer articleId);
}