package com.ligouhai.bao.entity.brt;

import java.util.TreeMap;

/**
 * @author ligouhai
 * @date 2020-03-30 10:08
 * @description 测试用红黑树
 */
public class BlackRedTree<K extends Comparable<K>, V> {

    private TreeNode root;

    private static final Boolean RED = false;

    private static final Boolean BLACK = true;

    class TreeNode<K extends Comparable<K>, V> {

        private K key;

        private V value;

        private TreeNode left;

        private TreeNode right;

        private TreeNode parent;

        private Boolean color;

        public V getValue() {
            return value;
        }

        public TreeNode getLeft() {
            return left;
        }

        public TreeNode getRight() {
            return right;
        }

        public TreeNode getParent() {
            return parent;
        }

        public Boolean getColor() {
            return color;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }

        public void setColor(Boolean color) {
            this.color = color;
        }

        public TreeNode(K key, V value, TreeNode left, TreeNode right, TreeNode parent, Boolean color) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.color = color;
        }

        public TreeNode() {}

        public K getKey() {
            return key;
        }
    }

    public void insert(K key, V value) {
        TreeNode node = new TreeNode();
        node.setKey(key);
        node.setValue(value);
    }

    /**
     *
     * @param node
     * 判断
     * 1. root 节点是否为null
     * 2. key是否存在
     * 3. 父节点是否为黑色
     */
    private void insert(TreeNode node) {
        TreeNode parent = null;
        TreeNode x = this.root;

        while (x != null) {
            parent = x;

            int cmp = x.key.compareTo(node.key);
            if (cmp > 0) {
                x = x.left;
            } else if (cmp < 0) {
                x = x.right;
            } else {

            }
        }
        if (parent == null) {
            this.root = node;
        }
    }
}
