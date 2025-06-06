package edu.up.controllers.exceptions;

/** Lanzada cuando no existe la entidad buscada. */
public class EntityNotFoundException extends RepositoryException {
  public EntityNotFoundException(String msg) {
    super(msg);
  }
}
