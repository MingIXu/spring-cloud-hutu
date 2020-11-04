package com.hutu.http;

import cn.hutool.json.JSONObject;
import com.hutu.utils.WebUtil;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 *  RestTemplate 增强
 */
public class CustomRestTemplate extends RestTemplate {

    /**
     * header中服务id的key
     */
    public final static String SERVICE_ID = "X-Service-Id";
    /**
     * header中方法名的key
     */
    public final static String SERVICE_METHOD = "X-Service-Method";
    /**
     * header中token的key
     */
    public final static String ACCESS_TOKEN = "X-Access-Token";

    public final static String URL = "http://baseurl";

    public CustomRestTemplate() {
        super();
    }

    public CustomRestTemplate(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
    }

    public CustomRestTemplate(List<HttpMessageConverter<?>> messageConverters) {
        super(messageConverters);
    }

    public   <T> T exchange(String serviceId, String serviceMethod, String token, Object body, Class<T> clazz) {

        //设置头信息
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(ACCESS_TOKEN,token);
		headers.add(SERVICE_ID,serviceId);
		headers.add(SERVICE_METHOD,serviceMethod);
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(body, headers);
        ResponseEntity<JSONObject> exchange = exchange(URL, HttpMethod.POST, requestEntity, JSONObject.class);
        JSONObject result = exchange.getBody();
        return result.get("body", clazz);
    }

    public <T> T exchange(String serviceId, String serviceMethod, T obj,Class<T> clazz) {
        String token = WebUtil.getRequestParameter(ACCESS_TOKEN);
        return exchange(serviceId, serviceMethod, token, obj,clazz);
    }
}
