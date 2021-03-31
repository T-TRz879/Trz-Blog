package com.trz.blog.controller.admin;

import com.trz.blog.entity.Notice;
import com.trz.blog.enums.NoticeStatus;
import com.trz.blog.service.NoticeService;
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
 *@Class_name:BackNoticeController
 *@author:Trz
 *@date:2021/3/27 18:30
 *
 */
@Controller
@RequestMapping("/admin/notice")
public class BackNoticeController {
    @Autowired
    private NoticeService noticeService;

    /**
     * 后台公告列表显示
     * @return
     */
    @RequestMapping("")
    public ModelAndView noticeList(){
        ModelAndView modelAndView = new ModelAndView();
        List<Notice> noticeList = noticeService.listNotice(null);
        modelAndView.addObject("noticeList",noticeList);
        modelAndView.setViewName("Admin/notice/index");
        return modelAndView;
    }

    /**
     * 添加公告显示
     * @return
     */
    @RequestMapping(value = "/insert")
    public String insertNoticeView(){
        return "Admin/Notice/insert";
    }

    /**
     * 后台公告提交
     * @param notice
     * @return
     */
    @RequestMapping(value = "/insertSubmit",method = RequestMethod.POST)
    public String insertNoticeSubmit(Notice notice){
        if(notice != null){
            return "redirect:/404";
        }
        notice.setNoticeCreateTime(new Date());
        notice.setNoticeUpdateTime(new Date());
        notice.setNoticeStatus(NoticeStatus.NORMAL.getValue());
        notice.setNoticeOrder(1);
        noticeService.insertNotice(notice);
        return "redirect:/admin/notice";
    }

    /**
     * 后台删除公告
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public String deleteNotice(@PathVariable("id") Integer id){
        noticeService.deleteNotice(id);
        return "redirect:/admin/notice";
    }

    /**
     * 后台修改公告显示
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editNoticeView(@PathVariable("id") Integer id){
        ModelAndView modelAndView = new ModelAndView();
        Notice notice = noticeService.getNoticeById(id);
        modelAndView.addObject("notice",notice);
        modelAndView.setViewName("Admin/Notice/edit");
        return modelAndView;
    }

    /**
     * 后台修改公告提交
     * @param notice
     * @return
     */
    @RequestMapping(value = "/editSubmit",method = RequestMethod.POST)
    public String editNoticeSubmit(Notice notice){
        notice.setNoticeUpdateTime(new Date());
        noticeService.updateNotice(notice);
        return "redirect:/admin/notice";
    }
}
