package com.hutu.cloud.monitor.config;

import com.hutu.cloud.core.constant.HeaderConstant;
import com.hutu.cloud.core.utils.secure.SignUtil;
import de.codecentric.boot.admin.server.web.client.HttpHeadersProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class MonitorAutoConfiguration {

    @Bean
    public HttpHeadersProvider customHttpHeadersProvider() {
        return (instance) -> {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HeaderConstant.CLIENT_ID_KEY, HeaderConstant.MONITOR_CLIENT_ID);
            httpHeaders.add(HeaderConstant.CLIENT_SECRET_KEY, SignUtil.sign(HeaderConstant.MONITOR_CLIENT_ID, SignUtil.DEFAULT_PRIVATE_KEY));
            return httpHeaders;
        };
    }

}
