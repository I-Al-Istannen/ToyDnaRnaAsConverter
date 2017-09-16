package me.ialistannen.toydnarnaasconverter.codon

/**
 * The type of a codon.
 */
enum class CodonType {
    AMINO_ACID(AminoAcid.getAllCodons()),
    START("AUG"),
    STOP("UAA", "UAG", "UGA");

    val codons: List<Codon>

    constructor(codons: Collection<String>) {
        this.codons = codons.map { Codon(this, it) }
    }

    constructor(vararg codons: String) : this(codons.toList())
}