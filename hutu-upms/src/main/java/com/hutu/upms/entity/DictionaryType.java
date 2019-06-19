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
 * 字典类别表
 * </p>
 *
 * @author generator
 * @since 2019-06-05
 */
@Data
@Accessors(chain = true)
@TableName("t_common_dictionary_type")
public class DictionaryType implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	private String key;
	private String valueEn;
	private String valueCn;
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
	private String updateName;
	private Integer updateId;
	private LocalDateTime updateTime;
    /**
     * 逻辑删除标记(已删除：1，未删除：0)

     */
    @TableLogic
	private Boolean isDeleted;


}
