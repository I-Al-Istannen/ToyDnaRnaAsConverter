package me.ialistannen.toydnarnaasconverter.data;

/**
 * Some utility functions.
 */
class Util {

  /**
   * Cats an object to the type you assign it to.
   *
   * @param input The input to cast
   * @param <T> The type to cast to
   * @return The casted element
   */
  static <T> T unsafeCast(Object input) {
    @SuppressWarnings("unchecked")
    T t = (T) input;
    return t;
  }
}
