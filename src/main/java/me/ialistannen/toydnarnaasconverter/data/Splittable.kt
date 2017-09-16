package me.ialistannen.toydnarnaasconverter.data

/**
 * A splittable object. This is needed to build a prefix tree.
 */
interface Splittable<T> {

    /**
     * Splits this object into parts.
     *
     *
     * **Must return terminals that can not be split any further!**
     *
     * @return The parts that make up this splittable.
     */
    fun split(): List<Splittable<T>>

    /**
     * Concatenates the given parts to a single splittable.
     *
     *
     * Does not alter this object.
     *
     * @param parts The symbols to join
     * @return The join result
     */
    fun concatParts(parts: Collection<T>): Splittable<T>
}