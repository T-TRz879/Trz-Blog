package com.trz.blog.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Package_name:com.trz.entity
 * @Class_name:Menu
 * @author:Trz
 * @date:2021/3/12 13:59
 */
@Data
public class Menu implements Serializable {

    private static final long serialVersionUID = 489914127235951698L;
    /**
     * 菜单ID
     */
    private Integer menuId;
    /**
     * 菜单名
     */
    private String menuName;
    /**
     * 菜单地址
     */
    private String menuUrl;
    /**
     * 菜单等级
     */
    private Integer menuLevel;
    /**
     * 菜单图标
     */
    private String menuIcon;
    /**
     * 菜单热度
     */
    private Integer menuOrder;

}