package com.mock.admin.netclass.constants.enums;

import lombok.Getter;

/**
 * @author zhao
 * @since 2022-06-13 16:07
 */
public enum PasswordEncoderTypeEnum {
    BCRYPT("{bcrypt}","BCRYPT加密"),
    NOOP("{noop}","无加密明文");

    @Getter
    private String prefix;

    PasswordEncoderTypeEnum(String prefix, String desc){
        this.prefix=prefix;
    }
}
