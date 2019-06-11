package com.hutu.common.util;

import com.hutu.common.entity.CallerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.time.LocalDateTime;

/**
 * 实体类相关工具类
 * 解决问题： 1、快速对实体的常驻字段，如：crtUser、crtHost、updUser等值快速注入
 *
 * @author hutu
 * @date 2018/8/8 16:51
 */
public class EntityUtils {
    final static Logger log = LoggerFactory.getLogger(EntityUtils.class);
    // 新建默认属性
    private final static String[] createFields = {"createName", "createId", "createTime"};
    // 更新默认属性
    private final static String[] updateFields = {"updateName", "updateId", "updateTime"};

    /**
     * 快速将bean的crtUser、crtHost、crtTime、updUser、updHost、updTime附上相关值
     *
     * @param entity 实体bean
     * @author 王浩彬
     */
    public static <T> void setCreatAndUpdatInfo(T entity) {
        setCreateInfo(entity);
        setUpdatedInfo(entity);
    }

    /**
     * 快速将bean的crtUser、crtHost、crtTime附上相关值
     *
     * @param entity 实体bean
     * @author 王浩彬
     */
    public static <T> void setCreateInfo(T entity) {
        String createName = URLDecoder.decode("createName");
        Integer createId = getUserId();
        LocalDateTime createTime = LocalDateTime.now();
        Object[] values = {createName, createId, createTime};
        // 填充默认属性值
        setDefaultValues(entity, createFields, values);
    }

    /**
     * 快速将bean的updUser、updHost、updTime附上相关值
     *
     * @param entity 实体bean
     * @author 王浩彬
     */
    public static <T> void setUpdatedInfo(T entity) {
        String updateName = URLDecoder.decode("updateName");
        Integer updateId = getUserId();
        LocalDateTime updateTime = LocalDateTime.now();
        Object[] values = {updateName, updateId, updateTime};
        // 填充默认属性值
        setDefaultValues(entity, updateFields, values);
    }

    /**
     * 依据对象的属性数组和值数组对对象的属性进行赋值
     *
     * @param entity 对象
     * @param fields 属性数组
     * @param value  值数组
     * @author 王浩彬
     */
    private static <T> void setDefaultValues(T entity, String[] fields, Object[] value) {
        for (int i = 0; i < fields.length; i++) {
            String field = fields[i];
            if (ReflectionUtils.hasField(entity, field)) {
                ReflectionUtils.invokeSetter(entity, field, value[i]);
            }
        }
    }

    /**
     * 根据主键属性，判断主键是否值为空
     *
     * @param entity 实体
     * @param field  属性
     * @return 主键为空，则返回false；主键有值，返回true
     * @author 王浩彬
     * @date 2016年4月28日
     */
    public static <T> boolean isPKNotNull(T entity, String field) {
        if (!ReflectionUtils.hasField(entity, field)) {
            return false;
        }
        Object value = ReflectionUtils.getFieldValue(entity, field);
        return value != null && !"".equals(value);
    }

    public static Integer getUserId() {

        try {
            CallerInfo callerInfo = JwtUtils.getCallerInfo();
            return callerInfo.uid;
        } catch (Exception e) {
            log.error("获取不到token");
            return 0;
        }
    }
}
