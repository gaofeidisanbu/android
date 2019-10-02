package com.example.lib.algorithm

class TreeNode(val data: Int, var leftChild: TreeNode? = null, val rightChild: TreeNode, val isRootNode: Boolean = false) {
    fun isExistLeftChild(): Boolean {
        return leftChild != null
    }

    fun isExistRightChild(): Boolean {
        return rightChild != null
    }
}