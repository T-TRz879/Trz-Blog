package com.trz.blog.controller.admin;

import com.trz.blog.entity.Tag;
import com.trz.blog.service.ArticleService;
import com.trz.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/***
 *@Package_name:com.trz.blog.controller.admin
 *@Class_name:BackTagController
 *@author:Trz
 *@date:2021/3/27 18:31
 *
 */
@Controller
@RequestMapping("admin/tag")
public class BackTagController {
    @Autowired
    private TagService tagService;
    @Autowired
    private ArticleService articleService;

    /**
     * 后台标签列表显示
     * @return
     */
    @RequestMapping("")
    public ModelAndView tagList(){
        ModelAndView modelAndView = new ModelAndView();
        List<Tag> tagList = tagService.listTag();
        modelAndView.addObject("tagList",tagList);
        modelAndView.setViewName("Admin/Tag/index");
        return modelAndView;
    }

    /**
     * 后台添加标签
     * @param tag
     * @return
     */
    @RequestMapping(value = "/insertSubmit",method = RequestMethod.POST)
    public String insertTag(Tag tag){
        tagService.insertTag(tag);
        return "redirect:/admin/page";
    }

    /**
     * 后台删除标签
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public String deleteTag(@PathVariable("id") Integer id){
        Tag tag = tagService.getTagById(id);
        if(tag == null){
            return "redirect:/404";
        }
        //只有标签没有绑定文章时 标签才能删除
        Integer count = articleService.countArticleByTagId(id);
        if (count == 0) {
            tagService.deleteTag(id);
        }
        return "redirect:/admin/page";
    }

    /**
     * 编辑文章显示页面
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editTag(@PathVariable("id") Integer id){
        ModelAndView modelAndView = new ModelAndView();

        Tag tag = tagService.getTagById(id);
        modelAndView.addObject("tag",tag);

        List<Tag> tagList = tagService.listTagWithCount();
        modelAndView.addObject("tagList",tagList);

        modelAndView.setViewName("Admin/Page/edit");
        return modelAndView;
    }

    /**
     * 编辑文章提交
     * @param tag
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public String editTagSubmit(Tag tag){
        tagService.updateTag(tag);
        return "redirect:/admin/tag";
    }
}
