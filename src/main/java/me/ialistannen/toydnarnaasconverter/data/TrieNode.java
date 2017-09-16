package me.ialistannen.toydnarnaasconverter.data;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import javafx.util.Pair;

/**
 * A node in a {@link Trie}.
 *
 * @param <T> The type of the key it contains
 * @param <V> The type of the value
 */
class TrieNode<T extends Splittable<T>, V> {

  private T key;
  private V value;
  private TrieNode<T, V> parent;
  private List<TrieNode<T, V>> children;

  TrieNode(T key, V value, TrieNode<T, V> parent) {
    this.key = key;
    this.value = value;
    this.parent = parent;

    this.children = new ArrayList<>();

    if (!isRootNode() && key == null) {
      throw new IllegalArgumentException("Key can not be null, if this is not the root!");
    }
  }

  /**
   * @param data The key to add
   * @param value The value to add
   */
  void addData(T data, V value) {
    addData(new ArrayDeque<>(data.split()), value);
  }

  /**
   * @return The amount of leaf nodes in this node.
   */
  int size() {
    int count = 0;

    if (children.isEmpty()) {
      count++;
    }

    for (TrieNode<T, V> child : children) {
      count += child.size();
    }
    return count;
  }

  private void addData(Queue<Splittable<T>> data, V value) {
    if (data.isEmpty()) {
      this.value = value;
      return;
    }

    T element = Util.unsafeCast(data.poll());
    Optional<TrieNode<T, V>> childNode = findChildForKey(element);
    if (childNode.isPresent()) {
      childNode.get().addData(data, value);
    } else {
      TrieNode<T, V> child = new TrieNode<>(element, null, this);
      children.add(child);
      child.addData(data, value);
    }
  }

  /**
   * @param key The key to remove
   */
  void removeData(T key) {
    Queue<Splittable<T>> parts = new ArrayDeque<>(key.split());

    if (parts.isEmpty()) {
      return;
    }

    if (parts.size() == 1) {
      findChildForKey(parts.poll()).ifPresent(child -> children.remove(child));
      return;
    }

    Splittable<T> element = parts.poll();
    T newKey = Util.unsafeCast(key.concatParts(Util.unsafeCast(parts)));

    findChildForKey(element).ifPresent(child -> child.removeData(newKey));
  }

  /**
   * @param key The key
   * @return The value for the given key
   */
  Optional<V> get(Splittable<T> key) {
    Queue<Splittable<T>> split = new ArrayDeque<>(key.split());

    if (!split.isEmpty()) {
      Splittable<T> element = split.poll();

      Splittable<T> newKey = key.concatParts(Util.unsafeCast(split));
      Optional<TrieNode<T, V>> childOptional = findChildForKey(element);

      return childOptional
          .flatMap(child -> child.get(newKey));
    }

    return Optional.ofNullable(this.value);
  }

  /**
   * @return All items in this Trie.
   */
  List<Pair<T, V>> getAll() {
    List<Pair<T, V>> items = new ArrayList<>();
    if (isRootNode()) {
      for (TrieNode<T, V> child : children) {
        items.addAll(child.getAll(child.key));
      }
    }
    return items;
  }

  /**
   * @return All items in this Trie.
   */
  private List<Pair<T, V>> getAll(T prefix) {
    List<Pair<T, V>> items = new ArrayList<>();

    for (TrieNode<T, V> node : children) {
      T key = Util.unsafeCast(prefix.concatParts(Arrays.asList(prefix, node.key)));

      if (node.value != null) {
        items.add(new Pair<>(key, node.value));
      }
      items.addAll(node.getAll(key));
    }

    return items;
  }

  /**
   * @param key The key to use
   * @return The found node, if any
   */
  private Optional<TrieNode<T, V>> findChildForKey(Splittable<T> key) {
    return children.stream()
        .filter(trieNode -> trieNode.key.equals(key))
        .findAny();
  }

  private boolean isRootNode() {
    return parent == null;
  }

  @Override
  public String toString() {
    return "TrieNode{"
        + "key=" + key
        + ", value=" + value
        + ", parent=" + parent
        + ", children=" + children
        + '}';
  }
}
