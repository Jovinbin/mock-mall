package com.mock.admin.netclass.controller;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mock.admin.netclass.entity.Role;
import com.mock.admin.netclass.service.RoleService;
import com.mock.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author zhao
 * @since 2022-06-13
 */
@Api(tags = "角色接口")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "新增角色")
    @PostMapping("/save")
    public Result saveRole(@RequestBody Role role){
        int count = roleService.count(new LambdaQueryWrapper<Role>()
                .eq(Role::getCode, role.getCode())
                .or()
                .eq(Role::getName, role.getCode())
        );
        Assert.isTrue(count == 0, "角色名称或角色编码重复，请检查！");
        boolean result = roleService.save(role);
        // TODO: 2022/6/13 后续需要处理缓存问题……
        return Result.judge(result);
    }

}

