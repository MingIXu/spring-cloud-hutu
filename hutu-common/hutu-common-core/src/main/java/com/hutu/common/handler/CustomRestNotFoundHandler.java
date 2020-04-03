package com.hutu.common.handler;

import com.hutu.common.entity.R;
import com.hutu.common.enums.ResultCode;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义springboot的404处理
 *
 * @author hutu
 * @date 2018/10/11 下午2:04
 */
@RestController
public class CustomRestNotFoundHandler implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public R handleError() {
        return R.error(ResultCode.NOT_FOUND);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}
