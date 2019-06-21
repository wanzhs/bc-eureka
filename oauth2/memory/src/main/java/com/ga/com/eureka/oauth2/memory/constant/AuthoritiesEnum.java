package com.ga.com.eureka.oauth2.memory.constant;

/**
 * @author:wanzhongsu
 * @description: 权限常量
 * @date:2019/6/18 17:04
 */
public enum AuthoritiesEnum {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    ANONYMOUS("ROLE_ANONYMOUS");

    private String role;

    AuthoritiesEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
