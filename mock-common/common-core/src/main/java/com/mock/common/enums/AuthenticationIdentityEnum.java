package com.mock.common.enums;

import com.mock.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 认证身份标识枚举
 * @author zhao
 * @since 2022-08-25 14:34
 */
public enum  AuthenticationIdentityEnum implements IBaseEnum<String> {

    USERNAME("username", "用户名"),
    MOBILE("mobile", "手机号"),
    OPENID("openId", "开放式认证系统唯一身份标识");

    @Getter
    private String value;

    @Getter
    private String label;

    AuthenticationIdentityEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

}
