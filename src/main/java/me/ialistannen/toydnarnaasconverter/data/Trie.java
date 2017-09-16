package me.ialistannen.toydnarnaasconverter.data;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javafx.util.Pair;

/**
 * A small implementation of a Trie.
 */
public class Trie<T extends Splittable<T>, V> {

  private TrieNode<T, V> rootNode;

  /**
   * Adds a new mapping to the Trie.
   *
   * @param key The key
   * @param value The value
   */
  public void add(T key, V value) {
    if (rootNode == null) {
      rootNode = new TrieNode<>(null, null, null);
    }

    rootNode.addData(key, value);
  }

  /**
   * Adds a new mapping to the Trie.
   *
   * @param key The keys for the value
   * @param value The value
   */
  public void addAllWithKey(Collection<T> key, V value) {
    for (T t : key) {
      add(t, value);
    }
  }

  /**
   * Removes all data associated with the key. Also removes all deeper levels dependent on the key.
   *
   * @param key The key to remove
   */
  public void remove(T key) {
    if (rootNode == null) {
      return;
    }

    rootNode.removeData(key);
  }

  /**
   * Returns the value for a key.
   *
   * @param key The key
   * @return The value associated with the key
   */
  public Optional<V> get(T key) {
    if (rootNode == null) {
      return Optional.empty();
    }

    return rootNode.get(key);
  }

  /**
   * @return The amount of leaf nodes in the trie.
   */
  public int size() {
    return rootNode == null ? 0 : rootNode.size();
  }

  /**
   * @return All the nodes in this Trie.
   */
  public List<Pair<T, V>> getAll() {
    if (rootNode == null) {
      return Collections.emptyList();
    }

    return rootNode.getAll();
  }
}
