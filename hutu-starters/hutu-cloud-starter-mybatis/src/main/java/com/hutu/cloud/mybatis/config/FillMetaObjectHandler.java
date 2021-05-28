package com.hutu.cloud.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.hutu.cloud.mybatis.properties.MybatisPlusAutoFillProperties;
import com.hutu.cloud.token.util.TokenUtil;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * 自定义填充公共字段
 *
 * @author hutu
 * @date 2020/7/10 10:44 上午
 */
public class FillMetaObjectHandler implements MetaObjectHandler {

	private MybatisPlusAutoFillProperties autoFillProperties;

	public FillMetaObjectHandler(MybatisPlusAutoFillProperties autoFillProperties) {
		this.autoFillProperties = autoFillProperties;
	}

	/**
	 * 是否开启了插入填充
	 */
	@Override
	public boolean openInsertFill() {
		return autoFillProperties.getEnableInsertFill();
	}

	/**
	 * 是否开启了更新填充
	 */
	@Override
	public boolean openUpdateFill() {
		return autoFillProperties.getEnableUpdateFill();
	}

	/**
	 * 插入填充，字段为空自动填充
	 */
	@Override
	public void insertFill(MetaObject metaObject) {

		LocalDateTime date = LocalDateTime.now();
		String userId = TokenUtil.getUserId();
		String userName = TokenUtil.getUserName();
		String userTenantId = TokenUtil.getTenantId();

		setFieldValByName(autoFillProperties.getCreateTimeField(), date, metaObject);
		setFieldValByName(autoFillProperties.getCreateUserIdField(), userId, metaObject);
		setFieldValByName(autoFillProperties.getCreateUserNameField(), userName, metaObject);
		setFieldValByName(autoFillProperties.getUpdateTimeField(), date, metaObject);
		setFieldValByName(autoFillProperties.getUpdateUserIdField(), userId, metaObject);
		setFieldValByName(autoFillProperties.getUpdateUserNameField(), userName, metaObject);
		setFieldValByName(autoFillProperties.getTenantId(), userTenantId, metaObject);
	}

	/**
	 * 更新填充
	 */
	@Override
	public void updateFill(MetaObject metaObject) {

		String userId = TokenUtil.getUserId();
		String userName = TokenUtil.getUserName();

		setFieldValByName(autoFillProperties.getUpdateTimeField(), LocalDateTime.now(), metaObject);
		setFieldValByName(autoFillProperties.getUpdateUserIdField(), userId, metaObject);
		setFieldValByName(autoFillProperties.getUpdateUserNameField(), userName, metaObject);
	}

}