package me.ialistannen.toydnarnaasconverter.exception;

/**
 *
 */
public class NoEndCodonFoundException extends RuntimeException {

  public NoEndCodonFoundException() {
    super("No end codon was found");
  }
}
