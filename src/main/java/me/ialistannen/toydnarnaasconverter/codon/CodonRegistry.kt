package me.ialistannen.toydnarnaasconverter.codon

import me.ialistannen.toydnarnaasconverter.data.SplittableString
import me.ialistannen.toydnarnaasconverter.data.Trie
import me.ialistannen.toydnarnaasconverter.data.toSplittableString

/**
 * Contains all codons and is able to convert and find them.
 */
object CodonRegistry {

    private val codonTrie: Trie<SplittableString, Codon> = Trie()

    init {
        CodonType.values()
                .flatMap { it.codons }
                .forEach {
                    codonTrie.add(it.sequence.toSplittableString(), it)
                }
    }

    operator fun get(sequence: String) = codonTrie[sequence.toSplittableString()]
}

/**
 * Converts this String to a [Codon], if the length is 3.
 *
 * @return the converted [Codon].
 */
fun String.toCodon(): Codon? {
    if (length != 3) {
        return null
    }

    return CodonRegistry[this]
}
