package com.trz.blog.controller.admin;

import com.trz.blog.entity.Link;
import com.trz.blog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/***
 *@Package_name:com.trz.blog.controller.admin
 *@Class_name:BackLinkController
 *@author:Trz
 *@date:2021/3/27 18:30
 *
 */
@Controller
@RequestMapping("/admin/link")
public class BackLinkController {
    @Autowired
    private LinkService linkService;

    /**
     * 后台链接列表显示
     * @return
     */
    @RequestMapping("")
    public ModelAndView linkList(){
        ModelAndView modelAndView = new ModelAndView();
        List<Link> linkList = linkService.listLink(null);
        modelAndView.addObject("linkList",linkList);
        modelAndView.setViewName("Admin/link/index");
        return modelAndView;
    }

    /**
     * 后台添加链接页面显示
     * @return
     */
    @RequestMapping("/isnert")
    public ModelAndView insertLinkView(){
        ModelAndView modelAndView = new ModelAndView();

        List<Link> linkList = linkService.listLink(null);
        modelAndView.addObject("linkList",linkList);

        modelAndView.setViewName("Admin/link/insert");
        return modelAndView;
    }

    /**
     * 后台添加链接提交
     * @param link
     * @return
     */
    @RequestMapping(value = "/insertSubmit",method = RequestMethod.POST)
    public String insertLinkSubmit(Link link){
        //1、链接创建日期
        link.setLinkCreateTime(new Date());
        //2、链接更新日期
        link.setLinkUpdateTime(new Date());
        //3、链接的状态
        link.setLinkStatus(1);
        //4、添加链接
        linkService.insertLink(link);
        return "redirect:/admin/link/insert";
    }

    /**
     * 后台删除链接
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public String deleteLin(@PathVariable("id") Integer id){
        Link linkById = linkService.getLinkById(id);
        //数据库中没有此链接 跳到404
        if(linkById == null){
            return "redirect:/404";
        }
        linkService.deleteLink(id);
        return "redirect:/admin/link";
    }

    /**
     * 后台编辑链接页面显示
     * @param id
     * @return &&&8******
     */
    @RequestMapping("/edit/{id}")
    public ModelAndView editLinkView(@PathVariable("id")Integer id){
        ModelAndView modelAndView = new ModelAndView();
        //1、链接页面左边的编辑区域 需要的内容
        Link link = linkService.getLinkById(id);
        if(link == null){
            modelAndView.setViewName("redirect:/404");
            return modelAndView;
        }
        modelAndView.addObject("linkCustom",link);
        //2、链接页面右边的显示全部页面
        List<Link> linkList = linkService.listLink(null);
        modelAndView.addObject("linkList",linkList);

        modelAndView.setViewName("Admin/Link/edit");
        return modelAndView;
    }

    /**
     * 后台编辑链接提交
     * @param link
     * @return
     */
    @RequestMapping(value = "/editSubmit",method = RequestMethod.POST)
    public String editLinkSubmit(Link link){
        link.setLinkUpdateTime(new Date());
        linkService.updateLink(link);
        return "redirect:/admin/link";
    }

}
