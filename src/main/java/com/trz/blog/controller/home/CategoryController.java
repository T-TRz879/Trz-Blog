package com.trz.blog.controller.home;

import com.github.pagehelper.PageInfo;
import com.trz.blog.entity.Article;
import com.trz.blog.entity.Category;
import com.trz.blog.entity.Tag;
import com.trz.blog.enums.ArticleStatus;
import com.trz.blog.service.ArticleService;
import com.trz.blog.service.CategoryService;
import com.trz.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

/***
 *@Package_name:com.trz.blog.controller.home
 *@Class_name:CategoryController
 *@author:Trz
 *@date:2021/3/27 18:32
 *
 */
@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TagService tagService;

    /**
     * 根据文章分类查询文章
     * @param cateId
     * @param pageIndex
     * @param pageSize
     * @param model
     * @return
     */
    @RequestMapping(value = "/category/{cateId}")
    public String getArticleListByCategory(@PathVariable("cateId") Integer cateId,
                                           @RequestParam(required = false,defaultValue = "1")Integer pageIndex,
                                           @RequestParam(required = false,defaultValue = "10")Integer pageSize,
                                           Model model){
        Category category = categoryService.getCategoryById(cateId);
        //如果分类不存在就到404
        if(category == null) {
            return "redirect:/404";
        }
        model.addAttribute("category",category);

        //文章列表
        HashMap<String, Object> criteria = new HashMap<>(2);
        criteria.put("categoryId",cateId);
        criteria.put("status", ArticleStatus.PUBLISH.getValue());
        PageInfo<Article> articlePageInfo = articleService.pageArticle(pageIndex, pageSize, criteria);
        model.addAttribute("pageInfo",articlePageInfo);

        //侧边栏
        //标签列表显示
        List<Tag> tagList = tagService.listTag();
        model.addAttribute("tagList",tagList);
        //获得随机文章
        List<Article> randomArticleList = articleService.listRandomArticle(5);
        model.addAttribute("randomArticleList",randomArticleList);
        //获得热评文章
        List<Article> mostCommentArticleList = articleService.listArticleByCommentCount(5);
        model.addAttribute("mostCommentArticleList", mostCommentArticleList);
        model.addAttribute("pageUrlPrefix", "/category/"+pageIndex+"?pageIndex");
        return "Home/Page/articleListByCategory";
    }

}
