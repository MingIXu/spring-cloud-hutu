package com.hutu.common.core.handler;

import com.hutu.common.core.entity.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 鉴权
 *
 * @author hutu
 * @date 2019/6/20 15:17
 */
@RestController
public class AuthHandler{

    @RequestMapping("auth")
    public R auth() {
        return R.ok();
    }


}
