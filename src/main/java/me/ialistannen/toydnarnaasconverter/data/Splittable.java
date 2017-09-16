package me.ialistannen.toydnarnaasconverter.data;

import java.util.Collection;
import java.util.List;

/**
 * A splittable object. This is needed to build a prefix tree.
 */
public interface Splittable<T> {


  /**
   * Splits this object into parts.
   *
   * <p><strong>Must return terminals that can not be split any further!</strong>
   *
   * @return The parts that make up this splittable.
   */
  List<Splittable<T>> split();

  /**
   * Concatenates the given parts to a single splittable.
   *
   * <p>Does not alter this object.</p>
   *
   * @param parts The symbols to join
   * @return The join result
   */
  Splittable<T> concatParts(Collection<T> parts);
}
