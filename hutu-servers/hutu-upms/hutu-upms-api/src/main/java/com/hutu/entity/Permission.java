
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
 * 权限实体类
 *
 * @author ehealth-generator
 * @since 2020-06-19
 */
@Data
@Accessors(chain = true)
@TableName("upms_permission")
@ApiModel(value = "Permission对象", description = "权限")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    @TableId(value = "id")
    private Integer id;
    /**
     * 父级id(为0则为顶级)
     */
    @ApiModelProperty(value = "父级id(为0则为顶级)")
    @TableField("parentId")
    private Integer parentId;
    /**
     * 权限名称
     */
    @ApiModelProperty(value = "权限名称")
    private String name;
    /**
     * 类型(1:目录,2:菜单,3:按钮)
     */
    @ApiModelProperty(value = "类型(1:目录,2:菜单,3:按钮)")
    private Boolean type;
    /**
     * 类型值
     */
    @ApiModelProperty(value = "类型值")
    @TableField("typeShow")
    private String typeShow;
    /**
     * 权限值
     */
    @ApiModelProperty(value = "权限值")
    @TableField("permissionValue")
    private String permissionValue;
    /**
     * 前端路由路径
     */
    @ApiModelProperty(value = "前端路由路径")
    private String path;
    /**
     * 接口路径
     */
    @ApiModelProperty(value = "接口路径")
    private String uri;
    /**
     * 前端路由地址
     */
    @ApiModelProperty(value = "前端路由地址")
    private String component;
    /**
     * 重定向地址
     */
    @ApiModelProperty(value = "重定向地址")
    private String redirect;
    /**
     * 是否只显示根路由
     */
    @ApiModelProperty(value = "是否只显示根路由")
    @TableField("alwaysShow")
    private Boolean alwaysShow;
    /**
     * 是否不被缓存
     */
    @ApiModelProperty(value = "是否不被缓存")
    @TableField("noCache")
    private Boolean noCache;
    /**
     * 是否在面包屑显示
     */
    @ApiModelProperty(value = "是否在面包屑显示")
    private Boolean breadcrumb;
    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;
    /**
     * 状态(0:禁止,1:正常)
     */
    @ApiModelProperty(value = "状态(0:禁止,1:正常)")
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
    private Integer sort;
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
