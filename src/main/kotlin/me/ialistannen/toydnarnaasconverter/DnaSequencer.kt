package me.ialistannen.toydnarnaasconverter

import me.ialistannen.toydnarnaasconverter.codon.AminoAcid
import me.ialistannen.toydnarnaasconverter.codon.Codon
import me.ialistannen.toydnarnaasconverter.codon.CodonType
import me.ialistannen.toydnarnaasconverter.codon.toCodon
import me.ialistannen.toydnarnaasconverter.exception.IllegalCodonFoundException
import me.ialistannen.toydnarnaasconverter.exception.NoStartCodonFoundException
import java.util.*
import java.util.concurrent.ThreadLocalRandom

/**
 * Converts a DNA base sequence to a list of amino acids.
 */
class DnaSequencer {

    /**
     * Converts a String to a codon list.
     *
     * @param input The input to convert
     * @return A list with all codons found in the input, from START to the first STOP
     */
    fun convert(input: String): List<Codon> {
        val startIndex = findStartIndex(input)
        val transcribedPart = input.substring(startIndex)

        val codons = ArrayList<Codon>()

        var i = 0
        while (i * CODON_LENGTH < transcribedPart.length - CODON_LENGTH) {
            val triplet = transcribedPart.substring(i * CODON_LENGTH, (i + 1) * CODON_LENGTH)
            val codon: Codon = triplet.toCodon() ?: throw IllegalCodonFoundException(triplet)

            codons.add(codon)

            if (codon.type === CodonType.STOP) {
                break
            }
            i++
        }

        return codons
    }

    private fun findStartIndex(input: String): Int {
        val firstOccurrence = findFirstOccurrence(input, CodonType.START.codons)

        if (firstOccurrence == Integer.MAX_VALUE) {
            throw NoStartCodonFoundException()
        }

        return firstOccurrence
    }

    private fun findFirstOccurrence(input: String, codons: Collection<Codon>): Int {
        var startIndex = Integer.MAX_VALUE
        codons
                .asSequence()
                .map { input.indexOf(it.sequence) }
                .filter { it != -1 && it < startIndex }
                .forEach { startIndex = it }

        return startIndex
    }

    companion object {
        private val CODON_LENGTH = 3

        @JvmStatic
        fun main(args: Array<String>) {
            var sequence = "32" + "AUG"
            for (i in 0..4) {
                sequence += getRandomAcidCodon()
            }

            sequence += "UAA" + getRandomAcidCodon() + getRandomAcidCodon()

            println(sequence)

            val codons = DnaSequencer().convert(sequence)
            for (codon in codons) {
                when {
                    codon.type === CodonType.AMINO_ACID ->
                        System.out.printf("%-10s ", codon.asAcid()?.abbreviation)
                    codon.type === CodonType.START      ->
                        System.out.printf("%-5s(%s) ", codon.type, codon.asAcid()?.abbreviation)
                    else                                ->
                        System.out.printf("%-5s(%s) ", codon.type, codon.sequence)
                }
            }
            println()

            for (codon in codons) {
                System.out.printf("%-10s ", codon.sequence)
            }
            println()
        }

        private fun getRandomAcidCodon(): String {
            val acid = AminoAcid.values()[ThreadLocalRandom.current().nextInt(AminoAcid.values().size)]
            return acid.codons[0]
        }
    }
}