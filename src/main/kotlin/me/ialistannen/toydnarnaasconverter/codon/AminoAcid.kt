package me.ialistannen.toydnarnaasconverter.codon

import me.ialistannen.toydnarnaasconverter.util.getReverseMultiMapping

/**
 * An amino acid.
 */
enum class AminoAcid(val abbreviation: String, val codons: List<String>) {
    ALANINE("Ala", "GCU", "GCC", "GCA", "GCG"),
    ARGININE("Arg", "CGU", "CGC", "CGA", "CGG", "AGA", "AGG"),
    ASPARAGINE("Asn", "AAU", "AAC"),
    ASPARTIC_ACID("Asp", "GAU", "GAC"),
    CYSTEINE("Cys", "UGU", "UGC"),
    GLUTAMIC_ACID("Glu", "GAA", "GAG"),
    GLUTAMINE("Gln", "CAA", "CAG"),
    GLYCINE("Gly", "GGU", "GGC", "GGA", "GGG"),
    HISTIDINE("His", "CAU", "CAC"),
    ISOLEUCINE("Ile", "AUU", "AUC", "AUA"),
    LEUCINE("Leu", "UUA", "UUG", "CUU", "CUC", "CUA", "CUG"),
    LYSINE("Lys", "AAA", "AAG"),
    METHIONINE("Met", "AUG"),
    PHENYLALANINE("Phe", "UUU", "UUC"),
    PROLINE("Pro", "CCU", "CCC", "CCA", "CCG"),
    SERINE("Ser", "AGU", "AGC", "UCU", "UCC", "UCA", "UCG"),
    THREONINE("Thr", "ACU", "ACC", "ACA", "ACG"),
    TRYPTOPHAN("Trp", "UGG"),
    TYROSINE("Tyr", "UAU", "UAC"),
    VALINE("Val", "GUU", "GUC", "GUA", "GUG");

    constructor(abbreviation: String, vararg codons: String) : this(abbreviation, codons.toList())


    companion object {
        @JvmStatic
        private val reverseLookupMap: Map<String, AminoAcid> by lazy {
            getReverseMultiMapping(AminoAcid::class.java, AminoAcid::codons)
        }

        /**
         * @param codon The codon to look up
         * @return The [AminoAcid] for that codon or null if none.
         */
        @JvmStatic
        fun forCodon(codon: String): AminoAcid? {
            return reverseLookupMap[codon]
        }

        /**
         * @return All codons from all amino acids.
         */
        @JvmStatic
        fun getAllCodons(): List<String> {
            return values().flatMap { it.codons }
        }
    }

}