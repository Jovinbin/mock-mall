package com.mock.admin.netclass.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mock.admin.netclass.constants.SystemConstants;
import com.mock.admin.netclass.entity.User;
import com.mock.admin.netclass.entity.UserRole;
import com.mock.admin.netclass.mapper.UserMapper;
import com.mock.admin.netclass.pojo.dto.AuthUserDTO;
import com.mock.admin.netclass.pojo.form.UserForm;
import com.mock.admin.netclass.service.UserRoleService;
import com.mock.admin.netclass.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author zhao
 * @since 2022-06-13
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;

    /**
     * 根据用户名获取认证信息
     *
     * @param username
     * @return
     */
    @Override
    public AuthUserDTO getAuthInfoByUsername(String username) {
        return this.baseMapper.getAuthInfoByUsername(username);
    }

    /**
     * 新增用户
     *
     * @param userForm 用户表单对象
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUser(UserForm userForm) {
        User user = new User();
        BeanUtil.copyProperties(userForm, user);

        //对密码进行加密
        if (StringUtils.checkValNull(user.getPassword())){
            user.setPassword(passwordEncoder.encode(SystemConstants.DEFAULT_USER_PASSWORD));//如果密码为空  就初始化默认密码123456
        }else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        //存储用户信息
        boolean result = this.save(user);
        if (result){
            Long userId = user.getId();
            List<Long> roleIds = user.getRoleIds();
            List<UserRole> userRoles = Optional.ofNullable(roleIds).orElse(new ArrayList<>())
                    .stream().map(roleId -> new UserRole(userId, roleId))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(userRoles)){
                //存储用户拥有角色信息
                userRoleService.saveBatch(userRoles);
            }
        }
        return result;
    }
}
