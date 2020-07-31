package com.hutu.fallback;

import com.hutu.core.R;
import com.hutu.core.enums.ResultCode;
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
		return R.error(ResultCode.SERVICE_FALLBACK);
	}

}
