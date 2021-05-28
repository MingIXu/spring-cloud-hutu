package com.hutu.cloud.sensitive.properties;

import com.hutu.cloud.sensitive.enums.RuleTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RuleProperty {

	public static final RuleProperty NONE = new RuleProperty(RuleTypeEnum.NONE, 0, 0, 0);

	private RuleTypeEnum ruleType;

	private int length = 0;

	private int headLength = 0;

	private int tailLength = 0;

}
