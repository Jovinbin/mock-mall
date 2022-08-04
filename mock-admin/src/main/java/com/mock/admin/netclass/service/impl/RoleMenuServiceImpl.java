package com.mock.admin.netclass.service.impl;

import com.mock.admin.netclass.entity.RoleMenu;
import com.mock.admin.netclass.mapper.RoleMenuMapper;
import com.mock.admin.netclass.service.RoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>
 *
 * @author zhao
 * @since 2022-06-13
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}
