package com.mock.admin.netclass.service.impl;

import com.mock.admin.netclass.entity.UserRole;
import com.mock.admin.netclass.mapper.UserRoleMapper;
import com.mock.admin.netclass.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author zhao
 * @since 2022-06-13
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
