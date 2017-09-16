package me.ialistannen.toydnarnaasconverter.codon

/**
 * Represents a single Codon.
 */
data class Codon(val type: CodonType, val sequence: String) {

    /**
     * @return this codon as an [AminoAcid], if possible. Stop tokens have no equivalent.
     */
    fun asAcid(): AminoAcid? = AminoAcid.forCodon(sequence)
}