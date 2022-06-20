package com.hutu.cloud.core.enums;

import java.io.Serializable;

/**
 * 返回码顶级接口
 *
 * @author hutu
 * @date 2021/3/31 2:11 下午
 */
public interface IResultCode extends Serializable {

    /**
     * 状态码
     * @return code
     */
    int getCode();

    /**
     * 提示信息
     * @return msg
     */
    String getMsg();

}
