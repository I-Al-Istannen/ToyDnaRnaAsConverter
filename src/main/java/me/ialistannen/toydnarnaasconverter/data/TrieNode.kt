package me.ialistannen.toydnarnaasconverter.data

import javafx.util.Pair
import java.util.*
import kotlin.collections.ArrayList

/**
 * A node in a [Trie].
 */
internal class TrieNode<T : Splittable<T>, V>(private val key: T?,
                                              private var value: V?,
                                              private val parent: TrieNode<T, V>?) {

    private val children: MutableList<TrieNode<T, V>> = ArrayList()

    init {
        if (!isRootNode && key == null) {
            throw IllegalArgumentException("Key can not be null, if this is not the root!")
        }
    }

    /**
     * @param data The key to add
     * @param value The value to add
     */
    fun addData(data: T, value: V) {
        addData(ArrayDeque(data.split()), value)
    }

    /**
     * @return The amount of leaf nodes in this node.
     */
    fun size(): Int {
        var count = 0

        if (children.isEmpty()) {
            count++
        }

        for (child in children) {
            count += child.size()
        }
        return count
    }

    private fun addData(data: Queue<Splittable<T>>, value: V) {
        if (data.isEmpty()) {
            this.value = value
            return
        }

        val element = unsafeCast<T>(data.poll())
        val childNode = findChildForKey(element)

        if (childNode != null) {
            childNode.addData(data, value)
        } else {
            val child = TrieNode(element, null, this)
            children.add(child)
            child.addData(data, value)
        }
    }

    /**
     * @param key The key to remove
     */
    fun removeData(key: T) {
        val parts = ArrayDeque(key.split())

        if (parts.isEmpty()) {
            return
        }

        if (parts.size == 1) {
            findChildForKey(parts.poll())?.let { children.remove(it) }
            return
        }

        val element = parts.poll()
        val newKey = unsafeCast<T>(key.concatParts(unsafeCast(parts)))

        findChildForKey(element)?.removeData(newKey)
    }

    /**
     * @param key The key
     * @return The value for the given key
     */
    operator fun get(key: Splittable<T>): V? {
        val split = ArrayDeque(key.split())

        if (!split.isEmpty()) {
            val element = split.poll()

            val newKey = key.concatParts(unsafeCast(split))
            val childOptional = findChildForKey(element)

            return childOptional?.get(newKey)
        }

        return this.value
    }

    /**
     * @return All items in this Trie.
     */
    fun getAll(): List<Pair<T, V>> {
        val items = ArrayList<Pair<T, V>>()
        if (isRootNode) {
            for (child in children) {
                items.addAll(child.getAll(child.key!!))
            }
        }
        return items
    }

    /**
     * @return All items in this Trie.
     */
    private fun getAll(prefix: T): List<Pair<T, V>> {
        val items = ArrayList<Pair<T, V>>()

        for (node in children) {
            val key = unsafeCast<T>(prefix.concatParts(Arrays.asList<T>(prefix, node.key)))

            if (node.value != null) {
                items.add(Pair<T, V>(key, node.value))
            }
            items.addAll(node.getAll(key))
        }

        return items
    }

    /**
     * @param key The key to use
     * @return The found node, if any
     */
    private fun findChildForKey(key: Splittable<T>): TrieNode<T, V>? {
        return children.firstOrNull { trieNode -> trieNode.key == key }
    }

    private val isRootNode: Boolean
        get() = parent == null


    private fun <T> unsafeCast(any: Any): T {
        @Suppress("UNCHECKED_CAST")
        return any as T
    }
}
