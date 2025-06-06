package edu.up.controllers.exceptions;

/** Lanzada cuando la lista resultante está vacía. */
public class NoDataFoundException extends RepositoryException {
  public NoDataFoundException(String msg) {
    super(msg);
  }
}
