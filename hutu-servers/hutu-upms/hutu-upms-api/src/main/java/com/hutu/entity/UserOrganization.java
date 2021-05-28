
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
 * 用户组织关联表实体类
 *
 * @author ehealth-generator
 * @since 2020-06-19
 */
@Data
@Accessors(chain = true)
@TableName("upms_user_organization")
@ApiModel(value = "UserOrganization对象", description = "用户组织关联表")
public class UserOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
  @ApiModelProperty(value = "编号")
  @TableId(value = "id")
  private Integer id;
    /**
     * 用户编号
     */
  @ApiModelProperty(value = "用户编号")
  @TableField("userId")
  private Integer userId;
    /**
     * 组织编号
     */
  @ApiModelProperty(value = "组织编号")
  @TableField("organizationId")
  private Integer organizationId;
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
