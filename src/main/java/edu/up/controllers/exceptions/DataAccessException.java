package edu.up.controllers.exceptions;

/** Lanzada ante cualquier error de acceso a datos (SQL, driver, etc.). */
public class DataAccessException extends RepositoryException {
  public DataAccessException(String msg) {
    super(msg);
  }

  public DataAccessException(String msg, Throwable ex) {
    super(msg, ex);
  }
}
