package me.ialistannen.toydnarnaasconverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import me.ialistannen.toydnarnaasconverter.codon.AminoAcid;
import me.ialistannen.toydnarnaasconverter.codon.Codon;
import me.ialistannen.toydnarnaasconverter.codon.CodonRegistry;
import me.ialistannen.toydnarnaasconverter.codon.CodonType;
import me.ialistannen.toydnarnaasconverter.exception.IllegalCodonFoundException;
import me.ialistannen.toydnarnaasconverter.exception.NoStartCodonFoundException;

/**
 * Converts a DNA base sequence to a list of amino acids.
 */
public class DnaSequencer {

  private static final int CODON_LENGTH = 3;


  public List<Codon> convert(String input) {
    int startIndex = findStartIndex(input);
    String transcribedPart = input.substring(startIndex);

    List<Codon> codons = new ArrayList<>();

    for (int i = 0; i * CODON_LENGTH < transcribedPart.length() - CODON_LENGTH; i++) {
      String triplet = transcribedPart.substring(i * CODON_LENGTH, (i + 1) * CODON_LENGTH);
      Optional<Codon> codonOptional = CodonRegistry.getInstance().getForSequene(triplet);

      if (!codonOptional.isPresent()) {
        throw new IllegalCodonFoundException(triplet);
      }

      Codon codon = codonOptional.get();

      codons.add(codon);

      if (codon.getType() == CodonType.STOP) {
        break;
      }
    }

    return codons;
  }

  private int findStartIndex(String input) {
    int firstOccurrence = findFirstOccurrence(input, CodonType.START.getCodons());

    if (firstOccurrence == Integer.MAX_VALUE) {
      throw new NoStartCodonFoundException();
    }

    return firstOccurrence;
  }

  private int findFirstOccurrence(String input, Collection<Codon> codons) {
    int startIndex = Integer.MAX_VALUE;
    for (Codon codon : codons) {
      int tmpStart = input.indexOf(codon.getSequence());
      if (tmpStart != -1 && tmpStart < startIndex) {
        startIndex = tmpStart;
      }
    }

    return startIndex;
  }

  public static void main(String[] args) {
    String sequence = "32" + "AUG";
    for (int i = 0; i < 5; i++) {
      sequence += getRandomAcidCodon();
    }

    sequence += "UAA" + getRandomAcidCodon() + getRandomAcidCodon();

    System.out.println(sequence);

    List<Codon> codons = new DnaSequencer().convert(sequence);
    for (Codon codon : codons) {
      if (codon.getType() == CodonType.AMINO_ACID) {
        System.out.printf("%-10s ", codon.asAcid().getAbbreviation());
      } else if (codon.getType() == CodonType.START) {
        System.out.printf("%-5s(%s) ", codon.getType(), codon.asAcid().getAbbreviation());
      } else {
        System.out.printf("%-5s(%s) ", codon.getType(), codon.getSequence());
      }
    }
    System.out.println();

    for (Codon codon : codons) {
      System.out.printf("%-10s ", codon.getSequence());
    }
    System.out.println();
  }

  private static String getRandomAcidCodon() {
    AminoAcid acid = AminoAcid.values()[
        ThreadLocalRandom.current().nextInt(AminoAcid.values().length)
        ];
    return acid.getCodons().get(0);
  }
}
