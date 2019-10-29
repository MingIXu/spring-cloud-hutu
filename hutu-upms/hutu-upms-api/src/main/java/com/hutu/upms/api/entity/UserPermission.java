package com.hutu.upms.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户权限关联表
 * </p>
 *
 * @author generator
 * @since 2019-06-05
 */
@Data
@Accessors(chain = true)
@TableName("t_upms_user_permission")
public class UserPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 用户编号
     */
	private Integer userId;
    /**
     * 权限编号
     */
	private Integer permissionId;
    /**
     * 权限类型(-1:减权限,1:增权限)
     */
	private Boolean type;
    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer createId;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
