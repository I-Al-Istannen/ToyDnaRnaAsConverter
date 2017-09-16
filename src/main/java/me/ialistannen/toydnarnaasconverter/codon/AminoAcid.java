package me.ialistannen.toydnarnaasconverter.codon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import me.ialistannen.toydnarnaasconverter.util.EnumUtil;

/**
 * All 20 amino acids that the body can synthesize.
 */
public enum AminoAcid {
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

  private static Map<String, AminoAcid> reverseLookupMap;
  private final String abbreviation;
  private final List<String> codons;

  AminoAcid(String abbreviation, String... codons) {
    this.abbreviation = abbreviation;
    this.codons = new ArrayList<>(Arrays.asList(codons));
  }

  public String getAbbreviation() {
    return abbreviation;
  }

  /**
   * @return All codons that encode this amino acid.
   */
  public List<String> getCodons() {
    return codons;
  }

  public static List<String> getAllCodons() {
    return Arrays.stream(values())
        .map(AminoAcid::getCodons)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  /**
   * @param codon The codon to look up
   * @return The {@link AminoAcid} for that codon or null if none.
   */
  public static AminoAcid forCodon(String codon) {
    if (reverseLookupMap == null) {
      reverseLookupMap = EnumUtil.getReverseMultiMapping(AminoAcid.class, AminoAcid::getCodons);
    }

    return reverseLookupMap.get(codon);
  }
}
