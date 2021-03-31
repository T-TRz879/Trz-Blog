package com.trz.blog.dao;

import com.trz.blog.entity.Article;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/***
 * 文章
 *@Package_name:com.trz.blog.dao
 *@Class_name:ArticleDao
 *@author:Trz
 *@date:2021/3/25 21:19
 *
 */
public interface ArticleDao {

    /**
     * 根据ID删除
     *
     * @param articleId 文章ID
     * @return 影响函数
     */
    @Delete("delete from article where article_id=#{articleId,jdbcType=INTEGER}")
    Integer deleteById(Integer articleId);

    /**
     * 根据用户ID删除
     *
     * @param userId 用户ID
     * @return 影响函数
     */
    @Delete("delete from article where article_user_id=#{articleUserId,jdbcType=INTEGER}")
    Integer deleteByUserId(Integer userId);

    /**
     * 添加文章
     *
     * @param article 文章
     * @return 文章
     */
    @Insert("insert into article(article_id,article_user_id,article_title,article_content,article_view_count,article_comment_count,article_like_count,article_is_comment,article_status,article_order,article_update_time,article_create_time,article_summary) values(#{articleId,jdbcType=INTEGER},#{articleUserId,jdbcType=INTEGER},#{articleTitle,jdbcType=VARCHAR},#{articleViewCount,jdbcType=INTEGER},#{articleCommentCount,jdbcType=INTEGER},#{articleLikeCount,jdbcType=INTEGER},#{articleCreateTime,jdbcType=TIMESTAMP},#{articleUpdateTime,jdbcType=TIMESTAMP},#{articleIsComment,jdbcType=INTEGER},#{articleStatus,jdbcType=INTEGER},#{articleOrder,jdbcType=INTEGER},#{articleContent,jdbcType=VARCHAR},#{articleSummary,jdbcType=VARCHAR})")
    Integer insert(Article article);

    /**
     * 更新文章
     *
     * @param article 文章
     * @return 影响行数
     */
    @Update("<script>" +
            "update article set" +
            "<if test='articleId!=null'>article_id=#{articleId,jdbcType=INTEGER},</if>" +
            "<if test='articleUserId!=null'>article_user_id=#{articleUserId,jdbcType=INTEGER},</if>" +
            "<if test='articleTitle!=null'>article_title=#{articleTitle,jdbcType=VARCHAR},</if>" +
            "<if test='articleContent!=null'>article_content=#{articleContent,jdbcType=VARCHAR},</if>" +
            "<if test='articleViewCount!=null'>article_view_count=#{articleViewCount,jdbcType=INTEGER},</if>" +
            "<if test='articleCommentCount!=null'>article_comment_count=#{articleCommentCount,jdbcType=INTEGER},</if>" +
            "<if test='articleLikeCount!=null'>article_like_count=#{articleLikeCount,jdbcType=INTEGER},</if>" +
            "<if test='articleIsComment!=null'>article_is_comment=#{articleIsComment,jdbcType=INTEGER},</if>" +
            "<if test='articleStatus!=null'>article_status=#{articleStatus,jdbcType=INTEGER},</if>" +
            "<if test='articleOrder!=null'>article_order=#{articleOrder,jdbcType=INTEGER},</if>" +
            "<if test='articleUpdateTime!=null'>article_update_time=#{articleUpdateTime,jdbcType=TIMESTAMP},</if>" +
            "<if test='articleCreateTime!=null'>article_create_time=#{articleId,jdbcType=TIMESTAMP},</if>" +
            "<if test='articleSummary!=null'>article_summary=#{articleSummary,jdbcType=VARCHAR}</if>" +
            "where article_id=#{articleId,jdbcType=INTEGER}" +
            "</script>")
    Integer update(Article article);

    /**
     * 获得所有的文章
     *
     * @param criteria 查询条件
     * @return 文章列表
     */
    @Select("<script>" +
            "select `article`.* from `article`" +
            "where" +
            "<if test='status != null'> `article`.`article_status` = #{status} AND </if>" +
            "<if test='keywords != null'> `article`.`article_title` LIKE concat('%',#{keywords},'%') AND </if>" +
            "<if test='userId != null'> `article`.`article_user_id` = #{userId} AND </if>" +
            "<if test='categoryId != null'> `article`.`article_id` IN (SELECT `article_category_ref`.`article_id` FROM `article_category_ref` WHERE `article_category_ref`.`category_id` = #{categoryId}) AND </if>" +
            "<if test='tagId != null'> `article`.`article_id` IN (SELECT `article_tag_ref`.`article_id` FROM `article_tag_ref` WHERE `article_tag_ref`.`tag_id` = #{tagId}) AND </if>" +
            "1 = 1 " +
            "ORDER BY `article`.`article_order` DESC, `article`.`article_id` DESC" +
            "</script>")
    @Results({
            @Result(column="article_id",property="articleId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="article_user_id",property="articleUserId",jdbcType = JdbcType.INTEGER),
            @Result(column="article_title",property="articleTitle",jdbcType = JdbcType.VARCHAR),
            @Result(column="article_content",property="articleContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="article_view_count",property="articleViewCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_comment_count",property="articleCommentCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_like_count",property="articleLikeCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_is_comment",property="articleIsComment",jdbcType = JdbcType.INTEGER),
            @Result(column="article_status",property="articleStatus",jdbcType = JdbcType.INTEGER),
            @Result(column="article_order",property="articleOrder",jdbcType = JdbcType.INTEGER),
            @Result(column="article_update_time",property="articleUpdateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="article_create_time",property="articleCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="article_summary",property="articleSummary",jdbcType = JdbcType.VARCHAR)
    })
    List<Article> findAll(HashMap<String, Object> criteria);

    /**
     * 文章归档
     * @return
     */
    @Select("select article_id, article_user_id, article_title, article_create_time from article where article_status = 1 ORDER BY article_id DESC")
    List<Article> listAllNotWithContent();

    /**
     * 获取文章总数
     *
     * @param status 状态
     * @return 数量
     */
    @Select("select count(*) from article where article_status=#{status,jdbcType=INTEGER}")
    Integer countArticle(@Param(value = "status") Integer status);

    /**
     * 获得留言总数
     *
     * @return 数量
     */
    @Select("select SUM(article_comment_count) from article where article_status=1")
    Integer countArticleComment();

    /**
     * 获得浏览量总数
     *
     * @return 文章数量
     */
    @Select("select SUM(article_view_count) from article where article_status=1")
    Integer countArticleView();

    /**
     * 获得所有文章(文章归档)
     *
     * @return 文章列表
     */
    @Select("select article_id,article_user_id,article_title,article_content,article_view_count,article_comment_count,article_like_count,article_is_comment,article_status,article_order,article_update_time,article_create_time,article_summary from article WHERE article_status = 1 ORDER BY article_status ASC, article_order DESC, article_id DESC")
    List<Article> listArticle();

    /**
     * 根据id查询用户信息
     *
     * @param status 状态
     * @param id 文章ID
     * @return 文章
     */
    @Select("<script>" +
            "select article_id,article_user_id,article_title,article_content,article_view_count,article_comment_count,article_like_count,article_is_comment,article_status,article_order,article_update_time,article_create_time,article_summary from article where " +
            "<if test='status != null'> article_status=#{status,jdbcType=INTEGER} and </if>" +
            " article_id=#{id,jdbcType=INTEGER}" +
            "</script>")
    @Results({
            @Result(column="article_id",property="articleId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="article_user_id",property="articleUserId",jdbcType = JdbcType.INTEGER),
            @Result(column="article_title",property="articleTitle",jdbcType = JdbcType.VARCHAR),
            @Result(column="article_content",property="articleContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="article_view_count",property="articleViewCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_comment_count",property="articleCommentCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_like_count",property="articleLikeCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_is_comment",property="articleIsComment",jdbcType = JdbcType.INTEGER),
            @Result(column="article_status",property="articleStatus",jdbcType = JdbcType.INTEGER),
            @Result(column="article_order",property="articleOrder",jdbcType = JdbcType.INTEGER),
            @Result(column="article_update_time",property="articleUpdateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="article_create_time",property="articleCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="article_summary",property="articleSummary",jdbcType = JdbcType.VARCHAR)
    })
    Article getArticleByStatusAndId(@Param(value = "status") Integer status, @Param(value = "id") Integer id);

    /**
     * 分页操作
     *
     * @param status    状态
     * @param pageIndex 从第几页开始
     * @param pageSize  数量
     * @return 文章列表
     */
    @Deprecated
    @Select("<script>" +
            "select article_id,article_user_id,article_title,article_content,article_view_count,article_comment_count,article_like_count,article_is_comment,article_status,article_order,article_update_time,article_create_time,article_summary from article where " +
            "<if test='status!=null'>article_status=#{status,jdbcType=INTEGER}</if>" +
            "ORDER BY article_status ASC, article_order DESC, article_id DESC " +
            "limit #{pageIndex,jdbcType=INTEGER},#{pageSize,jdbcType=INTEGER}" +
            "</script>")
    @Results({
            @Result(column="article_id",property="articleId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="article_user_id",property="articleUserId",jdbcType = JdbcType.INTEGER),
            @Result(column="article_title",property="articleTitle",jdbcType = JdbcType.VARCHAR),
            @Result(column="article_content",property="articleContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="article_view_count",property="articleViewCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_comment_count",property="articleCommentCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_like_count",property="articleLikeCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_is_comment",property="articleIsComment",jdbcType = JdbcType.INTEGER),
            @Result(column="article_status",property="articleStatus",jdbcType = JdbcType.INTEGER),
            @Result(column="article_order",property="articleOrder",jdbcType = JdbcType.INTEGER),
            @Result(column="article_update_time",property="articleUpdateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="article_create_time",property="articleCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="article_summary",property="articleSummary",jdbcType = JdbcType.VARCHAR)
    })
    List<Article> pageArticle(@Param(value = "status") Integer status,
                                    @Param(value = "pageIndex") Integer pageIndex,
                                    @Param(value = "pageSize") Integer pageSize);


    /**
     * 获得访问最多的文章(猜你喜欢)
     *
     * @param limit 查询数量
     * @return 文章列表
     */
    @Select("select article_id,article_user_id,article_title,article_content,article_view_count,article_comment_count,article_like_count,article_is_comment,article_status,article_order,article_update_time,article_create_time,article_summary from article where article_status=1 ORDER BY article_view_count DESC,article_order DESC, article_id DESC limit #{limit}")
    @Results({
            @Result(column="article_id",property="articleId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="article_user_id",property="articleUserId",jdbcType = JdbcType.INTEGER),
            @Result(column="article_title",property="articleTitle",jdbcType = JdbcType.VARCHAR),
            @Result(column="article_content",property="articleContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="article_view_count",property="articleViewCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_comment_count",property="articleCommentCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_like_count",property="articleLikeCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_is_comment",property="articleIsComment",jdbcType = JdbcType.INTEGER),
            @Result(column="article_status",property="articleStatus",jdbcType = JdbcType.INTEGER),
            @Result(column="article_order",property="articleOrder",jdbcType = JdbcType.INTEGER),
            @Result(column="article_update_time",property="articleUpdateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="article_create_time",property="articleCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="article_summary",property="articleSummary",jdbcType = JdbcType.VARCHAR)
    })
    List<Article> listArticleByViewCount(@Param(value = "limit") Integer limit);

    /**
     * 获得上一篇文章
     *
     * @param id 文章ID
     * @return 文章
     */
    @Select("select article_id,article_user_id,article_title,article_content,article_view_count,article_comment_count,article_like_count,article_is_comment,article_status,article_order,article_update_time,article_create_time,article_summary from article where article_id < #{id,jdbcType=INTEGER} AND article_status = 1 ORDER BY article_id limit 1")
    @Results({
            @Result(column="article_id",property="articleId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="article_user_id",property="articleUserId",jdbcType = JdbcType.INTEGER),
            @Result(column="article_title",property="articleTitle",jdbcType = JdbcType.VARCHAR),
            @Result(column="article_content",property="articleContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="article_view_count",property="articleViewCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_comment_count",property="articleCommentCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_like_count",property="articleLikeCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_is_comment",property="articleIsComment",jdbcType = JdbcType.INTEGER),
            @Result(column="article_status",property="articleStatus",jdbcType = JdbcType.INTEGER),
            @Result(column="article_order",property="articleOrder",jdbcType = JdbcType.INTEGER),
            @Result(column="article_update_time",property="articleUpdateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="article_create_time",property="articleCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="article_summary",property="articleSummary",jdbcType = JdbcType.VARCHAR)
    })
    Article getAfterArticle(@Param(value = "id") Integer id);

    /**
     * 获得下一篇文章
     *
     * @param id 文章ID
     * @return 文章
     */
    @Select("select article_id,article_user_id,article_title,article_content,article_view_count,article_comment_count,article_like_count,article_is_comment,article_status,article_order,article_update_time,article_create_time,article_summary from article where article_id > #{id,jdbcType=INTEGER} AND article_status = 1 ORDER BY article_id limit 1")
    @Results({
            @Result(column="article_id",property="articleId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="article_user_id",property="articleUserId",jdbcType = JdbcType.INTEGER),
            @Result(column="article_title",property="articleTitle",jdbcType = JdbcType.VARCHAR),
            @Result(column="article_content",property="articleContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="article_view_count",property="articleViewCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_comment_count",property="articleCommentCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_like_count",property="articleLikeCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_is_comment",property="articleIsComment",jdbcType = JdbcType.INTEGER),
            @Result(column="article_status",property="articleStatus",jdbcType = JdbcType.INTEGER),
            @Result(column="article_order",property="articleOrder",jdbcType = JdbcType.INTEGER),
            @Result(column="article_update_time",property="articleUpdateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="article_create_time",property="articleCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="article_summary",property="articleSummary",jdbcType = JdbcType.VARCHAR)
    })
    Article getPreArticle(@Param(value = "id") Integer id);

    /**
     * 获得随机文章
     *
     * @param limit 查询数量
     * @return 文章列表
     */
    @Select("select article_id,article_user_id,article_title,article_content,article_view_count,article_comment_count,article_like_count,article_is_comment,article_status,article_order,article_update_time,article_create_time,article_summary from article where article_status=1 ORDER BY RAND() limit #{limit}")
    @Results({
            @Result(column="article_id",property="articleId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="article_user_id",property="articleUserId",jdbcType = JdbcType.INTEGER),
            @Result(column="article_title",property="articleTitle",jdbcType = JdbcType.VARCHAR),
            @Result(column="article_content",property="articleContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="article_view_count",property="articleViewCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_comment_count",property="articleCommentCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_like_count",property="articleLikeCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_is_comment",property="articleIsComment",jdbcType = JdbcType.INTEGER),
            @Result(column="article_status",property="articleStatus",jdbcType = JdbcType.INTEGER),
            @Result(column="article_order",property="articleOrder",jdbcType = JdbcType.INTEGER),
            @Result(column="article_update_time",property="articleUpdateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="article_create_time",property="articleCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="article_summary",property="articleSummary",jdbcType = JdbcType.VARCHAR)
    })
    List<Article> listRandomArticle(@Param(value = "limit") Integer limit);

    /**
     * 热评文章
     *
     * @param limit  查询数量
     * @return 文章列表
     */
    @Select("select article_id,article_user_id,article_title,article_content,article_view_count,article_comment_count,article_like_count,article_is_comment,article_status,article_order,article_update_time,article_create_time,article_summary from article where article_status=1 ORDER BY article_comment_count DESC,article_order DESC, article_id DESC limit #{limit}")
    @Results({
            @Result(column="article_id",property="articleId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="article_user_id",property="articleUserId",jdbcType = JdbcType.INTEGER),
            @Result(column="article_title",property="articleTitle",jdbcType = JdbcType.VARCHAR),
            @Result(column="article_content",property="articleContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="article_view_count",property="articleViewCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_comment_count",property="articleCommentCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_like_count",property="articleLikeCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_is_comment",property="articleIsComment",jdbcType = JdbcType.INTEGER),
            @Result(column="article_status",property="articleStatus",jdbcType = JdbcType.INTEGER),
            @Result(column="article_order",property="articleOrder",jdbcType = JdbcType.INTEGER),
            @Result(column="article_update_time",property="articleUpdateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="article_create_time",property="articleCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="article_summary",property="articleSummary",jdbcType = JdbcType.VARCHAR)
    })
    List<Article> listArticleByCommentCount(@Param(value = "limit") Integer limit);



    /**
     * 更新文章的评论数
     *
     * @param articleId 文章ID
     * @return
     */
    @Update("update article set article_comment_count = (SELECT count(*) FROM `comment` WHERE article.article_id=comment.comment_article_id) WHERE article_id=#{articleId}")
    Integer updateCommentCount(@Param(value = "articleId") Integer articleId);

    /**
     * 获得最后更新的记录
     *
     * @return 文章
     */
    @Select("select article_id,article_user_id,article_title,article_content,article_view_count,article_comment_count,article_like_count,article_is_comment,article_status,article_order,article_update_time,article_create_time,article_summary from article where article_status=1 and article_update_time=(SELECT max(article_update_time) FROM article)")
    @Results({
            @Result(column="article_id",property="articleId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="article_user_id",property="articleUserId",jdbcType = JdbcType.INTEGER),
            @Result(column="article_title",property="articleTitle",jdbcType = JdbcType.VARCHAR),
            @Result(column="article_content",property="articleContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="article_view_count",property="articleViewCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_comment_count",property="articleCommentCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_like_count",property="articleLikeCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_is_comment",property="articleIsComment",jdbcType = JdbcType.INTEGER),
            @Result(column="article_status",property="articleStatus",jdbcType = JdbcType.INTEGER),
            @Result(column="article_order",property="articleOrder",jdbcType = JdbcType.INTEGER),
            @Result(column="article_update_time",property="articleUpdateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="article_create_time",property="articleCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="article_summary",property="articleSummary",jdbcType = JdbcType.VARCHAR)
    })
    Article getLastUpdateArticle();

    /**
     * 用户的文章数
     *
     * @param id 用户ID
     * @return 数量
     */
    @Select("select count(*) from article where article_user_id = #{id,jdbcType=INTEGER} and article_status = 1")
    Integer countArticleByUser(@Param(value = "id") Integer id);

    /**
     * 根据分类ID
     *
     * @param categoryId 分类ID
     * @param limit      查询数量
     * @return 文章列表
     */
    @Select("select `article`.`article_id`, `article`.`article_user_id`, `article`.`article_title`,`article`.`article_view_count`,`article`.`article_comment_count`,`article`.`article_like_count`, `article`.`article_create_time`, `article`.`article_update_time`,`article`.`article_is_comment`, `article`.`article_status`, `article`.`article_order`,`article`.`article_summary` FROM `article` , `article_category_ref` where `article`.`article_status` = 1 AND `article`.`article_id` = `article_category_ref`.`article_id` AND `article_category_ref`.`category_id` = #{categoryId,jdbcType=INTEGER} LIMIT #{limit,jdbcType=INTEGER}")

    List<Article> findArticleByCategoryId(@Param("categoryId") Integer categoryId,
                                          @Param("limit") Integer limit);

    /**
     * 根据分类ID
     *
     * @param categoryIds 分类ID集合
     * @param limit       查询数量
     * @return 文章列表
     */
    @Select("<script>" +
            "select article.article_id, article.article_user_id, article.article_title,article.article_view_count, article.article_comment_count,article.article_like_count, article.article_create_time, article.article_update_time,article.article_is_comment, article.article_status, article.article_order,article.article_summary FROM article, article_category_ref where article.article_status = 1 AND article.article_id = article_category_ref.article_id AND article_category_ref.category_id" +
            "<if test='categoryIds!=null'>" +
            "in" +
            "<foreach collection=\"categoryIds\" open=\"(\" close=\")\" separator=\",\" item=\"id\">" +
            "#{id}" +
            "</foreach>" +
            "</if>" +
            "limit #{limit}" +
            "</script>")
    List<Article> findArticleByCategoryIds(@Param("categoryIds") List<Integer> categoryIds,
                                           @Param("limit") Integer limit);

    /**
     * 获得最新文章
     * @param userId 用户id
     * @param limit 查询数量
     * @return 列表
     */
    @Select("<script>" +
            "select article_id, article_user_id, article_title, article_view_count, article_comment_count, article_like_count, article_create_time,article_update_time, article_is_comment, article_status, article_order from article where" +
            "<if test='userId != null'>article_user_id = #{userId}</if>" +
            "ORDER BY article_id DESC  LIMIT #{limit}" +
            "</script>")
    List<Article> listArticleByLimit(@Param("userId") Integer userId, @Param("limit") Integer limit);

    /**
     * 批量删除文章
     *
     * @param ids 文章Id列表
     * @return 影响行数
     */
    @Delete("<script>" +
            "delete from article" +
            "where" +
            "article_id in" +
            "<foreach collection=\"ids\" open=\"(\" close=\")\" separator=\",\" item=\"id\">" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    Integer deleteBatch(@Param("ids") List<Integer> ids);

    /**
     * 获得一个用户的文章id集合
     * @param userId
     * @return
     */
    @Select("select article_id from article where article_user_id=#{userId,jdbcType=INTEGER}")
    List<Integer> listArticleIdsByUserId(Integer userId);

    /**
     * 通过文章id获取文章
     * @param articleId
     * @return
     */
    @Select("select article_id,article_user_id,article_title,article_content,article_view_count,article_comment_count,article_like_count,article_is_comment,article_status,article_order,article_update_time,article_create_time,article_summary from article where article_id = #{articleId,jdbcType=INTEGER}")
    @Results({
            @Result(column="article_id",property="articleId",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column="article_user_id",property="articleUserId",jdbcType = JdbcType.INTEGER),
            @Result(column="article_title",property="articleTitle",jdbcType = JdbcType.VARCHAR),
            @Result(column="article_content",property="articleContent",jdbcType = JdbcType.VARCHAR),
            @Result(column="article_view_count",property="articleViewCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_comment_count",property="articleCommentCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_like_count",property="articleLikeCount",jdbcType = JdbcType.INTEGER),
            @Result(column="article_is_comment",property="articleIsComment",jdbcType = JdbcType.INTEGER),
            @Result(column="article_status",property="articleStatus",jdbcType = JdbcType.INTEGER),
            @Result(column="article_order",property="articleOrder",jdbcType = JdbcType.INTEGER),
            @Result(column="article_update_time",property="articleUpdateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="article_create_time",property="articleCreateTime",jdbcType = JdbcType.TIMESTAMP),
            @Result(column="article_summary",property="articleSummary",jdbcType = JdbcType.VARCHAR)
    })
    Article getByArticleId(Integer articleId);
}