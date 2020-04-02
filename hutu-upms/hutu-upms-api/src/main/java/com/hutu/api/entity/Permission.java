package com.hutu.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 权限
 * </p>
 *
 * @author generator
 * @since 2019-06-05
 */
@Data
@Accessors(chain = true)
@TableName("t_upms_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 所属系统
     */
	private Integer systemId;
    /**
     * 所属上级
     */
	private Integer pid;
    /**
     * 权限标识
     */
	private String code;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 类型(1:目录,2:菜单,3:按钮)
     */
	private Integer type;
    private String typeShow;
    /**
     * 权限值
     */
	private String permissionValue;
    /**
     * 路径
     */
	private String uri;
    /**
     * 图标
     */
	private String icon;
    /**
     * 状态(0:禁止,1:正常)
     */
	private Integer status;
    private String statusShow;
    /**
     * 排序
     */
	private Integer orders;
    /**
     * 创建人名称
     */
    @TableField(fill = FieldFill.INSERT)
    private String createName;
    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer createId;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 更新人名称
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateName;
    /**
     * 更新人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateId;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    /**
     * 逻辑删除标记(已删除：1，未删除：0)
     */
    @TableLogic
	private Integer isDeleted;


}
