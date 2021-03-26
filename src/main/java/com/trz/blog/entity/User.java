package com.trz.blog.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Package_name:com.trz.entity
 * @Class_name:User
 * @author:Trz
 * @date:2021/3/12 13:59
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -7767743599616479358L;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户密码
     */
    private String userPass;
    /**
     * 用户昵称
     */
    private String userNickname;
    /**
     * 用户邮箱
     */
    private String userEmail;
    /**
     * 用户地址
     */
    private String userUrl;
    /**
     * 用户头像
     */
    private String userAvatar;
    /**
     * 用户最后一次登陆ip
     */
    private String userLastLoginIp;
    /**
     * 用户注册时间
     */
    private Date userRegisterTime;
    /**
     * 用户最后登陆时间
     */
    private Date userLastLoginTime;
    /**
     * 用户状态
     */
    private Integer userStatus;

    /**
     * 用户角色：admin/user
     */
    private String userRole;

    /**
     * 文章数量（不是数据库字段）
     */
    private Integer articleCount;

}
