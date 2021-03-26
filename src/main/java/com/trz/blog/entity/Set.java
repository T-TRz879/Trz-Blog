package com.trz.blog.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Package_name:com.trz.entity
 * @Class_name:Set
 * @author:Trz
 * @date:2021/3/12 13:59
 */
@Data
public class Set implements Serializable {

    private static final long serialVersionUID = 4979368316257138582L;
    /**
     * 设置ID
     */
    private Integer setId;

    /**
     * 设置站点标题
     */
    private String setSiteTitle;

    /**
     * 设置站点描述
     */
    private String setSiteDescription;

    /**
     * 设置meta描述
     */
    private String setMetaDescription;

    /**
     * 设置meta关键字
     */
    private String setMetaKeyword;

    /**
     * 设置关于站点的头像
     */
    private String setAboutsiteAvatar;

    /**
     * 设置关于站点的标题
     */
    private String setAboutsiteTitle;

    /**
     * 设置关于站点内容
     */
    private String setAboutsiteContent;

    /**
     * 设置关于站点的vx
     */
    private String setAboutsiteWechat;

    /**
     * 设置关于站点的qq
     */
    private String setAboutsiteQq;

    /**
     * 设置关于站点的github
     */
    private String setAboutsiteGithub;

    /**
     * 设置关于站点的微博
     */
    private String setAboutsiteWeibo;

    /**
     * 设置
     */
    private String setTongji;

    /**
     * 设置设置状态
     */
    private Integer setStatus;

}