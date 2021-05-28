package com.hutu.cloud.sensitive.format;

import com.hutu.cloud.sensitive.annotation.Desensitization;

/**
 * 脱敏
 *
 * @author hutu
 * @date 2021/4/7 3:13 下午
 */
public interface DesensitizeFormatter {

	/**
	 * 脱敏处理
	 * @param text 敏感信息
	 * @param desensitization 脱敏注解
	 * @return 脱敏后的信息
	 */
	String format(String text, Desensitization desensitization);

}