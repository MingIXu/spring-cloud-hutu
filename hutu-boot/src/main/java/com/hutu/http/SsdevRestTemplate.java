package com.hutu.http;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.hutu.core.constant.SsdevConstant;
import com.hutu.core.exception.GlobalException;
import com.hutu.utils.SpringUtil;
import com.hutu.utils.WebUtil;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 *  RestTemplate 增强
 */
public class SsdevRestTemplate extends RestTemplate {

    public SsdevRestTemplate() {
        super();
    }

    public SsdevRestTemplate(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
    }

    public SsdevRestTemplate(List<HttpMessageConverter<?>> messageConverters) {
        super(messageConverters);
    }

    public   <T> T exchange(String serviceId, String serviceMethod, String token, Object body, Class<T> clazz) {

        //设置头信息
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(SsdevConstant.ACCESS_TOKEN,token);
		headers.add(SsdevConstant.SERVICE_ID,serviceId);
		headers.add(SsdevConstant.SERVICE_METHOD,serviceMethod);
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(body, headers);
        ResponseEntity<JSONObject> exchange = exchange(SsdevConstant.getSsdevUrl(SpringUtil.getActiveProfile()), HttpMethod.POST, requestEntity, JSONObject.class);
        JSONObject result = exchange.getBody();
        return result.get("body", clazz);
    }

    public <T> T exchange(String serviceId, String serviceMethod, T obj,Class<T> clazz) {
        String token = WebUtil.getRequestParameter(SsdevConstant.ACCESS_TOKEN);
        return exchange(serviceId, serviceMethod, token, obj,clazz);
    }

    private final static String USER_SERVICE_ID = "eh.remoteUserService";
    private final static String USER_METHOD_ID =  "getByKey";

    public void getUserInfo(){
        String token = "db6eef17-5ea6-4208-9b8a-4fa21bff690e";
//        String token = WebUtil.getRequestParameter(SsdevConstant.ACCESS_TOKEN);
        String uid = WebUtil.getRequestParameter(SsdevConstant.UID);
        // 无uid则直接抛异常
        if (StrUtil.isBlank(uid)){
            throw new GlobalException("请求头中无用户id信息");
        }
        String[] parameter = {uid};
        JSONObject exchange = exchange(USER_SERVICE_ID, USER_METHOD_ID,token, parameter, JSONObject.class);
        System.out.println(exchange);
    }
}
