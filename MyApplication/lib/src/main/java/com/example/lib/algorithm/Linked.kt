package com.example.lib.algorithm

class Linked : ILinked {
    private var head: Node? = null
    private var size: Int = 0

    override fun insertLast(node: Node): Boolean {
        return insertInner(node, size)
    }

    override fun insertHead(node: Node): Boolean {
        return insertInner(node, 0)
    }

    override fun delete(node: Node): Boolean {
        return false
    }

    override fun size(): Int {
        return size
    }

    override fun insert(node: Node, index: Int): Boolean {
        if (head == null) {
            head = node
        } else {
            var curr: Node = head!!
            while (curr.next != null) {
                curr = curr.next!!
            }
            curr.next = node

        }


        return insertInner(node, index)
    }

    private fun insertInner(node: Node, index: Int): Boolean {
        if (index in 0 downTo (size - 1)) {
            if (index == 0) {
                val beforeHead = head
                head = node
                node.next = beforeHead
                size++
            } else {
                val curr = head
                var i = 0
                while (curr != null) {
                    if (i == index) {
                        val before = curr.next
                        curr.next = node
                        node.next = before
                        size++
                        break
                    }
                    i++
                }
            }
            return true
        }
        return false
    }

    override fun get(index: Int): Node? {
        return null
    }

    override fun isEmpty(): Boolean {
        return size > 0
    }

    override fun modify(index: Int, data: Any) {
    }


}


interface ILinked {

    fun insertLast(node: Node): Boolean

    fun insertHead(node: Node): Boolean

    fun delete(node: Node): Boolean

    fun size(): Int

    fun insert(node: Node, index: Int): Boolean

    fun get(index: Int): Node?

    fun isEmpty(): Boolean

    fun modify(index: Int, data: Any)

}

class Node(val data: Any, var next: Node? = null)