
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
 * 角色实体类
 *
 * @author ehealth-generator
 * @since 2020-06-19
 */
@Data
@Accessors(chain = true)
@TableName("upms_role")
@ApiModel(value = "Role对象", description = "角色")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
  @ApiModelProperty(value = "编号")
  @TableId(value = "id")
  private Integer id;
    /**
     * 角色名称
     */
  @ApiModelProperty(value = "角色名称")
  private String name;
    /**
     * 角色标识
     */
  @ApiModelProperty(value = "角色标识")
  private String code;
    /**
     * 角色描述
     */
  @ApiModelProperty(value = "角色描述")
  private String description;
    /**
     * 是否启用（1 启用，0 禁用）
     */
  @ApiModelProperty(value = "是否启用（1 启用，0 禁用）")
  private Boolean status;
    /**
     * 状态值
     */
  @ApiModelProperty(value = "状态值")
  @TableField("statusShow")
  private String statusShow;
    /**
     * 排序
     */
  @ApiModelProperty(value = "排序")
  private Integer orders;
    /**
     * 创建人名称
     */
  @ApiModelProperty(value = "创建人名称")
  @TableField("createName")
  private String createName;
    /**
     * 创建人ID
     */
  @ApiModelProperty(value = "创建人ID")
  @TableField("createId")
  private Integer createId;
    /**
     * 创建时间
     */
  @ApiModelProperty(value = "创建时间")
  @TableField("createTime")
  private LocalDateTime createTime;
    /**
     * 更新人名称
     */
  @ApiModelProperty(value = "更新人名称")
  @TableField("updateName")
  private String updateName;
    /**
     * 更新人ID
     */
  @ApiModelProperty(value = "更新人ID")
  @TableField("updateId")
  private Integer updateId;
    /**
     * 更新时间
     */
  @ApiModelProperty(value = "更新时间")
  @TableField("updateTime")
  private LocalDateTime updateTime;
    /**
     * 逻辑删除标记(已删除：1，未删除：0)
     */
  @ApiModelProperty(value = "逻辑删除标记(已删除：1，未删除：0)")
  @TableField("isDeleted")
  private Boolean isDeleted;
    /**
     * 组织id
     */
  @ApiModelProperty(value = "组织id")
  @TableField("orgId")
  private Integer orgId;


}
