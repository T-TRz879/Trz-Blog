package com.trz.blog.controller.admin;

import com.trz.blog.dto.JsonResult;
import com.trz.blog.entity.Article;
import com.trz.blog.entity.Comment;
import com.trz.blog.entity.User;
import com.trz.blog.enums.UserRole;
import com.trz.blog.service.ArticleService;
import com.trz.blog.service.CommentService;
import com.trz.blog.service.UserService;
import com.trz.blog.util.MyUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.trz.blog.util.MyUtils.getIpAddr;

/***
 *@Package_name:com.trz.blog.controller.admin
 *@Class_name:AdminController
 *@author:Trz
 *@date:2021/3/27 18:29
 *
 */
@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    /**
     * register.jsp 中的注册提交
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/registerSubmit", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult registerSubmit(HttpServletRequest request) {
        //1、拿到前端传过来的数据
        String userName = request.getParameter("username");
        String email = request.getParameter("email");
        String nickName = request.getParameter("nickname");
        String password = request.getParameter("password");
        //2、判断用户名是否已经存在
        User userByName = userService.getUserByName(userName);
        if(userByName == null){
            return new JsonResult().fail("用户名已经存在");
        }
        //3、判断邮箱是否已经存在
        User userByEmail = userService.getUserByEmail(email);
        if(userByEmail == null){
            return new JsonResult().fail("邮箱已经存在");
        }
        //4、添加用户
        User user = new User();
        user.setArticleCount(0);
        user.setUserAvatar("/img/avatar/avatar.png");
        user.setUserEmail(email);
        user.setUserName(userName);
        user.setUserPass(password);
        user.setUserNickname(nickName);
        user.setUserStatus(1);
        user.setUserRole(UserRole.USER.getValue());
        try{
            userService.insertUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult().fail("系统异常");
        }
        //5、看是否添加成功 返回状态
        return new JsonResult().ok("注册成功");
    }

    /**
     * login.jsp 中的登陆
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/loginVerify", method = RequestMethod.POST, produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String loginVerify(HttpServletRequest request, HttpServletResponse response) {
        //1、创建一个HashMap
        HashMap<String, Object> hashMap = new HashMap<>();
        //2、拿到前传传过来的数据
        String userNameOrEmail = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("rememberme");
        //3、根据用户输入的用户名/邮箱拿到用户的信息
        User user = userService.getUserByNameOrEmail(userNameOrEmail);
        if(user == null){
            //4、用户为空 则向hashMap中添加信息
            hashMap.put("code",0);
            hashMap.put("msg","用户名无效");
        }else if(!user.getUserPass().equals(password)){
            //   用户密码不匹配 则向hashMap中添加信息
            hashMap.put("code",0);
            hashMap.put("msg","密码错误");
        }else{
            //   用户匹配成功 将用户加入进 session中 如果用户选择记录登陆 则设置cookie 期限为两天
            request.getSession().setAttribute("user",user);
            if(remember != null){
                Cookie cookie1 = new Cookie("username", userNameOrEmail);
                Cookie cookie2 = new Cookie("password", password);
                cookie1.setMaxAge(60*60*24*2);
                cookie2.setMaxAge(60*60*24*2);
                response.addCookie(cookie1);
                response.addCookie(cookie2);
            }
            user.setUserLastLoginTime(new Date());
            user.setUserLastLoginIp(getIpAddr(request));
            userService.updateUser(user);
        }
        //5、最后返回json的String类型
        String result = new JSONObject(hashMap).toString();
        return result;
    }

    /**
     * header.jsp 中的 登陆
     *
     * @return
     */
    @RequestMapping(value = "/login")
    public String loginPage() {
        //跳转到后台登陆界面
        return "Admin/login";
    }

    /**
     * header.jsp 中的 注册
     * @return
     */
    @RequestMapping(value = "/register")
    public String register(){
        //跳转到后台注册界面
        return "Admin/register";
    }

    /**
     * index.jsp 中的 首页
     * @param model
     * @param session   当用户登陆的时候 根据session中保存的用户信息 可以使用
     * @return
     */
    @RequestMapping("/admin")
    public String index(Model model, HttpSession session) {
        //1、拿到session中存放的user
        User user = (User)session.getAttribute("user");
        Integer userId = null;
        if(user.getUserRole().equals(UserRole.USER.getValue())){
            //2、如果用户的身份是用户 则拿到他的id  如果是管理者就不需要拿到id
            userId = user.getUserId();
        }
        //3、拿到文章列表 listRecentArticle
        List<Article> articleList = articleService.listRecentArticle(userId, 6);
        model.addAttribute("articleList",articleList);
        //4、拿到评论列表 listRecentComment
        List<Comment> commentList = commentService.listRecentComment(userId, 6);
        model.addAttribute("commentList",commentList);
        //5、使用模型返回内容 model
        return "Admin/index";
    }

    /**
     * 退出登陆
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/admin/logout")
    public String logout(HttpSession httpSession){
        //1、设置session中的user为null
        httpSession.setAttribute("user",null);
        //2、设置session失效
        httpSession.invalidate();
        //重定向到登陆
        return "redirect:/login";
    }

    /**
     * 个人信息页面显示
     * @param session
     * @return
     */
    @RequestMapping(value = "/admin/profile")
    public ModelAndView userProfileView(HttpSession session){
        //1、创建一个模型视图
        ModelAndView modelAndView = new ModelAndView();
        //2、从session中拿到user
        User userSession = (User)session.getAttribute("user");
        //3、根据userSession中的id拿到user的全部信息
        User user = userService.getUserById(userSession.getUserId());
        //4、将user内容放到模型中
        modelAndView.addObject("user",user);
        //5、设置视图名称
        modelAndView.setViewName("Admin/User/profile");
        return modelAndView;
    }

    /**
     * 编辑个人信息页面显示
     * @param session
     * @return
     */
    @RequestMapping(value = "/admin/profile/edit")
    public ModelAndView editUserView(HttpSession session){
        //1、创建一个模型视图
        ModelAndView modelAndView = new ModelAndView();
        //2、从session中拿到user
        User userSession = (User)session.getAttribute("user");
        //3、根据userSession中的id拿到user的全部信息
        User user = userService.getUserById(userSession.getUserId());
        //4、将user内容放到模型中
        modelAndView.addObject("user",user);
        //5、设置视图名称
        modelAndView.setViewName("Admin/User/editProfile");
        return modelAndView;
    }

    /**
     * 用户保存自己的信息
     * @param user
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/admin/profile/save")
    public String saveProfile(User user,HttpSession httpSession){
        //1、拿到user
        User userSession = (User)httpSession.getAttribute("user");
        //2、给修改好的user信息 设置userId
        user.setUserId(userSession.getUserId());
        //3、更新这个user的信息
        userService.updateUser(user);
        return "redirect:/admin/profile";
    }
}
