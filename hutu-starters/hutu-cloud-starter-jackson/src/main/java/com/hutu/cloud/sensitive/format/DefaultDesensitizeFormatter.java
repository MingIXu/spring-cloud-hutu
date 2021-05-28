package com.hutu.cloud.sensitive.format;

import cn.hutool.core.util.StrUtil;
import com.hutu.cloud.sensitive.annotation.Desensitization;
import com.hutu.cloud.sensitive.enums.RuleTypeEnum;
import com.hutu.cloud.sensitive.properties.DesensitizeProperties;
import com.hutu.cloud.sensitive.properties.RuleProperty;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import static com.hutu.cloud.sensitive.util.SensitiveUtil.*;

/**
 * 通用脱敏格式处理器
 *
 * @author hutu
 * @date 2021/4/7 3:18 下午
 */
@RequiredArgsConstructor
public class DefaultDesensitizeFormatter implements DesensitizeFormatter {

	private final DesensitizeProperties desensitizeProperties;

	@Override
	public String format(String text, Desensitization annotation) {
		if (!desensitizeProperties.isEnable()) {
			return text;
		}
		if (StrUtil.isNotEmpty(text)) {
			Map<String, RuleProperty> rules = desensitizeProperties.getRules();
			switch (annotation.type()) {
			case CHINESE_NAME:
			case MOBILE_PHONE:
			case ID_CARD:
			case HEALTH_CARD:
				return doCustom(text, rules.getOrDefault(annotation.type().toString(), RuleProperty.NONE));
			case FIXED_PHONE:
				return fixedPhone(text);
			case ADDRESS:
				return address(text, 8);
			case EMAIL:
				return email(text);
			case BANK_CARD:
				return bankCard(text);
			case PASSWORD:
				return password(text);
			case CAR_NUMBER:
				return carNumber(text);
			case CUSTOM:
				return doCustom(text, annotation);
			default:
				break;
			}
		}
		return text;
	}

	private String doCustom(String text, Desensitization annotation) {
		RuleTypeEnum rule = annotation.rule();
		int length = annotation.length();
		int headLength = annotation.headLength();
		int tailLength = annotation.tailLength();
		return caseProcess(text, rule, length, headLength, tailLength);
	}

	private String doCustom(String text, RuleProperty ruleProperty) {
		RuleTypeEnum rule = ruleProperty.getRuleType();
		int length = ruleProperty.getLength();
		int headLength = ruleProperty.getHeadLength();
		int tailLength = ruleProperty.getTailLength();
		return caseProcess(text, rule, length, headLength, tailLength);
	}

	private String caseProcess(String text, RuleTypeEnum rule, int length, int headLength, int tailLength) {
		switch (rule) {
		case HIDE_HEAD:
			return hideHead(text, length);
		case HIDE_TAIL:
			return hideTail(text, length);
		case HIDE_HEAD_AND_TAIL:
			return hideHeadAndTail(text, headLength, tailLength);
		case DISPLAY_HEAD:
			return displayHead(text, length);
		case DISPLAY_TAIL:
			return displayTail(text, length);
		case DISPLAY_HEAD_AND_TAIL:
			return displayHeadAndTail(text, headLength, tailLength);
		default:
			break;
		}
		return text;
	}

}