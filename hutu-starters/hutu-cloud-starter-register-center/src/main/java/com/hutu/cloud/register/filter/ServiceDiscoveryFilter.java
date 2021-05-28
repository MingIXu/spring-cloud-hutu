package com.hutu.cloud.register.filter;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 过滤服务接口
 *
 * @author hutu
 * @date 2021/4/21 10:55 上午
 */
public interface ServiceDiscoveryFilter {

	/**
	 * 过滤不符合条件的服务实例
	 * @param instances 服务实例列表
	 * @param key 过滤键
	 * @param value 过滤值
	 * @return 符合条件的实例列表
	 */
	default List<Instance> filter(List<Instance> instances, String key, String value) {
		ArrayList<Instance> list = new ArrayList<>();
		for (Instance instance : instances) {
			Map<String, String> metadata = instance.getMetadata();
			if (metadata.get(key).equals(value)) {
				list.add(instance);
			}
		}
		return list.size() == 0 ? instances : list;
	}

}
