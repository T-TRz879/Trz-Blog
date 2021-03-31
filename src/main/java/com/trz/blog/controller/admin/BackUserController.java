package com.trz.blog.controller.admin;

import com.trz.blog.entity.User;
import com.trz.blog.enums.UserRole;
import com.trz.blog.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 *@Package_name:com.trz.blog.controller.admin
 *@Class_name:BackUserController
 *@author:Trz
 *@date:2021/3/27 18:31
 *
 */
@Controller
@RequestMapping("/admin/user")
public class BackUserController {
    @Autowired
    private UserService userService;

    /**
     * 后台用户列表显示
     * @return
     */
    @RequestMapping(value = "")
    public ModelAndView userList(){
        ModelAndView modelAndView = new ModelAndView();
        List<User> userList = userService.listUser();
        modelAndView.addObject("userList",userList);
        modelAndView.setViewName("Admin/User/index");
        return modelAndView;
    }

    /**
     * 后台插入用户显示
     * @return
     */
    @RequestMapping(value = "/insert")
    public ModelAndView insertUserView(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Admin/User/insert");
        return modelAndView;
    }

    /**
     * 核实用户的用户名
     * @param request
     * @return
     */
    @RequestMapping(value = "/checkUserName",method = RequestMethod.POST,produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String checkUserName(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        String username = request.getParameter("username");
        User user = userService.getUserByName(username);
        int id = Integer.valueOf(request.getParameter("id"));
        //用户名已存在,但不是当前用户(编辑用户的时候，不提示)
        if (user != null) {
            if (user.getUserId() != id) {
                map.put("code", 1);
                map.put("msg", "用户名已存在！");
            }
        } else {
            map.put("code", 0);
            map.put("msg", "");
        }
        String result = new JSONObject(map).toString();
        return result;
    }

    /**
     * 核实用户的邮箱
     * @param request
     * @return
     */
    @RequestMapping(value = "/checkUserEmail",method = RequestMethod.POST,produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String checkUserEmail(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        String email = request.getParameter("email");
        User user = userService.getUserByEmail(email);
        int id = Integer.valueOf(request.getParameter("id"));
        //用户名已存在,但不是当前用户(编辑用户的时候，不提示)
        if (user != null) {
            if (user.getUserId() != id) {
                map.put("code", 1);
                map.put("msg", "电子邮箱已存在！");
            }
        } else {
            map.put("code", 0);
            map.put("msg", "");
        }
        String result = new JSONObject(map).toString();
        return result;
    }

    /**
     * 后台插入用户提交
     * @param user
     * @return
     */
    @RequestMapping(value = "/insertSubmit", method = RequestMethod.POST)
    public String insertUserSubmit(User user){
        User user1 = userService.getUserByName(user.getUserName());
        User user2 = userService.getUserByEmail(user.getUserEmail());
        if(user1 == null && user2 == null){
            user.setUserRegisterTime(new Date());
            user.setUserStatus(1);
            user.setUserRole(UserRole.USER.getValue());
            userService.insertUser(user);
        }
        return "redirect:/admin/user";
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id){
        userService.deleteUser(id);
        return "redirect:/admin/user";
    }

    /**
     * 编辑用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editUser(@PathVariable("id") Integer id){
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.getUserById(id);
        modelAndView.addObject("user",user);
        modelAndView.setViewName("Admin/User/edit");
        return modelAndView;
    }

    /**
     * 编辑用户信息提交
     * @param user
     * @return
     */
    @RequestMapping(value = "/editSubmit",method = RequestMethod.POST)
    public String editSubmit(User user){
        userService.updateUser(user);
        return "redirect:/admin/user";
    }
}
