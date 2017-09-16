package me.ialistannen.toydnarnaasconverter.exception;

/**
 * Thrown when no start codon could be found.
 */
public class NoStartCodonFoundException extends RuntimeException {

  public NoStartCodonFoundException() {
    super("No start token found");
  }
}
