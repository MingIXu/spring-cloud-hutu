package com.hutu.common.security.config;

import com.hutu.common.security.aspect.PermissionsAspect;
import com.hutu.common.security.handler.CustomRestNotFoundHandler;
import com.hutu.common.security.handler.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * something
 *
 * @author hutu
 * @date 2019/7/16 14:56
 */
@Import({PermissionsAspect.class})
@Configuration
public class PermissionHandlerConfig {
}
