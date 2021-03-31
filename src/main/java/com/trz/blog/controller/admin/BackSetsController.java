package com.trz.blog.controller.admin;

import com.trz.blog.entity.Set;
import com.trz.blog.service.SetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/***
 *@Package_name:com.trz.blog.controller.admin
 *@Class_name:BackSetsController
 *@author:Trz
 *@date:2021/3/27 18:31
 *
 */
@Controller
@RequestMapping("/admin/sets")
public class BackSetsController {
    @Autowired
    private SetsService setsService;

    /**
     * 基本信息页面显示
     * @return
     */
    @RequestMapping("")
    public ModelAndView setList(){
        ModelAndView modelAndView = new ModelAndView();
        Set sets = setsService.getSets();
        modelAndView.addObject("sets",sets);
        modelAndView.setViewName("Admin/Sets/index");
        return modelAndView;
    }

    /**
     * 编辑基本资料 显示
     * @return
     */
    @RequestMapping(value = "/edit")
    public ModelAndView editSetView(){
        ModelAndView modelAndView = new ModelAndView();
        Set sets = setsService.getSets();
        modelAndView.addObject("sets",sets);
        modelAndView.setViewName("Admin/Sets/edit");
        return modelAndView;
    }

    /**
     * 编辑基本资料提交
     * @param set
     * @return
     */
    @RequestMapping(value = "/editSubmit",method = RequestMethod.POST)
    public String editSetSubmit(Set set){
        Set sets = setsService.getSets();
        //如果基本资料不存在 则插入
        if(sets.getSetId() == null){
            setsService.insertSets(set);
        }else{
            //如果基本资料存在 则更新
            setsService.updateSets(set);
        }
        return "redirect:/admin/sets";
    }
}
