package com.trz.blog.controller.home;

import com.trz.blog.entity.Article;
import com.trz.blog.entity.Link;
import com.trz.blog.enums.LinkStatus;
import com.trz.blog.service.ArticleService;
import com.trz.blog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/***
 *@Package_name:com.trz.blog.controller.home
 *@Class_name:LinkController
 *@author:Trz
 *@date:2021/3/27 18:32
 *
 */
@Controller
public class LinkController {
    @Autowired
    private LinkService linkService;

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/applyLink")
    public String applyLinkView(Model model){
        //侧边栏显示
        //获得热评文章
        List<Article> mostCommentArticleList = articleService.listArticleByCommentCount(8);
        model.addAttribute("mostCommentArticleList", mostCommentArticleList);
        return "Home/Page/applyLink";
    }

    /**
     * 插入链接
     * @param link
     */
    @RequestMapping(value = "/applyLinkSubmit",method = {RequestMethod.POST}, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public void applyLinkSubmit(Link link){
        link.setLinkStatus(LinkStatus.HIDDEN.getValue());
        link.setLinkCreateTime(new Date());
        link.setLinkUpdateTime(new Date());
        linkService.insertLink(link);
    }
}
