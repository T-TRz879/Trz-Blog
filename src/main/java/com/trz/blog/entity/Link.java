package com.trz.blog.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name:com.trz.entity
 * @Class_name:Link
 * @author:Trz
 * @date:2021/3/12 13:59
 */
@Data
public class Link implements Serializable {

    private static final long serialVersionUID = -259829372268790508L;
    /**
     * 链接ID
     */
    private Integer linkId;
    /**
     * 链接地址
     */
    private String linkUrl;
    /**
     * 链接名称
     */
    private String linkName;
    /**
     * 链接图标
     */
    private String linkImage;
    /**
     * 链接描述
     */
    private String linkDescription;
    /**
     * 链接自己的昵称
     */
    private String linkOwnerNickname;
    /**
     * 链接自己的联系
     */
    private String linkOwnerContact;
    /**
     * 链接更新时间
     */
    private Date linkUpdateTime;
    /**
     * 链接设置时间
     */
    private Date linkCreateTime;
    /**
     * 链接热度
     */
    private Integer linkOrder;
    /**
     * 链接状态
     */
    private Integer linkStatus;

}