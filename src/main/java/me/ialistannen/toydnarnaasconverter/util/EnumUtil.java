package me.ialistannen.toydnarnaasconverter.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * A Util to help with enums.
 */
public class EnumUtil {

  /**
   * @param enumClass The class of the enum
   * @param transformation The transformation to apply
   * @param <T> The enum class
   * @param <V> The class of the transformed value
   * @return A reverse-mapping of the enum
   */
  public static <T extends Enum<T>, V> Map<V, T> getReverseMultiMapping(Class<T> enumClass,
      Function<T, Collection<V>> transformation) {

    Map<V, T> result = new HashMap<>();

    for (T t : enumClass.getEnumConstants()) {
      for (V v : transformation.apply(t)) {
        result.put(v, t);
      }
    }

    return result;
  }
}
