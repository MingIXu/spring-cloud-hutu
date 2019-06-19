package com.hutu.upms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
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
	private String createName;
    /**
     * 创建人ID
     */
	private Integer createId;
    /**
     * 创建时间
     */
	private LocalDateTime createTime;
    /**
     * 更新人名称
     */
	private String updateName;
    /**
     * 更新人ID
     */
	private Integer updateId;
    /**
     * 更新时间
     */
	private LocalDateTime updateTime;
    /**
     * 逻辑删除标记(已删除：1，未删除：0)
     */
    @TableLogic
	private Boolean isDeleted;


}
