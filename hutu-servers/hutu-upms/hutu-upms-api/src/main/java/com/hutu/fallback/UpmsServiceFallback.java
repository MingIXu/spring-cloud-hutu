package com.hutu.fallback;

import com.hutu.cloud.core.R;
import com.hutu.cloud.core.enums.CommonStatusEnum;
import com.hutu.entity.User;
import com.hutu.service.UpmsService;
import org.springframework.stereotype.Component;

/**
 * sentinel 降级处理
 *
 * @author hutu
 * @date 2020/5/17 6:51 下午
 */
@Component
public class UpmsServiceFallback implements UpmsService {


	@Override
	public R<User> user(Long id) {
		return R.failed(CommonStatusEnum.SERVICE_FALLBACK);
	}

}
