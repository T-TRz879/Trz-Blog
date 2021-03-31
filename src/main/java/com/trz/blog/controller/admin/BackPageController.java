package com.trz.blog.controller.admin;

import com.trz.blog.entity.Page;
import com.trz.blog.enums.PageStatus;
import com.trz.blog.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/***
 *@Package_name:com.trz.blog.controller.admin
 *@Class_name:BackPageController
 *@author:Trz
 *@date:2021/3/27 18:31
 *
 */
@Controller
@RequestMapping("/admin/page")
public class BackPageController {
    @Autowired
    private PageService pageService;

    /**
     * 后台页面显示
     * @return
     */
    @RequestMapping("")
    public ModelAndView pageList(){
        ModelAndView modelAndView = new ModelAndView();
        List<Page> pageList = pageService.listPage(null);
        modelAndView.addObject("pageList",pageList);
        modelAndView.setViewName("Admin/Page/insert");
        return modelAndView;
    }

    /**
     * 后台添加页面 页面显示
     * @return
     */
    @RequestMapping(value = "/insert")
    public ModelAndView insertPageView(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Admin/Page/insert");
        return modelAndView;
    }

    /**
     * 后台页面添加
     * @param page
     * @return
     */
    @RequestMapping(value = "/insertSubmit",method = RequestMethod.POST)
    public String insertPageSubmit(Page page){
        //判断别名是否存在
        Page checkPage = pageService.getPageByKey(null, page.getPageKey());
        if (checkPage == null) {
            page.setPageStatus(PageStatus.NORMAL.getValue());
            page.setPageCreateTime(new Date());
            page.setPageUpdateTime(new Date());
            pageService.insertPage(page);
        }
        return "redirect:/admin/page";
    }

    /**
     * 删除页面
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public String deletePage(@PathVariable("id") Integer id){
        Page page = pageService.getPageById(id);
        if(page == null)    return "redirect:/404";
        pageService.deletePage(id);
        return "redirect:/admin/page";
    }

    /**
     * 编辑页面 页面显示
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editPageView(@PathVariable("id") Integer id){
        ModelAndView modelAndView = new ModelAndView();
        Page page = pageService.getPageById(id);
        modelAndView.addObject("page",page);
        modelAndView.setViewName("Admin/Page/index");
        return modelAndView;
    }

    /**
     * 编辑页面提交
     * @param page
     * @return
     */
    @RequestMapping(value = "/editSubmit",method = RequestMethod.POST)
    public String editPageSubmit(Page page){
        Page checkPage = pageService.getPageByKey(null, page.getPageKey());
        //判断别名是否存在且不是这篇文章
        if (Objects.equals(checkPage.getPageId(), page.getPageId())) {
            page.setPageUpdateTime(new Date());
            pageService.updatePage(page);
        }
        return "redirect:/admin/page";
    }

}
