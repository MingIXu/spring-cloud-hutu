package com.hutu.upms.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.upms.admin.entity.Organization;

import java.util.List;

/**
 * <p>
 * 组织 服务类
 * </p>
 *
 * @author generator
 * @since 2019-06-05
 */
public interface OrganizationService extends IService<Organization> {

    List getPermissionTree();
}
