package com.trz.blog.controller.home;

import com.trz.blog.entity.Article;
import com.trz.blog.entity.Notice;
import com.trz.blog.service.ArticleService;
import com.trz.blog.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/***
 *@Package_name:com.trz.blog.controller.home
 *@Class_name:NoticeController
 *@author:Trz
 *@date:2021/3/27 18:33
 *
 */
@Controller
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/notice/{noticeId}")
    public String NoticeDetailView(@PathVariable("noticeId") Integer noticeId,
                                   Model model) {
        //公告内容和信息显示
        Notice notice  = noticeService.getNoticeById(noticeId);
        model.addAttribute("notice",notice);

        //侧边栏显示
        //获得热评文章
        List<Article> mostCommentArticleList = articleService.listArticleByCommentCount(8);
        model.addAttribute("mostCommentArticleList", mostCommentArticleList);
        return "Home/Page/noticeDetail";
    }
}
