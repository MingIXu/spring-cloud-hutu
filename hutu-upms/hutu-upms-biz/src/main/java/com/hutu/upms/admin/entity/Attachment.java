package com.hutu.upms.admin.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 公共附件表
 * </p>
 *
 * @author generator
 * @since 2019-06-05
 */
@Data
@Accessors(chain = true)
@TableName("t_common_attachment")
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	private String name;
	private String path;
	private Integer type;
	private String typeShow;
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
