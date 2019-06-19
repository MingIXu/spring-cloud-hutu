package com.hutu.upms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author generator
 * @since 2019-06-05
 */
@Data
@Accessors(chain = true)
@TableName("t_upms_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 用户名
     */
	private String name;
    /**
     * 密码
     */
	private String pass;
    /**
     * 昵称姓名
     */
	private String nick;
    /**
     * 头像
     */
	private String avatar;
    /**
     * 邮箱
     */
	private String email;
    /**
     * 手机号
     */
	private String phone;
    /**
     * 性别(男：0，女：1)
     */
	private Integer sex;
    /**
     * 用户状态（1=正常，0=禁用）
     */
	private Integer status;
    /**
     * 排序
     */
	private Integer orders;
    /**
     * 生日
     */
	private LocalDate birthday;
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
