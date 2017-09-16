package me.ialistannen.toydnarnaasconverter.exception;

/**
 *
 */
public class IllegalCodonFoundException extends RuntimeException {

  public IllegalCodonFoundException(String codon) {
    super("Found an illegal codon: '" + codon + "'.");
  }
}
