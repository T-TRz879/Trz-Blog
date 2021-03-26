package com.trz.blog.enums;

/**
 * @Package_name:com.trz.enums
 * @Class_name:PageStatus
 * @author:Trz
 * @date:2021/3/12 13:59
 */
public enum PageStatus {

    NORMAL(1, "显示"),
    HIDDEN(0, "隐藏");

    private Integer value;

    private String message;

    PageStatus(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
