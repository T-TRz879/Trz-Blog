package com.trz.blog.controller.admin;

import com.trz.blog.entity.Category;
import com.trz.blog.service.ArticleService;
import com.trz.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/***
 *@Package_name:com.trz.blog.controller.admin
 *@Class_name:BackCategoryController
 *@author:Trz
 *@date:2021/3/27 18:30
 *
 */
@Controller
@RequestMapping("admin/category")
public class BackCategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleService articleService;

    /**
     * 后台分类列表显示
     * @return
     */
    @RequestMapping("")
    public ModelAndView categoryList(){
        //1、创建模型视图
        ModelAndView modelAndView = new ModelAndView();
        //2、拿到所有分类
        List<Category> categoryList = categoryService.listCategory();
        modelAndView.addObject("categoryList",categoryList);
        //、设置视图名称
        modelAndView.setViewName("Admin/Category/index");
        return modelAndView;
    }

    /**
     * 后台添加文章提交
     * @param category
     * @return
     */
    @RequestMapping(value = "/insertSubmit",method = RequestMethod.POST)
    public String insertCategorySubmit(Category category){
        categoryService.insertCategory(category);
        return "redirect:/admin/category";
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public String deleteCategory(@PathVariable("id") Integer id){
        //1、只能删除文章没有关联的分类
        int count = articleService.countArticleByCategoryId(id);
        if(count == 0){
            categoryService.deleteCategory(id);
        }
        return "redirect:/admin/category";
    }

    /**
     * 编辑分类页面显示
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editCategory(@PathVariable("id") Integer id){
        //1、创建模型视图
        ModelAndView modelAndView = new ModelAndView();
        //2、找到要编辑的分类id
        Category category = categoryService.getCategoryById(id);
        modelAndView.addObject("category",category);
        //3、拿到所有的分类
        List<Category> categoryList = categoryService.listCategoryWithCount();
        modelAndView.addObject("categoryList",categoryList);
        modelAndView.setViewName("Admin/Category/edit");
        return modelAndView;
    }

    /**
     * 编辑分类提交
     * @param category
     * @return
     */
    @RequestMapping(value = "/editSubmit",method = RequestMethod.POST)
    public String editCategorySubmit(Category category){
        categoryService.updateCategory(category);
        return "redirect:/admin/category";
    }
}
