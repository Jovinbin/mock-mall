package com.mock.admin.netclass.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>
 * 用户和角色关联表
 * </p>
 *
 * @author zhao
 * @since 2022-06-13
 */
@Data
@AllArgsConstructor
@TableName("sys_user_role")
@ApiModel(value="UserRole对象", description="用户和角色关联表")
public class UserRole {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;


}
