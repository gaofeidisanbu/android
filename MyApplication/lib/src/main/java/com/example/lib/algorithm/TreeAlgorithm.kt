package com.example.lib.algorithm

import com.example.lib.common.printArray
import java.util.*
import kotlin.math.max

object TreeAlgorithm : Runnable {
    override fun run() {
        maxHeadTest()
    }

    fun maxHeadTest() {
        val array = arrayOf<Int>(5, 1, 13, 3, 16, 7, 10, 14, 6, 9)
        maxHead(array)
        array.printArray()
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
            } else if (currNode.isExistRightChild()) {
                currNode = currNode.rightChild
            } else if (currNode.parent != null) {
                currNode = currNode.parent!!.rightChild
            }
        }
    }


    /**
     * 它是一颗完全二叉树,它可以是空
     * 树中结点的值总是不大于或者不小于其孩子结点的值
     * 每一个结点的子树也是一个堆
     */
    fun maxHead(array: Array<Int>) {
        val size = array.size
        val lastNodeParentIndex = size / 2
        if (lastNodeParentIndex == 0) {
            return
        }
        for (i in lastNodeParentIndex - 1 downTo 0) {
            val currParentNodeIndex = i
            val currLeftNodeIndex = 2 * i + 1
            val currRightNodeIndex = 2 * i + 2
            if (currRightNodeIndex > size - 1) {
                if (currLeftNodeIndex == size - 1) {
                    if (array[currLeftNodeIndex] > array[currParentNodeIndex]) {
                        val temp = array[currParentNodeIndex]
                        array[currParentNodeIndex] = array[currLeftNodeIndex]
                        array[currLeftNodeIndex] = temp
                    }
                }
            } else {
                val maxNode = max(array[currLeftNodeIndex], array[currRightNodeIndex])
                if (maxNode > array[currParentNodeIndex]) {
                    val maxChildNodeIndex = if (maxNode == array[currLeftNodeIndex]) currLeftNodeIndex else currRightNodeIndex
                    val temp = array[currParentNodeIndex]
                    array[currParentNodeIndex] = array[maxChildNodeIndex]
                    array[maxChildNodeIndex] = temp
                    var j = maxChildNodeIndex
                    while (j <= lastNodeParentIndex) {
                        if (2 * j + 2 > size - 1) {
                            if (2 * j + 1 == size - 1) {
                                val temp = array[j]
                                array[j] = array[2 * j + 1]
                                array[2 * j + 1] = temp
                                break
                            } else {
                                break
                            }
                        } else {
                            val maxDown = max(array[2 * j + 1], array[2 * j + 2])
                            if (maxDown > array[j]) {
                                val downIndex = if (array[2 * j + 1] > array[2 * j + 2]) 2 * j + 1 else 2 * j + 2
                                val temp = array[j]
                                array[j] = array[downIndex]
                                array[downIndex] = temp
                                j = downIndex
                            } else {
                                break
                            }
                        }
                    }
                }
            }

        }
    }

}