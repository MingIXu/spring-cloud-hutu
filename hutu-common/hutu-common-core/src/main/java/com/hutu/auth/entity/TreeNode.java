package com.hutu.auth.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 树节点
 *
 * @author hutu
 * @date 2019/7/9 16:10
 */
public class TreeNode {
    protected int id;
    protected int parentId;
    protected String label;

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    List<TreeNode> children = new ArrayList<TreeNode>();

    public TreeNode(int id, int parentId, String label) {
        this.id = id;
        this.parentId = parentId;
        this.label = label;
    }

    public TreeNode() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void add(TreeNode node) {
        children.add(node);
    }
}
