package com.mock.admin.netclass.service.impl;

import com.mock.admin.netclass.entity.Menu;
import com.mock.admin.netclass.mapper.MenuMapper;
import com.mock.admin.netclass.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author zhao
 * @since 2022-06-13
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}
