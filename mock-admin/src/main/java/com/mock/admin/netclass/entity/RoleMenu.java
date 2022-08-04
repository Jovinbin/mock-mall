package com.mock.admin.netclass.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 角色和菜单关联表
 * </p>
 *
 * @author zhao
 * @since 2022-06-13
 */
@Data
@TableName("sys_role_menu")
@ApiModel(value="RoleMenu对象", description="角色和菜单关联表")
public class RoleMenu{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "菜单ID")
    private Long menuId;


}
