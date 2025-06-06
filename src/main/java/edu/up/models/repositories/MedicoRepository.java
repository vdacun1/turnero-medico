package edu.up.models.repositories;

import java.util.List;

import edu.up.controllers.exceptions.EntityNotFoundException;
import edu.up.controllers.exceptions.NoDataFoundException;
import edu.up.models.entities.MedicoEntity;

/**
 * Operaciones CRUD para MedicoEntity.
 * Incluye búsquedas por DNI/código y operaciones estándar.
 */
public interface MedicoRepository {
  MedicoEntity findById(Long id) throws EntityNotFoundException;

  MedicoEntity findByCodigo(String codigo) throws EntityNotFoundException;

  List<MedicoEntity> findAll() throws NoDataFoundException;

  List<MedicoEntity> findByNombre(String nombre) throws NoDataFoundException;

  void save(MedicoEntity medico);

  void delete(Long id) throws EntityNotFoundException;

  void deleteByCodigo(String codigo) throws EntityNotFoundException;
}
