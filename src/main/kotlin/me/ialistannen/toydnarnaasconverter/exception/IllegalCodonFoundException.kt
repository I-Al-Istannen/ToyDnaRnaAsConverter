package me.ialistannen.toydnarnaasconverter.exception

/**
 * Thrown when an illegal codon is encountered.
 */
class IllegalCodonFoundException(codon: String) : RuntimeException("Found an illegal codon: '$codon'.")