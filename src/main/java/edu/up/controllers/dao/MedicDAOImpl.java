package edu.up.controllers.dao;

import edu.up.controllers.infrastructure.MySQLConnectionManager;
import edu.up.models.entities.MedicoEntity;
import edu.up.utils.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicDAOImpl implements IMedicDAO {
    
    private final MySQLConnectionManager dbConnection;
    
    public MedicDAOImpl() {
        this.dbConnection = MySQLConnectionManager.getInstance();
    }
    
    public MedicDAOImpl(MySQLConnectionManager dbConnection) {
        this.dbConnection = dbConnection;
    }
    
    @Override
    public Optional<MedicoEntity> findById(Long id) {
        Logger.info(getClass().getSimpleName(), "Buscando médico por ID: " + id);
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSetToMedico(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            Logger.error(getClass().getSimpleName(), "Error al buscar médico por ID: " + id, e);
            throw new RuntimeException("Error al buscar médico por ID", e);
        }
    }
    
    @Override
    public List<MedicoEntity> findAll() {
        Logger.info(getClass().getSimpleName(), "Buscando todos los médicos");
        List<MedicoEntity> medicos = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                medicos.add(mapResultSetToMedico(rs));
            }
            return medicos;
        } catch (SQLException e) {
            Logger.error(getClass().getSimpleName(), "Error al buscar todos los médicos", e);
            throw new RuntimeException("Error al buscar todos los médicos", e);
        }
    }
    
    @Override
    public MedicoEntity save(MedicoEntity medico) {
        Logger.info(getClass().getSimpleName(), "Guardando nuevo médico: " + medico);
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 SQL_INSERT,
                 PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, medico.getNombre());
            stmt.setString(2, medico.getApellido());
            stmt.setString(3, medico.getDni());
            stmt.setString(4, medico.getUsuario());
            stmt.setString(5, medico.getContrasena());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                medico.setId(rs.getLong(1));
            }
            return medico;
        } catch (SQLException e) {
            Logger.error(getClass().getSimpleName(), "Error al guardar médico: " + medico, e);
            throw new RuntimeException("Error al guardar médico", e);
        }
    }
    
    @Override
    public void update(MedicoEntity medico) {
        Logger.info(getClass().getSimpleName(), "Actualizando médico: " + medico);
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            stmt.setString(1, medico.getNombre());
            stmt.setString(2, medico.getApellido());
            stmt.setString(3, medico.getDni());
            stmt.setString(4, medico.getUsuario());
            stmt.setString(5, medico.getContrasena());
            stmt.setLong(6, medico.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.error(getClass().getSimpleName(), "Error al actualizar médico: " + medico, e);
            throw new RuntimeException("Error al actualizar médico", e);
        }
    }
    
    @Override
    public void delete(Long id) {
        Logger.info(getClass().getSimpleName(), "Eliminando médico con ID: " + id);
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_BY_ID)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.error(getClass().getSimpleName(), "Error al eliminar médico con ID: " + id, e);
            throw new RuntimeException("Error al eliminar médico", e);
        }
    }
    
    @Override
    public Optional<MedicoEntity> findByCode(String code) {
        Logger.info(getClass().getSimpleName(), "Buscando médico por código: " + code);
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_CODE)) {
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSetToMedico(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            Logger.error(getClass().getSimpleName(), "Error al buscar médico por código: " + code, e);
            throw new RuntimeException("Error al buscar médico por código", e);
        }
    }
    
    @Override
    public List<MedicoEntity> findByName(String name) {
        Logger.info(getClass().getSimpleName(), "Buscando médicos por nombre: " + name);
        List<MedicoEntity> medicos = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_NAME)) {
            String searchPattern = "%" + name + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                medicos.add(mapResultSetToMedico(rs));
            }
            return medicos;
        } catch (SQLException e) {
            Logger.error(getClass().getSimpleName(), "Error al buscar médicos por nombre: " + name, e);
            throw new RuntimeException("Error al buscar médicos por nombre", e);
        }
    }
    
    @Override
    public void deleteByCode(String code) {
        Logger.info(getClass().getSimpleName(), "Eliminando médico por código: " + code);
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_BY_CODE)) {
            stmt.setString(1, code);
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.error(getClass().getSimpleName(), "Error al eliminar médico por código: " + code, e);
            throw new RuntimeException("Error al eliminar médico por código", e);
        }
    }
    
    private MedicoEntity mapResultSetToMedico(ResultSet rs) throws SQLException {
        MedicoEntity medico = new MedicoEntity();
        medico.setId(rs.getLong("id"));
        medico.setNombre(rs.getString("nombre"));
        medico.setApellido(rs.getString("apellido"));
        medico.setDni(rs.getString("dni"));
        medico.setUsuario(rs.getString("usuario"));
        medico.setContrasena(rs.getString("contrasena"));
        return medico;
    }
} 