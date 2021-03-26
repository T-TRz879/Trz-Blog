package com.trz.blog.service.impl;

import com.trz.blog.dao.MenuDao;
import com.trz.blog.entity.Menu;
import com.trz.blog.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 *@Package_name:com.trz.blog.service.impl
 *@Class_name:MenuServiceImpl
 *@author:Trz
 *@date:2021/3/25 21:20
 *
 */
@Service
@Slf4j
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Menu> listMenu() {
        List<Menu> list = null;
        try{
            list = menuDao.listMenu();
        }catch (Exception e){
            e.printStackTrace();
            log.error("获得菜单列表失败，cause:{}",e);
        }
        return list;
    }

    @Override
    public Menu insertMenu(Menu menu) {
        try{
            menuDao.insert(menu);
        }catch (Exception e){
            e.printStackTrace();
            log.error("插入菜单失败，status:{}，cause:{}",menu,e);
        }
        return menu;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(Integer id) {
        try{
            menuDao.deleteById(id);
        }catch (Exception e) {
            e.printStackTrace();
            log.error("删除菜单失败，status:{}，cause:{}", id, e);
        }
    }

    @Override
    public void updateMenu(Menu menu) {
        try{
            menuDao.update(menu);
        }catch (Exception e) {
            e.printStackTrace();
            log.error("删除菜单失败，status:{}，cause:{}", menu, e);
        }
    }

    @Override
    public Menu getMenuById(Integer id) {
        Menu menu = null;
        try{
            menu = menuDao.getMenuById(id);
        }catch (Exception e) {
            e.printStackTrace();
            log.error("根据id获得菜单项目失败，status:{}，cause:{}", id, e);
        }
        return menu;
    }
}
