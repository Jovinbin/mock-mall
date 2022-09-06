package com.mock.auth.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zhao
 * @since 2022-06-13 10:33
 * @description: OAuth2认证用户信息传输层对象
 */
@Data
public class AuthUserDTO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户状态(1:正常;0:禁用)
     */
    private Integer status;

    /**
     * 用户角色编码集合 ["ROOT","ADMIN"]
     */
    private List<String> roles;

}
