package com.trz.blog.service.impl;

import com.trz.blog.dao.ArticleDao;
import com.trz.blog.dao.UserDao;
import com.trz.blog.entity.User;
import com.trz.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 *@Package_name:com.trz.blog.service.impl
 *@Class_name:UserServiceImpl
 *@author:Trz
 *@date:2021/3/25 21:21
 *
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ArticleDao articleDao;

    @Override
    public List<User> listUser() {
        List<User> list = null;
        try {
            list = userDao.listUser();
            for (User user : list) {
                Integer count = articleDao.countArticleByUser(user.getUserId());
                user.setArticleCount(count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取用户列表失败，cause:{}", e);
        }
        return null;
    }

    @Override
    public User getUserById(Integer id) {
        User user = null;
        try {
            user = userDao.getUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取用户列表失败，status:{}，cause:{}", id,e);
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        try{
            userDao.update(user);
        }catch (Exception e){
            e.printStackTrace();
            log.error("修改用户信息失败，status:{}，cause:{}",user,e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Integer id) {
        try{
            userDao.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除用户失败，status:{}，cause:{}",id,e);
        }
    }

    @Override
    public User insertUser(User user) {
        try{
            userDao.insert(user);
        }catch (Exception e){
            e.printStackTrace();
            log.error("插入用户失败，status:{}，cause:{}",user,e);
        }
        return user;
    }

    @Override
    public User getUserByNameOrEmail(String str) {
        User user = null;
        try{
            user = userDao.getUserByNameOrEmail(str);
            Integer count = articleDao.countArticleByUser(user.getUserId());
            user.setArticleCount(count);
        }catch (Exception e){
            e.printStackTrace();
            log.error("根据用户名和邮箱查询用户失败，status:{}，cause:{}",str,e);
        }
        return user;
    }

    @Override
    public User getUserByName(String name) {
        User user = null;
        try{
            user = userDao.getUserByName(name);
            Integer count = articleDao.countArticleByUser(user.getUserId());
            user.setArticleCount(count);
        }catch (Exception e){
            e.printStackTrace();
            log.error("根据用户名查询用户失败，status:{}，cause:{}",name,e);
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = null;
        try{
            user = userDao.getUserByEmail(email);
            Integer count = articleDao.countArticleByUser(user.getUserId());
            user.setArticleCount(count);
        }catch (Exception e){
            e.printStackTrace();
            log.error("根据邮箱查询用户失败，status:{}，cause:{}",email,e);
        }
        return user;
    }
}
