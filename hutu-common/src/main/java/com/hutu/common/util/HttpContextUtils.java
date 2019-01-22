package com.hutu.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * http请求上下文工具类
 *
 * @author hutu
 * @date 2018/3/28 11:20
 */
public class HttpContextUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpContextUtils.class);
    private static final String TOKEN = "token";
    private static final String EXPIRE = "expire";

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取请求的token
     */
    public static String getRequestToken() {
        return getRequestParameter(TOKEN);
    }

    /**
     * 获取请求的expire
     */
    public static String getRequestExpire() {
        return getRequestParameter(EXPIRE);
    }

    /**
     * 从请求头或体里获取请求中指定参数
     */
    public static String getRequestParameter(String param) {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        //从header中获取
        String value = httpServletRequest.getHeader(param);
        //如果header中不存在
        if (StringUtils.isBlank(value)) {
            value = httpServletRequest.getParameter(param);
            if (StringUtils.isBlank(value)) {
                logger.error("从Request获取参数:" + param + " 失败");
                value = null;
            }
        }
        return value;
    }

    /**
     * 获取httpSession
     * @return HttpSession
     */
    public static HttpSession getHttpSession() {
        return getHttpServletRequest().getSession();
    }
    /**
     * 获取httpSession中参数
     * @return 参数value
     */
    public static String getSessionParameter(String param) {
        return (String) getHttpSession().getAttribute(param);
    }
    /**
     * 输入httpSession中参数
     */
    public static void setSessionParameter(String key, String value) {
        getHttpSession().setAttribute(key, value);
    }

    /**
     * 通过request获取ip
     *
     * @param request 请求
     * @return IP Address
     */
    public static String getIpAddrByRequest(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取本机地址若有外网返回外网ip
     *
     * @return 本机IPSocketException
     * @throws SocketException
     */
    public static String getRealIp() throws SocketException {
        // 本地IP，如果没有配置外网IP则返回它
        String localip = null;
        // 外网IP
        String netip = null;

        Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip;
        // 是否找到外网IP
        boolean finded = false;
        while (netInterfaces.hasMoreElements() && !finded) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                // 外网IP
                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")) {
                    netip = ip.getHostAddress();
                    finded = true;
                    break;
                }
                // 内网IP
                else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")) {
                    localip = ip.getHostAddress();
                }
            }
        }

        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }
}
