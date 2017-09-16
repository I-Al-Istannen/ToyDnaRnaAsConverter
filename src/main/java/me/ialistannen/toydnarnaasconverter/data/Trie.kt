package me.ialistannen.toydnarnaasconverter.data

import javafx.util.Pair

/**
 * A small implementation of a Trie.
 */
class Trie<T : Splittable<T>, V> {

    private val rootNode: TrieNode<T, V> by lazy {
        TrieNode<T, V>(null, null, null)
    }

    /**
     * Adds a new mapping to the Trie.
     *
     * @param key The key
     * @param value The value
     */
    fun add(key: T, value: V) {
        rootNode.addData(key, value)
    }

    /**
     * Adds a new mapping to the Trie.
     *
     * @param key The keys for the value
     * @param value The value
     */
    fun addAllWithKey(key: Collection<T>, value: V) {
        for (t in key) {
            add(t, value)
        }
    }

    /**
     * Removes all data associated with the key. Also removes all deeper levels dependent on the key.
     *
     * @param key The key to remove
     */
    fun remove(key: T) {
        rootNode.removeData(key)
    }

    /**
     * Returns the value for a key.
     *
     * @param key The key
     * @return The value associated with the key
     */
    operator fun get(key: T): V? {
        return rootNode[key]
    }

    /**
     * @return The amount of leaf nodes in the trie.
     */
    fun size(): Int {
        return rootNode.size()
    }

    /**
     * @return All the nodes in this Trie.
     */
    fun getAll(): List<Pair<T, V>> = rootNode.getAll()
}
