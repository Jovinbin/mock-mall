package com.mock.admin.netclass.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 角色权限表
 * </p>
 *
 * @author zhao
 * @since 2022-06-13
 */
@Data
@TableName("sys_role_permission")
@ApiModel(value="RolePermission对象", description="角色权限表")
public class RolePermission {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "资源id")
    private Long permissionId;


}
