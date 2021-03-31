package com.trz.blog.controller.admin;

import cn.hutool.http.HtmlUtil;
import com.github.pagehelper.PageInfo;
import com.trz.blog.entity.Article;
import com.trz.blog.entity.Comment;
import com.trz.blog.entity.User;
import com.trz.blog.enums.ArticleStatus;
import com.trz.blog.enums.Role;
import com.trz.blog.enums.UserRole;
import com.trz.blog.service.ArticleService;
import com.trz.blog.service.CommentService;
import com.trz.blog.util.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/***
 *@Package_name:com.trz.blog.controller.admin
 *@Class_name:BackCommentController
 *@author:Trz
 *@date:2021/3/27 18:32
 *
 */
@Controller
@RequestMapping("/admin/comment")
public class BackCommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;

    /**
     * 评论首页
     *
     * @param pageIndex 页码
     * @param pageSize  页大小
     * @param session   会话
     * @param model     模型
     * @return
     */
    @RequestMapping("")
    public String commentList(@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                              HttpSession session, Model model) {
        HashMap<String, Object> criteria = new HashMap<>(1);
        //1、拿到用户
        User user = (User) session.getAttribute("user");
        //2、用户只能拿到自己的 管理员可以看到所有的
        if(!user.getUserRole().equals(UserRole.ADMIN.getValue())){
            criteria.put("userId",user.getUserId());
        }
        //3、查询出评论
        PageInfo<Comment> commentPageInfo = commentService.listCommentByPage(pageIndex, pageSize, criteria);
        model.addAttribute("pageInfo",commentPageInfo);
        model.addAttribute("pageUrlPrefix","/admin/comment?pageIndex");
        return "Admin/Comment/index";
    }

    /**
     * 身份是普通用户
     * 有下拉列表 选择 【评论我的】 和 【我评论的】
     *
     * @param pageIndex
     * @param pageSize
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/receive")
    public String myReceiveComment(@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                   @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                   HttpSession session, Model model) {
        HashMap<String, Object> criteria = new HashMap<>(1);
        User user = (User) session.getAttribute("user");
        criteria.put("userId",user.getUserId());
        PageInfo<Comment> commentPageInfo = commentService.listCommentByPage(pageIndex, pageSize, criteria);
        model.addAttribute("pageInfo",commentPageInfo);
        model.addAttribute("pageUrlPrefix","/admin/comment?pageIndex");
        return "Admin/Comment/index";
    }

    /**
     * 添加评论
     *
     * @param request
     * @param comment
     * @param session
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public void insertComment(HttpServletRequest request, Comment comment, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Article article = articleService.getArticleByStatusAndId(null, comment.getCommentArticleId());
        if(article == null){
            return;
        }
        //1、填充评论的属性
        comment.setCommentUserId(user.getUserId());
        comment.setCommentIp(MyUtils.getIpAddr(request));
        comment.setCommentCreateTime(new Date());
        commentService.insertComment(comment);
        //2、更新文章的评论数
        articleService.updateCommentCount(article.getArticleId());
    }

    /**
     * 删除评论
     *
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public String deleteComment(@PathVariable("id") Integer id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Comment comment = commentService.getCommentById(id);
        if(comment == null){
            return "redirect:/404";
        }
        if(!Objects.equals(user.getUserRole(),UserRole.ADMIN.getValue()) && !Objects.equals(comment.getCommentUserId(),user.getUserId())){
            return "redirect:/admin/comment";
        }
        //1、删除评论
        commentService.deleteComment(id);
        //2、删除子评论
        List<Comment> comments = commentService.listChildComment(id);
        for (Comment comment1 : comments) {
            commentService.deleteComment(comment1.getCommentId());
        }
        //3、更新文章评论数
        articleService.updateCommentCount(comment.getCommentArticleId());
        return "redirect:/admin/comment";
    }

    /**
     * 编辑评论页面
     * @param id
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public String editCommentView(@PathVariable("id") Integer id, Model model, HttpSession session) {
        //1、只能管理员能编辑评论
        User user = (User) session.getAttribute("user");
        if (!Objects.equals(user.getUserRole(), UserRole.ADMIN.getValue())) {
            return "redirect:/403";
        }
        //2、如果没有评论 则跳到404
        Comment comment = commentService.getCommentById(id);
        if(comment == null){
            return "redirect:/404";
        }
        //3、加入到模型中
        model.addAttribute("comment",comment);
        return "redirect:/admin/comment";
    }

    /**
     * 编辑评论提交
     * @param comment
     * @param session
     * @return
     */
    @RequestMapping(value = "/editSubmit",method = RequestMethod.POST)
    public String editCommentSubmit(Comment comment, HttpSession session){
        User user = (User) session.getAttribute("user");
        //1、没有权限操作,只有管理员可以操作
        if (!Objects.equals(user.getUserRole(), UserRole.ADMIN.getValue())) {
            return "redirect:/403";
        }
        commentService.updateComment(comment);
        return "redirect:/admin/comment";
    }

    /**
     * 回复评论页面显示
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/reply/{id}")
    public String replyCommentView(@PathVariable("id") Integer id,Model model){
        Comment comment = commentService.getCommentById(id);
        model.addAttribute("comment",comment);
        return "redirect:/admin/Comment/reply";
    }

    /**
     * 回复评论提交
     * @param request
     * @param comment
     * @param session
     * @return
     */
    @RequestMapping(value = "/replySubmit",method = RequestMethod.POST)
    public String replyCommentSubmit(HttpServletRequest request,Comment comment,HttpSession session){
        Article article = articleService.getArticleByStatusAndId(ArticleStatus.PUBLISH.getValue(), comment.getCommentArticleId());
        //1、文章不存在就404
        if(article == null){
            return "redirect:/404";
        }
        User user = (User) session.getAttribute("user");
        /*
         * 更新文章
         */
        comment.setCommentContent(HtmlUtil.escape(comment.getCommentContent()));
        //2、评论人的昵称
        comment.setCommentAuthorName(user.getUserNickname());
        //3、评论人的邮箱
        comment.setCommentAuthorEmail(user.getUserEmail());
        //4、评论人的url
        comment.setCommentAuthorUrl(user.getUserUrl());
        //5、文章评论数
        article.setArticleCommentCount(article.getArticleCommentCount() + 1);

        articleService.updateArticle(article);
        /*
         * 更新评论
         */
        //6、评论日期
        comment.setCommentCreateTime(new Date());

        //7、设置ip
        comment.setCommentIp(MyUtils.getIpAddr(request));

        //8、自己评论自己的文章 还是 其他人评论
        if (Objects.equals(user.getUserId(), article.getArticleUserId())) {
            comment.setCommentRole(Role.OWNER.getValue());
        } else {
            comment.setCommentRole(Role.VISITOR.getValue());
        }

        //9、添加评论
        commentService.insertComment(comment);
        return "redirect:/admin/comment";
    }
}
