package com.hutu.upms.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.auth.entity.TreeNode;
import com.hutu.auth.util.TreeUtil;
import com.hutu.upms.admin.entity.Organization;
import com.hutu.upms.admin.mapper.OrganizationMapper;
import com.hutu.upms.admin.service.OrganizationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 组织 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-06-05
 */
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements OrganizationService {

    @Override
    public List getPermissionTree(){
        List<Organization> list = list();
        List<TreeNode> treeList = new ArrayList<>();
        for (Organization obj : list) {
            TreeNode treenode = new TreeNode(obj.getId(), obj.getPId(), obj.getName());
            treeList.add(treenode);
        }
        return TreeUtil.buildByRecursive(treeList, 0);
    }
}