package com.example.lib.algorithm

import java.util.*

object TreeAlgorithm : Runnable {
    override fun run() {

    }

    //先序
    fun preOrderTraverse1(treeNode: TreeNode?, list: LinkedList<TreeNode>) {
        if (treeNode == null) {
            return
        }
        list.add(treeNode)
        preOrderTraverse1(treeNode.leftChild, list)
        preOrderTraverse1(treeNode.rightChild, list)
    }

}