package com.example.lib.algorithm

import java.util.*

object TreeAlgorithm : Runnable {
    override fun run() {
        test()
    }

    fun test() {

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

    //先序
    fun preOrderTraverse12(treeNode: TreeNode?, list: LinkedList<TreeNode>) {
        if (treeNode == null) {
            return
        }
        var currNode = treeNode
        while (currNode != null) {
            list.add(currNode)
            if (currNode.isExistLeftChild()) {
                currNode = currNode.leftChild!!
            } else if (currNode.isExistRightChild()){
                currNode = currNode.rightChild
            } else if (currNode.parent != null) {
                currNode = currNode.parent!!.rightChild
            }
        }
    }

}