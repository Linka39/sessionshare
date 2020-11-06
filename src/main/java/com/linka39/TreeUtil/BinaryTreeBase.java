package com.linka39.TreeUtil;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class BinaryTreeBase {
    public static class TreeNode{
        //数据
        public int data;
        //左节点
        public TreeNode left;
        //右节点
        public TreeNode right;
    }

    public static void main(String[] agrs) {

        int[] arr = {5,17,15,19,4,8,7,10,9,14,16};
        TreeNode root = new TreeNode();
        root.data = 11;
        System.out.println("TreeMain start");
        //创建二叉树
        for(int item :arr) {
            createTree(root,item);
        }
        //先序遍历
        System.out.println("先序遍历:");
        preOrderTraverse(root);
        System.out.println();
/*
        //中序遍历
        System.out.println("中序遍历");
        midOrderTraverse(root);
        System.out.println();

        //后序遍历
        System.out.println("后序遍历");
        postOrderTraverse(root);
        System.out.println();

        //广度遍历
        System.out.println("广度遍历");
        layOrderTraverse(root);
        System.out.println();

        //深度遍历
        System.out.println("深度遍历");
        deepOrderTraverse(root);
        System.out.println();

        //查找节点
        find(root,10);
        System.out.println("树的高度:"+height(root));
        System.out.println("TreeMain end");*/

        delete(root,15);

        //先序遍历
        System.out.println("先序遍历:");
        preOrderTraverse(root);
        System.out.println();


    }

    /**
     * 根据现有数据 生成二叉排序树(右边的节点大于左边)
     * @param node
     * @param data
     */
    public static TreeNode createTree(TreeNode node,int data) {
        if(null==node) {
            node = new TreeNode();
            node.data = data;
            return node;
        }else {
            if(data>node.data){
                node.right = createTree(node.right,data);
            }else
                node.left = createTree(node.left,data);
        }
        return node;
    }

    /**
     * 先序遍历
     * @param node
     */
    public static void preOrderTraverse(TreeNode node) {
        if(null == node)
            return;
        System.out.print(node.data+",");
        preOrderTraverse(node.left);
        preOrderTraverse(node.right);
    }

    /**
     * 中序遍历
     * @param node
     */
    public static void midOrderTraverse(TreeNode node) {
        if(null==node) {
            return;
        }
        midOrderTraverse(node.left);
        System.out.print(node.data+",");
        midOrderTraverse(node.right);
    }

    /**
     * 后序遍历
     * @param node
     */
    public static void postOrderTraverse(TreeNode node) {
        if(null==node) {
            return;
        }
        postOrderTraverse(node.left);
        postOrderTraverse(node.right);
        System.out.print(node.data+",");
    }

    /**
     * 广度遍历
     * @param root
     */
    public static void layOrderTraverse(TreeNode root) {
        Queue<TreeNode> q = new ArrayDeque<>();
        if(null != root) {
            System.out.print(root.data +",");
            q.add(root.left);
            q.add(root.right);
            while(!q.isEmpty()) {
                TreeNode node = q.poll();
                System.out.print(node.data +",");
                if(null!=node.left) {
                    q.add(node.left);
                }
                if(null!=node.right) {
                    q.add(node.right);
                }
            }
        }
    }

    /**
     * 深度遍历,用栈实现_先序遍历
     * @param root
     */
    public static void deepOrderTraverse(TreeNode root) {
        Stack<TreeNode> q = new Stack<>();
        if(null!=root) {
            System.out.print(root.data + ",");
            q.push(root.right);
            q.push(root.left);
            while(!q.isEmpty()) {
                TreeNode node = q.pop();
                System.out.print(node.data + ",");
                if(null!=node.right)
                    q.push(node.right);
                if(null!=node.left)
                    q.push(node.left);
            }
        }
    }

    /**
     * 查找节点
     * @param node 根节点
     * @param data  数据
     */
    public static TreeNode find(TreeNode node,int data) {
        if(null==node) {
            System.out.println("没有找到节点");
            return null;
        }
        if(data>node.data){
            return find(node.right,data);
        }else if(data<node.data){
            return find(node.left,data);
        }else {
            System.out.println("找到节点："+data);
            return node;
        }
    }

    /**
     * 查找父节点
     * @param node 根节点
     * @param data  数据
     */
    // 左子树为0，右子树为1
    static int LR = 0;
    public static TreeNode findParent(TreeNode node,int data,TreeNode parent) {
        if(null==node) {
            System.out.println("没有找到节点");
            return null;
        }
        if(data>node.data){
            LR=1;
            return findParent(node.right,data,node);
        }else if(data<node.data){
            LR=0;
            return findParent(node.left,data,node);
        }else {
            System.out.println("找到节点："+data);
            return parent;
        }
    }

    /**
     * 删除节点
     * @param node 根节点
     * @param data  数据
     */
    public static void delete(TreeNode node,int data) {
        TreeNode parent = findParent(node,data,null);
        node = find(node,data);
        if(null==node.left && null==node.right) {
            //直接删除节点
            node=null;
        }else if(null==node.left && null!=node.right){
            //左子树为空，右子树不为空
            if(parent!=null){
                if(LR==0){
                    parent.left=node.right;
                }else{
                    parent.right=node.right;
                }
            }
            node = node.right;
        }else if(null!=node.left && null==node.right){
            //左子树为空，右子树为空
            if(parent!=null){
                if(LR==0){
                    parent.left=node.left;
                }else
                    parent.right=node.left;
            }
            node = node.left;
        }else if(null!=node.left && null!=node.right){
            //左右都不为空，选择右子树最小的节点替换下
            TreeNode tempNode = node.right;
            while (tempNode.left!=null)
                tempNode=tempNode.left;
            if(parent!=null){
                if(LR==0){
                    parent.left=tempNode;
                }else{
                    parent.right=tempNode;
                }
            }
            tempNode.left = node.left;
            node =tempNode;
        }
    }

    /**
     * 求树的高度
     * @param node
     * @return
     */
    public static int height(TreeNode node) {
        if(null==node) {
            return 0;
        }
        int hLeft = height(node.left)+1;
        int hRight = height(node.right)+1;
        return hLeft > hRight? hLeft:hRight;
    }

}
