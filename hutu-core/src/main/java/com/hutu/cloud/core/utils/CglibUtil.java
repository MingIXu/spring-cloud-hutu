package com.hutu.cloud.core.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.hutu.cloud.core.entity.DynamicBean;
import lombok.Data;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

public class CglibUtil {

	public static Object getTarget(Object dest, Map<String, Object> addProperties) {
		// get property map
		PropertyDescriptor[] descriptors = BeanUtil.getPropertyDescriptors(dest.getClass());
		Map<String, Class> propertyMap = MapUtil.newHashMap();
		for (PropertyDescriptor d : descriptors) {
			if (!"class".equalsIgnoreCase(d.getName())) {
				propertyMap.put(d.getName(), d.getPropertyType());
			}
		}
		// add extra properties
		addProperties.forEach((k, v) -> propertyMap.put(k, v.getClass()));
		// new dynamic bean
		DynamicBean dynamicBean = new DynamicBean(dest.getClass(), propertyMap);
		// add old value
		propertyMap.forEach((k, v) -> {
			try {
				// filter extra properties
				if (!addProperties.containsKey(k)) {
					dynamicBean.setValue(k, BeanUtil.getProperty(dest, k));
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		});
		// add extra value
		addProperties.forEach((k, v) -> {
			try {
				dynamicBean.setValue(k, v);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		});
		Object target = dynamicBean.getTarget();
		return target;

	}

	public static void main(String[] args) {

		DicEntity entity = new DicEntity();
		entity.setName("eee");
		entity.setAge("222");

		Map<String, Object> addProperties = new HashMap() {
			{
				put("desc", "动态属性值");
			}
		};
		DicEntity target = (DicEntity) CglibUtil.getTarget(entity, addProperties);

		System.out.println(JsonUtil.toJsonString(target));
	}

	@Data
	public static class DicEntity {

		String name;

		String age;

	}

}