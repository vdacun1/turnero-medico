package edu.up.controllers.exceptions;

/** Raíz de todas las excepciones de repositorio. */
public class RepositoryException extends RuntimeException {
  public RepositoryException(String msg) {
    super(msg);
  }

  public RepositoryException(String msg, Throwable ex) {
    super(msg, ex);
  }
}
