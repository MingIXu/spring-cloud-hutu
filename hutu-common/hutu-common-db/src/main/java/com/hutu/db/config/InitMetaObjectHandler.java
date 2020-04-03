package com.hutu.db.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.hutu.common.utils.token.TokenUtil;
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

        LocalDateTime date = LocalDateTime.now();
        Integer userId = TokenUtil.getUserId();
        String userName = TokenUtil.getUserName();
        String userTenantId = TokenUtil.getTenantId();

        setFieldValByName(CREATE_TIME, date, metaObject);
        setFieldValByName(CREATE_ID, userId, metaObject);
        setFieldValByName(CREATE_NAME, userName, metaObject);
        setFieldValByName(UPDATE_TIME, date, metaObject);
        setFieldValByName(UPDATE_ID, userId, metaObject);
        setFieldValByName(UPDATE_NAME, userName, metaObject);
        setFieldValByName(TENANT_ID, userTenantId, metaObject);
    }

    /**
     * 更新填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {

        Integer userId = TokenUtil.getUserId();
        String userName = TokenUtil.getUserName();

        setFieldValByName(UPDATE_TIME, LocalDateTime.now(), metaObject);
        setFieldValByName(UPDATE_ID, userId, metaObject);
        setFieldValByName(UPDATE_NAME, userName, metaObject);
    }
}
