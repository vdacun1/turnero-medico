package edu.up.controllers.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.up.controllers.infrastructure.MySQLConnectionManager;
import edu.up.models.entities.PacienteEntity;
import edu.up.utils.Logger;

/**
 * Implementación del DAO para pacientes
 */
public class PacienteDAOImpl implements IPacienteDAO {
    
    private final MySQLConnectionManager dbConnection;
    
    public PacienteDAOImpl() {
        this.dbConnection = MySQLConnectionManager.getInstance();
    }
    
    public PacienteDAOImpl(MySQLConnectionManager dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<PacienteEntity> findAll() {
        Logger.info(getClass().getSimpleName(), "Buscando todos los pacientes");
        List<PacienteEntity> pacientes = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                pacientes.add(mapResultSet(rs));
            }
            
            Logger.info(getClass().getSimpleName(), "Se encontraron " + pacientes.size() + " pacientes");
        } catch (SQLException e) {
            Logger.error(getClass().getSimpleName(), "Error al listar pacientes", e);
            throw new RuntimeException("Error al listar pacientes", e);
        }
        
        return pacientes;
    }

    @Override
    public Optional<PacienteEntity> findById(Long id) {
        Logger.info(getClass().getSimpleName(), "Buscando paciente por ID: " + id);
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSet(rs));
                }
            }
            
            return Optional.empty();
        } catch (SQLException e) {
            Logger.error(getClass().getSimpleName(), "Error al buscar paciente por ID: " + id, e);
            throw new RuntimeException("Error al buscar paciente por ID", e);
        }
    }

    @Override
    public PacienteEntity buscarPorDni(String dni) {
        Logger.info(getClass().getSimpleName(), "Buscando paciente por DNI: " + dni);
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_DNI)) {
            
            stmt.setString(1, dni);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
            
        } catch (SQLException e) {
            Logger.error(getClass().getSimpleName(), "Error al buscar paciente por DNI: " + dni, e);
            throw new RuntimeException("Error al buscar paciente por DNI", e);
        }
        
        return null;
    }

    @Override
    public PacienteEntity buscarPorUsuario(String usuario) {
        Logger.info(getClass().getSimpleName(), "Buscando paciente por usuario: " + usuario);
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_USUARIO)) {
            
            stmt.setString(1, usuario);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
            
        } catch (SQLException e) {
            Logger.error(getClass().getSimpleName(), "Error al buscar paciente por usuario: " + usuario, e);
            throw new RuntimeException("Error al buscar paciente por usuario", e);
        }
        
        return null;
    }

    @Override
    public PacienteEntity save(PacienteEntity paciente) {
        Logger.info(getClass().getSimpleName(), "Guardando nuevo paciente: " + paciente.getNombreCompleto());
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getApellido());
            stmt.setString(3, paciente.getDni());
            stmt.setString(4, paciente.getUsuario());
            stmt.setString(5, paciente.getContrasena());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    paciente.setId(rs.getLong(1));
                }
            }
            
            Logger.info(getClass().getSimpleName(), "Paciente creado exitosamente: " + paciente.getNombreCompleto());
            return paciente;
        } catch (SQLException e) {
            Logger.error(getClass().getSimpleName(), "Error al crear paciente: " + paciente.getNombreCompleto(), e);
            throw new RuntimeException("Error al crear paciente", e);
        }
    }

    @Override
    public void update(PacienteEntity paciente) {
        Logger.info(getClass().getSimpleName(), "Actualizando paciente: " + paciente.getNombreCompleto());
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            
            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getApellido());
            stmt.setString(3, paciente.getDni());
            stmt.setString(4, paciente.getUsuario());
            stmt.setString(5, paciente.getContrasena());
            stmt.setLong(6, paciente.getId());
            
            stmt.executeUpdate();
            
            Logger.info(getClass().getSimpleName(), "Paciente actualizado exitosamente: " + paciente.getNombreCompleto());
        } catch (SQLException e) {
            Logger.error(getClass().getSimpleName(), "Error al actualizar paciente: " + paciente.getNombreCompleto(), e);
            throw new RuntimeException("Error al actualizar paciente", e);
        }
    }

    @Override
    public void delete(Long id) {
        Logger.info(getClass().getSimpleName(), "Eliminando paciente con ID: " + id);
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_BY_ID)) {
            
            stmt.setLong(1, id);
            
            stmt.executeUpdate();
            
            Logger.info(getClass().getSimpleName(), "Paciente eliminado exitosamente con ID: " + id);
        } catch (SQLException e) {
            Logger.error(getClass().getSimpleName(), "Error al eliminar paciente con ID: " + id, e);
            throw new RuntimeException("Error al eliminar paciente", e);
        }
    }

    /**
     * Mapea un ResultSet a una entidad PacienteEntity
     */
    private PacienteEntity mapResultSet(ResultSet rs) throws SQLException {
        PacienteEntity paciente = new PacienteEntity();
        paciente.setId(rs.getLong("id"));
        paciente.setNombre(rs.getString("nombre"));
        paciente.setApellido(rs.getString("apellido"));
        paciente.setDni(rs.getString("dni"));
        paciente.setUsuario(rs.getString("usuario"));
        paciente.setContrasena(rs.getString("contrasena"));
        return paciente;
    }
} 