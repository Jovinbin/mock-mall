package com.mock.admin.netclass.mapper;

import com.mock.admin.netclass.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mock.admin.netclass.pojo.dto.AuthUserDTO;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author zhao
 * @since 2022-06-13
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名获取认证信息
     *
     * @param username
     * @return
     */
    AuthUserDTO getAuthInfoByUsername(String username);
}
