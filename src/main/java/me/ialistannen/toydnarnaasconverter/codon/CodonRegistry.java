package me.ialistannen.toydnarnaasconverter.codon;

import java.util.Optional;
import me.ialistannen.toydnarnaasconverter.data.SplittableString;
import me.ialistannen.toydnarnaasconverter.data.Trie;

/**
 * Contains all codons and is able to convert and find them.
 */
public class CodonRegistry {

  private static final CodonRegistry instance = new CodonRegistry();

  private Trie<SplittableString, Codon> codonTrie;

  private CodonRegistry() {
    codonTrie = new Trie<>();

    for (CodonType type : CodonType.values()) {
      for (Codon codon : type.getCodons()) {
        codonTrie.add(new SplittableString(codon.getSequence()), codon);
      }
    }
  }

  public Optional<Codon> getForSequene(String sequence) {
    return codonTrie.get(new SplittableString(sequence));
  }

  public static CodonRegistry getInstance() {
    return instance;
  }
}
