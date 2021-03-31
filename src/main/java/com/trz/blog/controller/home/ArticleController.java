package com.trz.blog.controller.home;

import com.alibaba.fastjson.JSON;
import com.trz.blog.entity.Article;
import com.trz.blog.entity.Comment;
import com.trz.blog.entity.Tag;
import com.trz.blog.entity.User;
import com.trz.blog.enums.ArticleStatus;
import com.trz.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/***
 *@Package_name:com.trz.blog.controller.home
 *@Class_name:ArticleController
 *@author:Trz
 *@date:2021/3/27 18:32
 *
 */
@Controller
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CategoryService categoryService;
    /**
     * 具体文章详情显示
     * @param articleId
     * @param model
     * @return
     */
    @RequestMapping(value = "/article/{articleId}")
    public String getArticleDetailPage(@PathVariable("articleId")Integer articleId, Model model){
        Article article = articleService.getArticleByStatusAndId(ArticleStatus.PUBLISH.getValue(), articleId);
        //如果文章为空 则跳转到404
        if(article == null){
            return "Home/Error/404";
        }
        //用户信息
        User user = userService.getUserById(article.getArticleUserId());
        article.setUser(user);
        //文章添加用户信息
        model.addAttribute("article",article);
        //评论列表
        List<Comment> commentList = commentService.listCommentByArticleId(articleId);
        model.addAttribute("commentList",commentList);
        //标签列表
        List<Tag> tagList = tagService.listTagByArticleId(articleId);
        model.addAttribute("tagList",tagList);
        //获得上一篇文章
        Article preArticle = articleService.getPreArticle(articleId);
        model.addAttribute("preArticle",preArticle);
        //获得下一篇文章
        Article afterArticle = articleService.getAfterArticle(articleId);
        model.addAttribute("afterArticle",afterArticle);
        //猜你喜欢看的
        List<Article> mostViewArticleList = articleService.listArticleByViewCount(5);
        model.addAttribute("mostViewArticleList",mostViewArticleList);
        //相关文章
        List<Integer> categoryIds = articleService.listCategoryIdByArticleId(articleId);
        List<Article> similarArticleList = articleService.listArticleByCategoryIds(categoryIds, 5);
        model.addAttribute("similarArticleList",similarArticleList);

        //侧边栏
        //标签栏列表显示
        List<Tag> allTagList = tagService.listTag();
        model.addAttribute("allTagList",allTagList);
        //获取随机文章
        List<Article> randomArticleList = articleService.listRandomArticle(5);
        model.addAttribute("randomArticleList",randomArticleList);
        //获取热评文章
        List<Article> mostCommentArticleList = articleService.listArticleByCommentCount(5);
        model.addAttribute("mostCommentArticleList",mostCommentArticleList);

        return "Home/Page/articleDetail";
    }

    /**
     * 点赞增加
     * @param id
     * @return
     */
    @RequestMapping(value = "article/like/{id}",method = RequestMethod.POST,produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String increaseLikeCount(@PathVariable("id") Integer id){
        //拿到文章
        Article article = articleService.getArticleByStatusAndId(ArticleStatus.PUBLISH.getValue(), id);
        //更新点赞人数
        article.setArticleLikeCount(article.getArticleLikeCount() + 1);
        articleService.updateArticle(article);
        return JSON.toJSONString(article.getArticleLikeCount());
    }

    /**
     * 观看人数增加
     * @param id
     * @return
     */
    @RequestMapping(value = "/article/view/{id}",method = RequestMethod.POST,produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String increaseViewCount(@PathVariable("id")Integer id){
        //拿到文章
        Article article = articleService.getArticleByStatusAndId(ArticleStatus.PUBLISH.getValue(), id);
        //更新观看人数
        article.setArticleViewCount(article.getArticleViewCount() + 1);
        articleService.updateArticle(article);
        return JSON.toJSONString(article.getArticleViewCount());
    }
}
