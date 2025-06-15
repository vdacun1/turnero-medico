package edu.up.controllers.dao;

import edu.up.models.entities.MedicoEntity;
import java.util.List;
import java.util.Optional;

public interface IMedicDAO extends IBaseDAO<MedicoEntity> {
    
    // Constantes SQL para operaciones con médicos
    String SQL_SELECT_BY_ID = "SELECT * FROM medicos WHERE id = ?";
    String SQL_SELECT_ALL = "SELECT * FROM medicos ORDER BY apellido, nombre";
    String SQL_INSERT = "INSERT INTO medicos (nombre, apellido, dni) VALUES (?, ?, ?)";
    String SQL_UPDATE = "UPDATE medicos SET nombre = ?, apellido = ?, dni = ? WHERE id = ?";
    String SQL_DELETE_BY_ID = "DELETE FROM medicos WHERE id = ?";
    String SQL_SELECT_BY_CODE = "SELECT * FROM medicos WHERE dni = ?";
    String SQL_SELECT_BY_NAME = "SELECT * FROM medicos WHERE nombre LIKE ? OR apellido LIKE ? ORDER BY apellido, nombre";
    String SQL_DELETE_BY_CODE = "DELETE FROM medicos WHERE dni = ?";
    
    Optional<MedicoEntity> findByCode(String code);
    List<MedicoEntity> findByName(String name);
    void deleteByCode(String code);
} 