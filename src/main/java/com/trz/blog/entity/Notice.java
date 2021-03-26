package com.trz.blog.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package_name:com.trz.entity
 * @Class_name:Notice
 * @author:Trz
 * @date:2021/3/12 13:59
 */
@Data
public class Notice implements Serializable {

    private static final long serialVersionUID = -4901560494243593100L;
    /**
     * 提示信息ID
     */
    private Integer noticeId;

    /**
     * 提示信息标题
     */
    private String noticeTitle;

    /**
     * 提示信息内容
     */
    private String noticeContent;

    /**
     * 提示信息创造时间
     */
    private Date noticeCreateTime;

    /**
     * 提示信息更新时间
     */
    private Date noticeUpdateTime;

    /**
     * 提示信息状态
     */
    private Integer noticeStatus;

    /**
     * 提示信息热度
     */
    private Integer noticeOrder;
}