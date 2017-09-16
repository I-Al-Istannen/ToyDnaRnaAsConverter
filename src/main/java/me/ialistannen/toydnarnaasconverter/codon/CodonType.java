package me.ialistannen.toydnarnaasconverter.codon;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * The type of a codon.
 */
public enum CodonType {
  AMINO_ACID(AminoAcid.getAllCodons()),
  START("AUG"),
  STOP("UAA", "UAG", "UGA");

  private Collection<Codon> codons;

  CodonType(String... tokens) {
    this(Arrays.asList(tokens));
  }

  CodonType(Collection<String> tokens) {
    this.codons = tokens.stream()
        .map(s -> new Codon(s, this))
        .collect(Collectors.toList());
  }

  /**
   * @return All codons of the given type.
   */
  public Collection<Codon> getCodons() {
    return codons;
  }
}
