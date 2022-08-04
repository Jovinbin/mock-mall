package com.mock.admin.netclass.controller;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mock.admin.netclass.entity.User;
import com.mock.admin.netclass.pojo.dto.AuthUserDTO;
import com.mock.admin.netclass.pojo.form.UserForm;
import com.mock.admin.netclass.service.UserService;
import com.mock.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author zhao
 * @since 2022-06-13
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户登录")
    @GetMapping("/login")
    public Result login(){

        return Result.failed("先登陆一下吧");
    }

    @ApiOperation(value = "用户测试")
    @GetMapping("/login1")
    public Result login1(){

        return Result.failed("先登陆一下吧1");
    }

    @ApiOperation(value = "添加用户")
    @PostMapping("/save")
    public Result saveUser(UserForm userForm){
        int count = userService.count(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, userForm.getUsername())
        );
        Assert.isTrue(count == 0, "用户名称已存在，请检查！");
        return Result.judge(userService.saveUser(userForm));
    }

    @ApiOperation(value = "根据用户名获取认证信息",notes = "提供用于用户登录认证信息")
    @GetMapping("/username/{username}")
    public Result<AuthUserDTO> getAuthInfoByUsername(
            @ApiParam("用户名") @PathVariable String username) {
        AuthUserDTO user = userService.getAuthInfoByUsername(username);
        return Result.success(user);
    }
}

