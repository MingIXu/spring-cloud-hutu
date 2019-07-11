package com.hutu.upms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.common.core.entity.TreeNode;
import com.hutu.common.core.util.TreeUtil;
import com.hutu.upms.entity.Organization;
import com.hutu.upms.mapper.OrganizationMapper;
import com.hutu.upms.service.OrganizationService;
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
