package com.example.lib.algorithm

import com.example.lib.common.print
import java.util.*
import java.util.Stack

object TreeAlgorithm : Runnable {

    override fun run() {
        val array = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
        val size = array.size
        val rootNode = TreeNode(0, null, null, null)
        val queue = LinkedList<TreeNode>()
        queue.add(rootNode)
        var index = 0
        var index2 = 1
        while (!queue.isEmpty()) {
            val tempNode = queue.poll()
            tempNode.data = array[index]
            if (index2 < size) {
                val leftChild = TreeNode(0, tempNode, null, null)
                tempNode.leftChild = leftChild
                queue.add(leftChild)
                index2++
            }
            if (index2 < size) {
                val rightChild = TreeNode(0, tempNode, null, null)
                tempNode.rightChild = rightChild
                queue.add(rightChild)
                index2++
            }
            index++
        }
        val list = LinkedList<TreeNode>();
//        preOrderTraverse1(rootNode, list)
//        breadthFirstSearch(rootNode, list)
        traverse13(rootNode, list)
        list.print()
    }

    //先序遍历，递归
    private fun preOrderTraverse1(treeNode: TreeNode?, list: LinkedList<TreeNode>) {
        if (treeNode == null) {
            return
        }
        preOrderTraverse1(treeNode.leftChild, list)
        list.add(treeNode)
        preOrderTraverse1(treeNode.rightChild, list)
    }

    //遍历，木有找到实现方式
    fun traverse12(treeNode: TreeNode?, list: LinkedList<TreeNode>) {
        if (treeNode == null) {
            return
        }

    }

    //深度优先遍历，使用栈
    fun traverse13(treeNode: TreeNode?, list: LinkedList<TreeNode>) {
        if (treeNode == null) {
            return
        }
        val stack = Stack<TreeNode>()
        stack.add(treeNode)
        while (stack.isNotEmpty()) {
            val tempNode = stack.pop()
            list.add(tempNode)
            tempNode.rightChild?.let {
                stack.add(it)
            }
            tempNode.leftChild?.let {
                stack.add(it)
            }
        }


    }

    private fun breadthFirstSearch(treeNode: TreeNode?, list: LinkedList<TreeNode>) {
        treeNode?.let { rootNode ->
            val queue = LinkedList<TreeNode>()
            queue.offer(rootNode)
            while (queue.isNotEmpty()) {
                val tempNode = queue.poll()
                list.add(tempNode)
                tempNode.leftChild?.let {
                    queue.offer(it)
                }
                tempNode.rightChild?.let {
                    queue.offer(it)
                }
            }
        }
    }

}