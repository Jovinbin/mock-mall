package com.mock.admin.netclass.service;

import com.mock.admin.netclass.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mock.admin.netclass.pojo.dto.AuthUserDTO;
import com.mock.admin.netclass.pojo.form.UserForm;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author zhao
 * @since 2022-06-13
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名获取认证信息
     *
     * @param username
     * @return
     */
    AuthUserDTO getAuthInfoByUsername(String username);

    /**
     * 新增用户
     *
     * @param userForm 用户表单对象
     * @return
     */
    boolean saveUser(UserForm userForm);
}
