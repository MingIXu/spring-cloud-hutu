
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
 * 系统用户表实体类
 *
 * @author ehealth-generator
 * @since 2020-06-19
 */
@Data
@Accessors(chain = true)
@TableName("t_upms_user")
@ApiModel(value = "User对象", description = "系统用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Integer id;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String account;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String pass;
    /**
     * 昵称姓名
     */
    @ApiModelProperty(value = "昵称姓名")
    private String nick;
    /**
     * 头像地址
     */
    @ApiModelProperty(value = "头像地址")
    private String avatar;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;
    /**
     * 性别(男：0，女：1,未知：2)
     */
    @ApiModelProperty(value = "性别(男：0，女：1,未知：2)")
    private Boolean sex;
    /**
     * 性别值
     */
    @ApiModelProperty(value = "性别值")
    @TableField("sexShow")
    private String sexShow;
    /**
     * 用户状态（1=正常，0=禁用）
     */
    @ApiModelProperty(value = "用户状态（1=正常，0=禁用）")
    private Boolean status;
    /**
     * 状态值
     */
    @ApiModelProperty(value = "状态值")
    @TableField("statusShow")
    private String statusShow;
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
    private Integer isDeleted;
    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    @TableField("orgId")
    private Integer orgId;


}
