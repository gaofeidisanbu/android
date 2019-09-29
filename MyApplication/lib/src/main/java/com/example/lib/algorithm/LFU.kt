package com.example.lib.algorithm

import java.util.*
import kotlin.Comparator
import kotlin.collections.HashMap

class LFU<T, R>(val count: Int) {
    private val map = HashMap<T, Node>()
    private val queue = PriorityQueue<Node>()

    fun set(key: T, value: R) {
        val existNode = map[key]
        if (existNode != null) {
            existNode.value = value
        } else {
            clear()
            map[key] = Node(key, value, 0)
        }

    }

    fun get(key: T): R? {
        val result = map[key]
        result?.let {
            queue.remove(it)
            result.accessCount++
            queue.add(result)
        }
        return result?.value
    }

    private fun clear() {
        if (queue.size == count) {
            val node = queue.poll()
            map.remove(node.key)
            print("remove, $node")
        }
    }


    inner class Node(val key: T, var value: R, var accessCount: Int) : Comparator<Node> {
        override fun compare(p0: Node, p1: Node): Int {
            return when {
                p0.accessCount == p1.accessCount -> 0
                p0.accessCount == p1.accessCount -> 1
                else -> -1

            }
        }


    }




}