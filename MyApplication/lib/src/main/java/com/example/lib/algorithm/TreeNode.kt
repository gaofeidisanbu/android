package com.example.lib.algorithm

class TreeNode(var data: Int = 0, val parent: TreeNode? = null, var leftChild: TreeNode? = null, var rightChild: TreeNode? = null, val isRootNode: Boolean = false) {
    fun isExistLeftChild(): Boolean {
        return leftChild != null
    }

    fun isExistRightChild(): Boolean {
        return rightChild != null
    }

    override fun toString(): String {
        return data.toString()
    }

}