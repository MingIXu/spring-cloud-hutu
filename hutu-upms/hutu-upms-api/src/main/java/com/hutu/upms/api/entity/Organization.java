package com.hutu.upms.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 组织
 * </p>
 *
 * @author generator
 * @since 2019-06-05
 */
@Data
@Accessors(chain = true)
@TableName("t_upms_organization")
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 所属上级
     */
	private Integer pId;
    /**
     * 组织名称
     */
    private String name;
    /**
     * 组织类别
     */
    private String type;
    private String typeShow;
    /**
     * 组织描述
     */
	private String description;
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
