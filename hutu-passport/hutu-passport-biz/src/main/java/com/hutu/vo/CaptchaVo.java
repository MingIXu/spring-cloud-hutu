package com.hutu.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 验证码，此处都交由前端，由前端判断是否提交表单
 *
 * @author hutu
 * @date 2020/6/5 3:00 下午
 */
@Getter
@Setter
@Accessors(chain = true)
public class CaptchaVo {
    /**
     * 验证码明文
     */
    String key;
    /**
     * 验证码base64编码
     */
    String img;
}
