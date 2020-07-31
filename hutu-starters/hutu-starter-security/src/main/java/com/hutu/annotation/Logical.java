package com.hutu.annotation;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 权限验证逻辑
 *
 * @author hutu
 * @date 2018/6/10 15:09
 */
@Getter
@AllArgsConstructor
public enum Logical {
    /**
     * 权限与
     */
    AND(0),
    /**
     * 权限或
     */
    OR(1);
    public int value;
}
