package com.hutu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作类型枚举
 *
 * @author hutu
 */
@Getter
@AllArgsConstructor
public enum OperateEnum {
    /**
     * 新增
     */
    ADD("新增"),
    /**
     * 删除
     */
    DELETE("删除"),
    /**
     * 更新
     */
    UPDATE("更新"),
    /**
     * 查询
     */
    SELECT("查询"),
    /**
     * 其他
     */
    OTHER("其他");

    public String value;
}
