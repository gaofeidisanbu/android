package com.example.lib.algorithm

class TreeNode(val data: Int, val parent: TreeNode? = null, var leftChild: TreeNode? = null, val rightChild: TreeNode? = null, val isRootNode: Boolean = false) {
    fun isExistLeftChild(): Boolean {
        return leftChild != null
    }

    fun isExistRightChild(): Boolean {
        return rightChild != null
    }

}