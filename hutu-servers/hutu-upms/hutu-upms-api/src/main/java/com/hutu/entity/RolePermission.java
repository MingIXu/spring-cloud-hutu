
package com.hutu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色权限关联表实体类
 *
 * @author ehealth-generator
 * @since 2020-06-19
 */
@Data
@Accessors(chain = true)
@TableName("upms_role_permission")
@ApiModel(value = "RolePermission对象", description = "角色权限关联表")
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
  @ApiModelProperty(value = "编号")
  @TableId(value = "id")
  private Integer id;
    /**
     * 角色编号
     */
  @ApiModelProperty(value = "角色编号")
  @TableField("roleId")
  private Integer roleId;
    /**
     * 权限编号
     */
  @ApiModelProperty(value = "权限编号")
  @TableField("permissionId")
  private Integer permissionId;
    /**
     * 创建人
     */
  @ApiModelProperty(value = "创建人")
  @TableField("createId")
  private Integer createId;
    /**
     * 创建时间
     */
  @ApiModelProperty(value = "创建时间")
  @TableField("createTime")
  private LocalDateTime createTime;


}
