package com.hutu.common.core.handler;

import com.hutu.common.core.entity.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义springboot的404处理
 *
 * @author hutu
 * @date 2018/10/11 下午2:04
 */
@RestController
public class AuthHandler{

    @RequestMapping("auth")
    public R handleError() {
        return R.ok();
    }


}
