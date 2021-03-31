package com.trz.blog.controller.admin;

import com.trz.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
