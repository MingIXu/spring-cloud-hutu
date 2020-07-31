package com.hutu.core.constant;


import com.hutu.core.exception.GlobalException;

/**
 * ssdev公共常量
 *
 * @author hutu
 * @date 2020/7/7 2:58 下午
 */
public class SsdevConstant {

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
    /**
     * header中token的key
     */
    public final static String TOKEN_KEy = "token";

    /**
     * header中sing的key
     */
    public final static String SIGN_KEY = "sign";

    /**
     * header中uid的key
     */
    public final static String UID = "uid";
    /**
     * header中sign中的前缀
     */
    public final static String SIGN_PREFIX = "ngariyun_";


    public final static String DEV_URL = "https://ssltest.ngarihealth.com/hutu-base-devtest/*.jsonRequest";
    public final static String TEST_URL = "https://ssltest.ngarihealth.com/hutu-base-test/*.jsonRequest";
    public final static String PROD_URL = "https://baseapi.ngarihealth.com/hutu-base/*.jsonRequest";

    /**
     * 获取ssdev网关地址
     * @param env
     * @return
     */
    public final static String getSsdevUrl(String env){
        if (ProfilesConstant.DEV.equals(env)){
            return DEV_URL;
        }else if (ProfilesConstant.TEST.equals(env)){
            return TEST_URL;
        }else if (ProfilesConstant.PROD.equals(env)){
            return PROD_URL;
        }
        throw new GlobalException("未知的环境");
    }

}
