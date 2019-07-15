package com.hutu.common.security.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.hutu.common.security.utils.TokenUtils;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * 自定义填充公共 name 字段
 *
 * @author hutu
 * @date 2019/7/11 16:45
 */
public class InitMetaObjectHandler implements MetaObjectHandler {

    private final static String CREATE_TIME = "createTime";
    private final static String CREATE_ID = "createId";
    private final static String CREATE_NAME = "createName";

    private final static String UPDATE_TIME = "updateTime";
    private final static String UPDATE_ID = "updateId";
    private final static String UPDATE_NAME = "updateName";
    private final static String TENANT_ID = "tenantId";

    /**
     * 插入填充，字段为空自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = getFieldValByName(CREATE_TIME, metaObject);
        Object createId = getFieldValByName(CREATE_ID, metaObject);
        Object createName = getFieldValByName(CREATE_NAME, metaObject);

        Object updateTime = getFieldValByName(UPDATE_TIME, metaObject);
        Object updateId = getFieldValByName(UPDATE_ID, metaObject);
        Object updateName = getFieldValByName(UPDATE_NAME, metaObject);

        Object tenantId = getFieldValByName(TENANT_ID, metaObject);


        LocalDateTime date = LocalDateTime.now();
        Integer userId = TokenUtils.getUserId();
        String userName = TokenUtils.getUserName();
        Integer userTenantId = TokenUtils.getTenantId();

        if (createTime == null) {
            setFieldValByName(CREATE_TIME, date, metaObject);
        }
        if (createId == null) {
            setFieldValByName(CREATE_ID, userId, metaObject);
        }
        if (createName == null) {
            setFieldValByName(CREATE_NAME, userName, metaObject);
        }

        if (updateTime == null) {
            setFieldValByName(UPDATE_TIME, date, metaObject);
        }
        if (updateId == null) {
            setFieldValByName(UPDATE_ID, userId, metaObject);
        }
        if (updateName == null) {
            setFieldValByName(UPDATE_NAME, userName, metaObject);
        }
        if (tenantId == null) {
            setFieldValByName(TENANT_ID, userTenantId, metaObject);
        }
    }

    /**
     * 更新填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {

        Integer userId = TokenUtils.getUserId();
        String userName = TokenUtils.getUserName();

        setFieldValByName(UPDATE_TIME, LocalDateTime.now(), metaObject);
        setFieldValByName(UPDATE_ID, userId, metaObject);
        setFieldValByName(UPDATE_NAME, userName, metaObject);
    }
}
