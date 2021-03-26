package com.trz.blog.enums;

/**
 * @Package_name:com.trz.enums
 * @Class_name:UserRole
 * @author:Trz
 * @date:2021/3/12 13:59
 */
public enum UserRole {

    ADMIN("admin", "管理员"),

    USER("user", "用户");

    private String value;

    private String message;

    UserRole(String value, String message) {
        this.value = value;
        this.message = message;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
