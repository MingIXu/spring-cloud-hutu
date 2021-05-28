package com.hutu.cloud.sensitive.util;

import cn.hutool.core.util.StrUtil;
import com.hutu.cloud.core.constant.StringPool;

/**
 * 工具类
 *
 * @author hutu
 * @date 2021/4/7 4:16 下午
 */
public class SensitiveUtil {

	/**
	 * 【中文姓名】只显示第一个汉字，其他隐藏为2个星号，比如：李**
	 * @param text
	 * @return
	 */
	public static String chineseName(String text) {
		if (StrUtil.isBlank(text)) {
			return StringPool.EMPTY;
		}
		return StrUtil.hide(text, 1, text.length());
	}

	/**
	 * 【身份证号】显示最后四位，其他隐藏。共计18位或者15位，比如：*************1234
	 * @param text
	 * @return
	 */
	public static String idCardNum(String text) {
		if (StrUtil.isBlank(text)) {
			return StringPool.EMPTY;
		}
		return StrUtil.hide(text, 0, text.length() - 4);
	}

	/**
	 * 【固定电话 后四位，其他隐藏，比如1234
	 * @param num
	 * @return
	 */
	public static String fixedPhone(String num) {
		if (StrUtil.isBlank(num)) {
			return StringPool.EMPTY;
		}
		return StrUtil.hide(num, 0, num.length() - 4);
	}

	/**
	 * 【手机号码】前三位，后四位，其他隐藏，比如135******10
	 * @param num
	 * @return
	 */
	public static String mobilePhone(String num) {
		if (StrUtil.isBlank(num)) {
			return StringPool.EMPTY;
		}
		return StrUtil.hide(num, 3, num.length() - 4);
	}

	/**
	 * 【地址】只显示到地区，不显示详细地址，比如：北京市海淀区****
	 * @param address
	 * @param sensitiveSize 敏感信息长度
	 * @return
	 */
	public static String address(String address, int sensitiveSize) {
		if (StrUtil.isBlank(address)) {
			return StringPool.EMPTY;
		}
		return StrUtil.hide(address, 0, address.length() - sensitiveSize);
	}

	/**
	 * 【电子邮箱 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示，比如：d**@126.com>
	 * @param email
	 * @return
	 */
	public static String email(String email) {
		if (StrUtil.isBlank(email)) {
			return StringPool.EMPTY;
		}
		int index = email.indexOf(StringPool.AT);
		if (index <= 1) {
			return email;
		}
		else {
			return StrUtil.hide(email, 1, index);
		}
	}

	/**
	 * 【银行卡号】前六位，后四位，其他用星号隐藏每位1个星号，比如：6222600**********1234>
	 * @param cardNum
	 * @return
	 */
	public static String bankCard(String cardNum) {
		if (StrUtil.isBlank(cardNum)) {
			return StringPool.EMPTY;
		}
		return StrUtil.hide(cardNum, 6, cardNum.length() - 4);
	}

	/**
	 * 【密码】密码的全部字符都用*代替，比如：******
	 * @param password
	 * @return
	 */
	public static String password(String password) {
		if (StrUtil.isBlank(password)) {
			return StringPool.EMPTY;
		}
		return StrUtil.hide(password, 0, password.length());
	}

	/**
	 * 【车牌号】前两位后一位，比如：苏M****5
	 * @param carNumber
	 * @return
	 */
	public static String carNumber(String carNumber) {
		if (StrUtil.isBlank(carNumber)) {
			return StringPool.EMPTY;
		}
		return StrUtil.hide(carNumber, 2, carNumber.length() - 1);

	}

	public static String hideHead(String text, int length) {
		if (StrUtil.isBlank(text)) {
			return StringPool.EMPTY;
		}
		if (text.length() <= length) {
			return text;
		}
		return StrUtil.hide(text, 0, length);
	}

	public static String hideTail(String text, int length) {
		if (StrUtil.isBlank(text)) {
			return StringPool.EMPTY;
		}
		if (text.length() <= length) {
			return text;
		}
		return StrUtil.hide(text, text.length() - length, text.length());
	}

	public static String hideHeadAndTail(String text, int headLength, int tailLength) {
		if (StrUtil.isBlank(text)) {
			return StringPool.EMPTY;
		}
		return hideTail(hideHead(text, headLength), tailLength);
	}

	public static String displayHead(String text, int length) {
		if (StrUtil.isBlank(text)) {
			return StringPool.EMPTY;
		}
		return StrUtil.hide(text, length, text.length());
	}

	public static String displayTail(String text, int length) {
		if (StrUtil.isBlank(text)) {
			return StringPool.EMPTY;
		}
		return StrUtil.hide(text, 0, text.length() - length);
	}

	public static String displayHeadAndTail(String text, int headLength, int tailLength) {
		if (StrUtil.isBlank(text)) {
			return StringPool.EMPTY;
		}
		return StrUtil.hide(text, headLength, text.length() - tailLength);
	}

	/*
	 * public static void main(String[] args) {
	 *
	 * System.out.println(hideHead("杭M88793", 3)); System.out.println(hideTail("杭M88793",
	 * 3)); System.out.println(hideHeadAndTail("杭M88793", 2, 2));
	 * System.out.println(displayHead("杭M88793", 3));
	 * System.out.println(displayTail("杭M88793", 3));
	 * System.out.println(displayHeadAndTail("杭M88793", 3, 3)); }
	 */

}
