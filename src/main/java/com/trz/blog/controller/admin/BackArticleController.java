package com.trz.blog.controller.admin;

import cn.hutool.http.HtmlUtil;
import com.github.pagehelper.PageInfo;
import com.trz.blog.dto.ArticleParam;
import com.trz.blog.entity.Article;
import com.trz.blog.entity.Category;
import com.trz.blog.entity.Tag;
import com.trz.blog.entity.User;
import com.trz.blog.enums.UserRole;
import com.trz.blog.service.ArticleService;
import com.trz.blog.service.CategoryService;
import com.trz.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;

/***
 *@Package_name:com.trz.blog.controller.admin
 *@Class_name:BackArticleController
 *@author:Trz
 *@date:2021/3/27 18:29
 *
 */
@Controller
@RequestMapping(value = "/admin/article")
public class BackArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 后台文章列表显示
     * @param pageIndex
     * @param pageSize
     * @param status
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("")
    public String index(@RequestParam(required = false,defaultValue = "1")Integer pageIndex,
                        @RequestParam(required = false,defaultValue = "10")Integer pageSize,
                        @RequestParam(required = false)String status, Model model,
                        HttpSession session){
        //1、创建一个HashMap 这个HashMap是用来存放文章状态的键值对的
        HashMap<String, Object> criteria = new HashMap<>(1);
        //2、如果这个状态为null model添加 所有状态文章的 键值对 地址
        if(status == null){
            model.addAttribute("pageUrlPrefix","/admin/article?pageIndex");
        }else{
            //  如果不为空 则显示 model添加 status的 键值对 地址
            criteria.put("status",status);
            model.addAttribute("pageUrlPrefix","/admin/article?status="+ status + "&pageIndex");
        }
        //3、从session中拿userId 如果用户不是管理者 则添加userId 【管理者要看到所有人的东西 所有不用添加userId查询】
        User user = (User) session.getAttribute("user");
        if(!user.getUserRole().equals(UserRole.ADMIN.getValue())){
            criteria.put("userId",user.getUserId());
        }
        //4、查询出来的是用分页插件接受的
        PageInfo<Article> articlePageInfo = articleService.pageArticle(pageIndex,pageSize,criteria);
        //5、将分页查询出的结果放入model中
        model.addAttribute("pageInfo",articlePageInfo);
        return "Admin/Article/index";
    }

    /**
     * 后台添加文章页面显示
     * @return
     */
    @RequestMapping("/insert")
    public ModelAndView insertArticleView(){
        //1、创建模型视图
        ModelAndView modelAndView = new ModelAndView();
        //2、拿到所有标签和分类 添加返回视图名称Admin/Article/insert
        List<Tag> tagList = tagService.listTag();
        if(tagList != null){
            modelAndView.addObject("tagList",tagList);
        }
        List<Category> categoryList = categoryService.listCategory();
        if(categoryList != null){
            modelAndView.addObject("categoryList",categoryList);
        }
        modelAndView.setViewName("Admin/Article/insert");
        return modelAndView;
    }

    /**
     * 后台添加文章提交操作
     * @param session
     * @param articleParam  前端传过来的文章对象
     * @return
     */
    public ModelAndView insertArticleSubmit(HttpSession session, ArticleParam articleParam){
        ModelAndView modelAndView = new ModelAndView();
        //1、创建一个文章对象
        Article article = new Article();
        //2、拿到用户id
        User user = (User) session.getAttribute("user");
        if(user != null){
            article.setArticleUserId(user.getUserId());
        }
        //3、文章摘要
        int summaryLength = 150;
        String summaryText = HtmlUtil.cleanHtmlTag(articleParam.getArticleContent());
        if (summaryText.length() > summaryLength) {
            String summary = summaryText.substring(0, summaryLength);
            article.setArticleSummary(summary);
        } else {
            article.setArticleSummary(summaryText);
        }
        article.setArticleTitle(articleParam.getArticleTitle());
        article.setArticleContent(articleParam.getArticleContent());
        article.setArticleStatus(articleParam.getArticleStatus());
        //4、填充文章的分类
        List<Category> categoryList = new ArrayList<>();
        if(articleParam.getArticleParentCategoryId() != null){
            //类型转换
            categoryList.add(new Category(articleParam.getArticleParentCategoryId()));
        }
        if(articleParam.getArticleChildCategoryId() != null){
            categoryList.add(new Category(articleParam.getArticleChildCategoryId()));
        }
        article.setCategoryList(categoryList);
        //5、填充文章的标签
        List<Tag> tagList = new ArrayList<>();
        if(articleParam.getArticleTagIds() != null){
            for (Integer articleTagId : articleParam.getArticleTagIds()) {
                tagList.add(new Tag(articleTagId));
            }
        }
        article.setTagList(tagList);
        articleService.insertArticle(article);
        modelAndView.setViewName("redirect:/admin/article");
        //6、返回模型视图
        return modelAndView;
    }

    /**
     * 删除文章
     * @param id 传过来的文章id
     * @param session
     */
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST)
    public void deleteArticle(@PathVariable("id") Integer id, HttpSession session){
        //1、根据id拿到文章
        Article articleByStatusAndId = articleService.getArticleByStatusAndId(null, id);
        //2、如果文章为空直接返回
        if(articleByStatusAndId == null){
            return;
        }
        User user = (User) session.getAttribute("user");
        //     文章 不为空 用户访问其他用户的数据 如果用户不是管理员则直接返回
        if(!Objects.equals(user.getUserId(),articleByStatusAndId.getArticleUserId()) && !Objects.equals(user.getUserRole(),UserRole.ADMIN.getValue())){
            //                                  用户是管理员 则可以删除
            return;
        }
        articleService.deleteArticle(id);
    }

    /**
     * 编辑文章页面 显示
     * 先显示文章
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("/edit/{id}")
    public ModelAndView editArticleView(@PathVariable("id") Integer id,HttpSession session){
        Article articleByStatusAndId = articleService.getArticleByStatusAndId(null, id);
        ModelAndView modelAndView = new ModelAndView();
        if(articleByStatusAndId == null){
            //1、如果文章不存在 直接跳到404
            modelAndView.setViewName("redirect:/404");
            return modelAndView;
        }
        User user = (User) session.getAttribute("user");
        if(!Objects.equals(user.getUserId(),articleByStatusAndId.getArticleUserId()) && !Objects.equals(user.getUserRole(),UserRole.ADMIN.getValue())){
            //2、如果不是管理员访问其他数据 403
            modelAndView.setViewName("redirect:/403");
            return modelAndView;
        }
        //3、将文章 标签 分类 放入到模型视图中
        modelAndView.addObject("article",articleByStatusAndId);

        List<Tag> tagList = tagService.listTag();
        modelAndView.addObject("tagList",tagList);

        List<Category> categoryList = categoryService.listCategory();
        modelAndView.addObject("categoryList",categoryList);
        //4、添加视图名称 返回
        modelAndView.setViewName("Admin/Article/edit");
        return modelAndView;
    }

    /**
     * 编辑文章提交
     * @param articleParam
     * @param session
     * @return
     */
    @RequestMapping(value = "/edirSubmit",method = RequestMethod.POST)
    public ModelAndView editArticleSubmit(ArticleParam articleParam,HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        //1、如果文章存在 则报404
        Article articleByStatusAndId = articleService.getArticleByStatusAndId(null, articleParam.getArticleId());
        if(articleByStatusAndId == null){
            modelAndView.setViewName("redirect:/404");
            return modelAndView;
        }
        //2、如果不是管理员 访问其他资源 403
        User user = (User) session.getAttribute("user");
        if(!Objects.equals(user.getUserRole(),UserRole.ADMIN.getValue()) && !Objects.equals(user.getUserId(),articleByStatusAndId.getArticleUserId())){
            modelAndView.setViewName("redirect:/403");
            return modelAndView;
        }
        //3、将前端传过来的内容填充
        Article article = new Article();
        article.setArticleId(articleParam.getArticleId());
        article.setArticleTitle(articleParam.getArticleTitle());
        article.setArticleContent(articleParam.getArticleContent());
        article.setArticleStatus(articleParam.getArticleStatus());
        //4、文章摘要
        int summaryLength = 150;
        String summaryText = HtmlUtil.cleanHtmlTag(article.getArticleContent());
        if (summaryText.length() > summaryLength) {
            String summary = summaryText.substring(0, summaryLength);
            article.setArticleSummary(summary);
        } else {
            article.setArticleSummary(summaryText);
        }
        //5、填充分类
        List<Category> categoryList = new ArrayList<>();
        if(articleParam.getArticleParentCategoryId() != null){
            categoryList.add(new Category(articleParam.getArticleParentCategoryId()));
        }
        if(articleParam.getArticleChildCategoryId() != null){
            categoryList.add(new Category(articleParam.getArticleChildCategoryId()));
        }
        article.setCategoryList(categoryList);
        //6、填充标签
        List<Tag> tagList = new ArrayList<>();
        if(articleParam.getArticleTagIds() != null){
            for (Integer articleTagId : articleParam.getArticleTagIds()) {
                tagList.add(new Tag(articleTagId));
            }
        }
        article.setTagList(tagList);
        articleService.updateArticle(article);
        modelAndView.addObject("redirect:/admin/article");
        return modelAndView;
    }

    /**
     * 后台添加文章（简陋添加文章【保存草稿、重置】） 再提交
     * @param session
     * @param articleParam
     * @return
     */
    @RequestMapping(value = "/insertDraftSubmit",method = RequestMethod.POST)
    public ModelAndView insertDraftSubmit(HttpSession session,ArticleParam articleParam){
        ModelAndView modelAndView = new ModelAndView();
        Article article = new Article();
        //1、拿到用户id
        User user = (User) session.getAttribute("user");
        if (user != null) {
            //2、填充用户id
            article.setArticleUserId(user.getUserId());
        }
        article.setArticleTitle(articleParam.getArticleTitle());
        //3、将前端传过来的内容填充
        article.setArticleId(articleParam.getArticleId());
        article.setArticleTitle(articleParam.getArticleTitle());
        article.setArticleContent(articleParam.getArticleContent());
        article.setArticleStatus(articleParam.getArticleStatus());
        //4、文章摘要
        int summaryLength = 150;
        String summaryText = HtmlUtil.cleanHtmlTag(article.getArticleContent());
        if (summaryText.length() > summaryLength) {
            String summary = summaryText.substring(0, summaryLength);
            article.setArticleSummary(summary);
        } else {
            article.setArticleSummary(summaryText);
        }
        //5、填充分类
        List<Category> categoryList = new ArrayList<>();
        if(articleParam.getArticleParentCategoryId() != null){
            categoryList.add(new Category(articleParam.getArticleParentCategoryId()));
        }
        if(articleParam.getArticleChildCategoryId() != null){
            categoryList.add(new Category(articleParam.getArticleChildCategoryId()));
        }
        article.setCategoryList(categoryList);
        //6、填充标签
        List<Tag> tagList = new ArrayList<>();
        if(articleParam.getArticleTagIds() != null){
            for (Integer articleTagId : articleParam.getArticleTagIds()) {
                tagList.add(new Tag(articleTagId));
            }
        }
        article.setTagList(tagList);
        articleService.insertArticle(article);
        modelAndView.addObject("redirect:/admin");
        return modelAndView;
    }
}
