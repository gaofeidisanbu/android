package com.example.lib.algorithm.tree;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

public class TreeTemplate {

    public static void main(String[] args) {
//        TreeNode treeNode1_1 = new TreeNode(9);
//        TreeNode treeNode1_2_1 = new TreeNode(15);
//        TreeNode treeNode1_2_2 = new TreeNode(7);
//        TreeNode treeNode1_2 = new TreeNode(20);
//        treeNode1_2.left = treeNode1_2_1;
//        treeNode1_2.right = treeNode1_2_2;
//        TreeNode root = new TreeNode(3);
//        root.left = treeNode1_1;
//        root.right = treeNode1_2;
        printlnTree(createTree(new Integer[] {1, null, 3, 4, 5, 6}));


    }

    public static TreeNode createTree(Integer[] integers) {
        int len = integers.length;
        if (len == 0 || integers[0] == null) {
            return null;
        }
        int i = 1;
        TreeNode root = new TreeNode(integers[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (i < len && !queue.isEmpty()) {
            TreeNode temp = queue.poll();
            if (integers[i] != null) {
                temp.left = new TreeNode(integers[i]);
                queue.add(temp.left);
            }
            i++;
            if (i < len && integers[i] != null) {
                temp.right = new TreeNode(integers[i]);
                queue.add(temp.right);
            }
            i++;
        }
        return root;
    }

    public static List<Integer> tra(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root != null) {
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                TreeNode temp = queue.poll();
                list.add(temp.val);
                if (temp.left != null) {
                    queue.add(temp.left);
                }
                if (temp.right != null) {
                    queue.add(temp.right);
                }
            }

        }
        return list;
    }

    public static void printlnTree(TreeNode root) {
        printlnList(tra(root));
    }

    public static void printlnList(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        list.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                sb.append(integer + ",");
            }
        });
        System.out.println(sb.toString());
    }
//给定一个二叉树，返回其节点值自底向上的层次遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
//
// 例如：
//给定二叉树 [3,9,20,null,null,15,7],
//
//     3
//   / \
//  9  20
//    /  \
//   15   7
//
//
// 返回其自底向上的层次遍历为：
//
// [
//  [15,7],
//  [9,20],
//  [3]
//]
//
// Related Topics 树 广度优先搜索


//leetcode submit region begin(Prohibit modification and deletion)

    // Definition for a binary tree node.


    static class Solution {
        public List<List<Integer>> levelOrderBottom(TreeNode root) {
            List<List<Integer>> list = new ArrayList<>();
            if (root != null) {
                Queue<TreeNode> queue = new LinkedList<>();
                queue.add(root);
                Queue<TreeNode> tempQueue = new LinkedList<>();
                while (!queue.isEmpty()) {
                    List<Integer> tempList = new ArrayList<Integer>();
                    while (!queue.isEmpty()) {
                        TreeNode temp = queue.poll();
                        tempList.add(temp.val);
                        tempQueue.add(temp);
                    }
                    while (!tempQueue.isEmpty()) {
                        TreeNode temp = tempQueue.poll();
                        if (temp.left != null) {
                            queue.add(temp.left);
                        }
                        if (temp.right != null) {
                            queue.add(temp.right);
                        }
                    }
                    list.add(0, tempList);
                }
            }

            return list;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)


}
