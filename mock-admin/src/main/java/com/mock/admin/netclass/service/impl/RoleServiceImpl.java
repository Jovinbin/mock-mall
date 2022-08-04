package com.mock.admin.netclass.service.impl;

import com.mock.admin.netclass.entity.Role;
import com.mock.admin.netclass.mapper.RoleMapper;
import com.mock.admin.netclass.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author zhao
 * @since 2022-06-13
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
