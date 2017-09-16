package me.ialistannen.toydnarnaasconverter.util

/**
 * @param enumClass The class of the enum
 * @param transformation The transformation to apply
 * @return A reverse-mapping of the enum
 */
fun <T : Enum<T>, V> getReverseMultiMapping(enumClass: Class<T>,
                                            transformation: (T) -> Collection<V>): Map<V, T> {

    val result = HashMap<V, T>()

    for (t in enumClass.enumConstants) {
        for (v in transformation.invoke(t)) {
            result.put(v, t)
        }
    }

    return result
}
