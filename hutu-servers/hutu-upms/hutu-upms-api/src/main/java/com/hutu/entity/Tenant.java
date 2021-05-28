
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
 * 组织实体类
 *
 * @author ehealth-generator
 * @since 2020-06-19
 */
@Data
@Accessors(chain = true)
@TableName("upms_tenant")
@ApiModel(value = "Tenant对象", description = "组织")
public class Tenant implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
  @ApiModelProperty(value = "主键")
  @TableId(value = "id")
  private Integer id;
    /**
     * 租户名称
     */
  @ApiModelProperty(value = "租户名称")
  private String name;
    /**
     * 租户编码
     */
  @ApiModelProperty(value = "租户编码")
  private String code;
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


}
