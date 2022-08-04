package com.mock.admin.netclass.service.impl;

import com.mock.admin.netclass.entity.Permission;
import com.mock.admin.netclass.mapper.PermissionMapper;
import com.mock.admin.netclass.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author zhao
 * @since 2022-06-13
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
