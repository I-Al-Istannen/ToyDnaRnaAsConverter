package me.ialistannen.toydnarnaasconverter.data

import java.util.stream.Collectors

/**
 * A [Splittable] String.
 */
data class SplittableString(private val data: String) : Splittable<SplittableString> {

    override fun split(): List<Splittable<SplittableString>> {
        return data.codePoints()
                .mapToObj(Character::toChars)
                .map { String(it) }
                .map { it.toSplittableString() }
                .collect(Collectors.toList())
    }

    override fun concatParts(parts: Collection<SplittableString>): Splittable<SplittableString> {
        return parts.joinToString("") { it.data }.toSplittableString()
    }
}

/**
 * @return This string as a [SplittableString].
 */
fun String.toSplittableString(): SplittableString = SplittableString(this)