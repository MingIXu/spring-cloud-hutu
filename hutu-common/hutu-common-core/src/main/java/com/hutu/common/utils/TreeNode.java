package com.hutu.common.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hutu
 * @date 2019-11-04 14:20
 */
@Getter
@Setter
@NoArgsConstructor
public class TreeNode {
    private Integer id;
    private Integer parentId;
    private String label;
    private List<TreeNode> children = new ArrayList<TreeNode>();

    public void add(TreeNode node){
        children.add(node);
    }

    public TreeNode(Integer id, Integer parentId, String label) {
        this.setId(id);
        this.setParentId(parentId);
        this.label = label;
    }
}
