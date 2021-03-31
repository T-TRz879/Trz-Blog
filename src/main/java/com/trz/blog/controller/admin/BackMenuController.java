package com.trz.blog.controller.admin;

import com.trz.blog.entity.Menu;
import com.trz.blog.enums.MenuLevel;
import com.trz.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/***
 *@Package_name:com.trz.blog.controller.admin
 *@Class_name:BackMenuController
 *@author:Trz
 *@date:2021/3/27 18:30
 *
 */
@Controller
@RequestMapping("/admin/menu")
public class BackMenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 后台菜单列表显示
     * @return
     */
    @RequestMapping("")
    public ModelAndView menuList(){
        ModelAndView modelAndView = new ModelAndView();
        List<Menu> menuList = menuService.listMenu();
        modelAndView.addObject("menuList",menuList);
        modelAndView.setViewName("Admin/menu/index");
        return modelAndView;
    }

    /**
     * 后台菜单内容提交
     * @param menu
     * @return
     */
    @RequestMapping(value = "/insertSubmit",method = RequestMethod.POST)
    public String insertMenuSubmit(Menu menu){
        if(menu.getMenuOrder() == null){
            menu.setMenuLevel(MenuLevel.TOP_MENU.getValue());
        }
        menuService.updateMenu(menu);
        return "redirect:/admin/menu";
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public String deleteMenu(@PathVariable("id") Integer id){
        Menu menu = menuService.getMenuById(id);
        if(menu == null){
            return "redirect:/404";
        }
        menuService.deleteMenu(id);
        return "redirect:/admin/menu";
    }

    /**
     * 编辑菜单页面显示
     * @param id
     * @return
     */
    @RequestMapping("/edit/{id}")
    public ModelAndView editMenuView(@PathVariable("id") Integer id){
        ModelAndView modelAndView = new ModelAndView();
        //1、点击编辑的那个菜单
        Menu menu = menuService.getMenuById(id);
        modelAndView.addObject("menu",menu);
        //2、右边的所有菜单
        List<Menu> menuList = menuService.listMenu();
        modelAndView.addObject("menuList",menuList);
        modelAndView.setViewName("Admin/Menu/edit");
        return modelAndView;
    }

    /**
     * 编辑菜单页面提交
     * @param menu
     * @return
     */
    @RequestMapping("/editSubmit")
    public String editMenuSubmit(Menu menu){
        menuService.updateMenu(menu);
        return "redirect:/admin/menu";
    }
}
