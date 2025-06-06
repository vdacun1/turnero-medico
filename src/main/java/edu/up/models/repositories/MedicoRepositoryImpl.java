package edu.up.models.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.up.controllers.exceptions.DataAccessException;
import edu.up.controllers.exceptions.EntityNotFoundException;
import edu.up.controllers.exceptions.NoDataFoundException;
import edu.up.controllers.infrastructure.IDBConnection;
import edu.up.models.entities.MedicoEntity;
import edu.up.utils.Logger;

public class MedicoRepositoryImpl extends AbstractRepository implements MedicoRepository {

  // SQL statements
  private static final String SQL_SELECT_BY_ID = "SELECT id, nombre, apellido, dni FROM medicos WHERE id = ?";
  private static final String SQL_SELECT_BY_CODIGO = "SELECT id, nombre, apellido, dni FROM medicos WHERE dni = ?";
  private static final String SQL_SELECT_ALL = "SELECT id, nombre, apellido, dni FROM medicos ORDER BY apellido, nombre";
  private static final String SQL_SELECT_BY_NOMBRE = "SELECT id, nombre, apellido, dni FROM medicos WHERE nombre LIKE ? OR apellido LIKE ? ORDER BY apellido, nombre";
  private static final String SQL_INSERT_MEDICO = "INSERT INTO medicos(nombre, apellido, dni) VALUES(?, ?, ?)";
  private static final String SQL_UPDATE_MEDICO = "UPDATE medicos SET nombre = ?, apellido = ?, dni = ? WHERE id = ?";
  private static final String SQL_DELETE_BY_ID = "DELETE FROM medicos WHERE id = ?";
  private static final String SQL_DELETE_BY_CODIGO = "DELETE FROM medicos WHERE dni = ?";

  // Error messages
  private static final String MSG_FIND_ERROR = "Error finding medico by id: ";
  private static final String MSG_FIND_BY_CODIGO_ERROR = "Error finding medico by codigo: ";
  private static final String MSG_NOT_FOUND = "Medico not found with id: ";
  private static final String MSG_NOT_FOUND_CODIGO = "Medico not found with codigo: ";
  private static final String MSG_FIND_ALL_ERROR = "Error retrieving all medicos";
  private static final String MSG_FIND_BY_NOMBRE_ERROR = "Error finding medicos by nombre";
  private static final String MSG_NO_DATA = "No medicos found in the database";
  private static final String MSG_NO_DATA_NOMBRE = "No medicos found with nombre containing: ";
  private static final String MSG_SAVE_ERROR = "Error saving medico";
  private static final String MSG_DELETE_ERROR = "Error deleting medico with id: ";
  private static final String MSG_DELETE_BY_CODIGO_ERROR = "Error deleting medico with codigo: ";
  private static final String MSG_NOT_FOUND_DELETE = "Cannot delete; medico not found with id: ";
  private static final String MSG_NOT_FOUND_DELETE_CODIGO = "Cannot delete; medico not found with codigo: ";

  /**
   * Constructor por defecto que utiliza MySQLConnectionManager.
   */
  public MedicoRepositoryImpl() {
    super();
  }

  /**
   * Constructor para inyección de dependencias (útil para testing).
   */
  public MedicoRepositoryImpl(IDBConnection dbConnection) {
    super(dbConnection);
  }

  @Override
  public MedicoEntity findById(Long id) throws EntityNotFoundException, DataAccessException {
    return execute(conn -> {
      try (PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {
        ps.setLong(1, id);
        try (ResultSet rs = ps.executeQuery()) {
          if (!rs.next()) {
            throw new EntityNotFoundException(MSG_NOT_FOUND + id);
          }
          return mapRowToEntity(rs);
        }
      } catch (SQLException e) {
        throw new DataAccessException(MSG_FIND_ERROR + id, e);
      }
    });
  }

  @Override
  public MedicoEntity findByCodigo(String codigo) throws EntityNotFoundException, DataAccessException {
    return execute(conn -> {
      try (PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_CODIGO)) {
        ps.setString(1, codigo);
        try (ResultSet rs = ps.executeQuery()) {
          if (!rs.next()) {
            throw new EntityNotFoundException(MSG_NOT_FOUND_CODIGO + codigo);
          }
          return mapRowToEntity(rs);
        }
      } catch (SQLException e) {
        throw new DataAccessException(MSG_FIND_BY_CODIGO_ERROR + codigo, e);
      }
    });
  }

  @Override
  public List<MedicoEntity> findAll() throws NoDataFoundException, DataAccessException {
    Logger.info("MedicoRepositoryImpl", "Buscando todos los médicos");
    return execute(conn -> {
      try (PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
          ResultSet rs = ps.executeQuery()) {

        List<MedicoEntity> medicos = new ArrayList<>();
        while (rs.next()) {
          medicos.add(mapRowToEntity(rs));
        }

        if (medicos.isEmpty()) {
          Logger.warn("MedicoRepositoryImpl", "No se encontraron médicos en la base de datos");
          throw new NoDataFoundException(MSG_NO_DATA);
        }

        Logger.info("MedicoRepositoryImpl", "Se encontraron " + medicos.size() + " médicos");
        return medicos;
      } catch (SQLException e) {
        Logger.error("MedicoRepositoryImpl", "Error al buscar todos los médicos", e);
        throw new DataAccessException(MSG_FIND_ALL_ERROR, e);
      }
    });
  }

  @Override
  public List<MedicoEntity> findByNombre(String nombre) throws NoDataFoundException, DataAccessException {
    return execute(conn -> {
      try (PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_NOMBRE)) {
        String searchPattern = "%" + nombre + "%";
        ps.setString(1, searchPattern);
        ps.setString(2, searchPattern);

        try (ResultSet rs = ps.executeQuery()) {
          List<MedicoEntity> medicos = new ArrayList<>();
          while (rs.next()) {
            medicos.add(mapRowToEntity(rs));
          }

          if (medicos.isEmpty()) {
            throw new NoDataFoundException(MSG_NO_DATA_NOMBRE + nombre);
          }

          return medicos;
        }
      } catch (SQLException e) {
        throw new DataAccessException(MSG_FIND_BY_NOMBRE_ERROR, e);
      }
    });
  }

  @Override
  public void save(MedicoEntity medico) throws DataAccessException {
    execute(conn -> {
      try {
        if (medico.getId() == null) {
          // Insert new medico
          try (PreparedStatement ps = conn.prepareStatement(SQL_INSERT_MEDICO, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, medico.getNombre());
            ps.setString(2, medico.getApellido());
            ps.setString(3, medico.getDni());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
              throw new DataAccessException(MSG_SAVE_ERROR);
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
              if (generatedKeys.next()) {
                medico.setId(generatedKeys.getLong(1));
              }
            }
          }
        } else {
          // Update existing medico
          try (PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_MEDICO)) {
            ps.setString(1, medico.getNombre());
            ps.setString(2, medico.getApellido());
            ps.setString(3, medico.getDni());
            ps.setLong(4, medico.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
              throw new EntityNotFoundException(MSG_NOT_FOUND + medico.getId());
            }
          }
        }
        return null;
      } catch (SQLException e) {
        throw new DataAccessException(MSG_SAVE_ERROR, e);
      }
    });
  }

  @Override
  public void delete(Long id) throws EntityNotFoundException, DataAccessException {
    execute(conn -> {
      try (PreparedStatement ps = conn.prepareStatement(SQL_DELETE_BY_ID)) {
        ps.setLong(1, id);
        int affectedRows = ps.executeUpdate();

        if (affectedRows == 0) {
          throw new EntityNotFoundException(MSG_NOT_FOUND_DELETE + id);
        }

        return null;
      } catch (SQLException e) {
        throw new DataAccessException(MSG_DELETE_ERROR + id, e);
      }
    });
  }

  @Override
  public void deleteByCodigo(String codigo) throws EntityNotFoundException, DataAccessException {
    execute(conn -> {
      try (PreparedStatement ps = conn.prepareStatement(SQL_DELETE_BY_CODIGO)) {
        ps.setString(1, codigo);
        int affectedRows = ps.executeUpdate();

        if (affectedRows == 0) {
          throw new EntityNotFoundException(MSG_NOT_FOUND_DELETE_CODIGO + codigo);
        }

        return null;
      } catch (SQLException e) {
        throw new DataAccessException(MSG_DELETE_BY_CODIGO_ERROR + codigo, e);
      }
    });
  }

  /**
   * Maps a ResultSet row to a MedicoEntity.
   */
  private MedicoEntity mapRowToEntity(ResultSet rs) throws SQLException {
    MedicoEntity medico = new MedicoEntity();
    medico.setId(rs.getLong("id"));
    medico.setNombre(rs.getString("nombre"));
    medico.setApellido(rs.getString("apellido"));
    medico.setDni(rs.getString("dni"));
    return medico;
  }
}
