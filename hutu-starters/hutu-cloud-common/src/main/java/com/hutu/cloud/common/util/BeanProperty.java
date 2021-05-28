package com.hutu.cloud.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Bean属性
 */
@Getter
@AllArgsConstructor
public class BeanProperty {
	private final String name;
	private final Class<?> type;
}
