package me.ialistannen.toydnarnaasconverter.codon;

import java.util.Objects;

/**
 * Contains all mappings from codons to amino acids.
 */
public class Codon {

  private CodonType type;
  private String sequence;

  public Codon(String sequence, CodonType type) {
    this.type = type;
    this.sequence = sequence;
  }

  /**
   * @return The base sequence
   */
  public String getSequence() {
    return sequence;
  }

  /**
   * @return The type of the codon
   */
  public CodonType getType() {
    return type;
  }

  /**
   * @return This codon as an AminoAcid. May be null for stop tokens.
   */
  public AminoAcid asAcid() {
    return AminoAcid.forCodon(getSequence());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Codon codon = (Codon) o;
    return getType() == codon.getType() &&
        Objects.equals(getSequence(), codon.getSequence());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getType(), getSequence());
  }

  @Override
  public String toString() {
    return "Codon{"
        + "type=" + type
        + ", sequence='" + sequence + '\''
        + '}';
  }
}
