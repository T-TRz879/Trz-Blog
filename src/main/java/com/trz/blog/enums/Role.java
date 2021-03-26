package com.trz.blog.enums;

/**
 * @Package_name:com.trz.enums
 * @Class_name:Role
 * @author:Trz
 * @date:2021/3/12 13:59
 */
public enum Role {

    OWNER(1, "博主"),
    VISITOR(0, "其他用户");

    private Integer value;

    private String message;

    Role(Integer value, String message) {
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
