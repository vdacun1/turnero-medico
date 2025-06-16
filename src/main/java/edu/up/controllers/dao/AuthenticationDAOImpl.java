package edu.up.controllers.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.up.controllers.infrastructure.MySQLConnectionManager;
import edu.up.models.entities.MedicoEntity;
import edu.up.models.entities.PacienteEntity;
import edu.up.models.entities.PersonaEntity;
import edu.up.utils.ConfigurationManager;
import edu.up.utils.Logger;

/**
 * DAO para manejo de autenticación de usuarios
 */
public class AuthenticationDAOImpl implements IPersonaDAO {
    
    private final MySQLConnectionManager dbConnection;
    
    public AuthenticationDAOImpl() {
        this.dbConnection = MySQLConnectionManager.getInstance();
    }
    
    public AuthenticationDAOImpl(MySQLConnectionManager dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public PersonaEntity autenticar(String usuario, String contrasena) {
        Logger.info(getClass().getSimpleName(), "Intentando autenticar usuario: " + usuario);
        
        // No autenticar credenciales de admin como usuario normal
        if (esAdmin(usuario, contrasena)) {
            Logger.info(getClass().getSimpleName(), "Credenciales de admin detectadas, no procesando como usuario normal");
            return null;
        }
        
        // Buscar en médicos
        PersonaEntity medico = autenticarMedico(usuario, contrasena);
        if (medico != null) {
            Logger.info(getClass().getSimpleName(), "Médico autenticado exitosamente: " + medico.getNombreCompleto());
            return medico;
        }
        
        // Buscar en pacientes
        PersonaEntity paciente = autenticarPaciente(usuario, contrasena);
        if (paciente != null) {
            Logger.info(getClass().getSimpleName(), "Paciente autenticado exitosamente: " + paciente.getNombreCompleto());
            return paciente;
        }
        
        Logger.info(getClass().getSimpleName(), "Credenciales inválidas para usuario: " + usuario);
        return null;
    }
    
    /**
     * Verifica si las credenciales corresponden al administrador
     * @param usuario Nombre de usuario
     * @param contrasena Contraseña
     * @return true si las credenciales son del admin, false en caso contrario
     */
    public boolean esAdmin(String usuario, String contrasena) {
        String adminUser = ConfigurationManager.getAdminUser();
        String adminPassword = ConfigurationManager.getAdminPassword();
        
        Logger.info(getClass().getSimpleName(), String.format("Verificando admin - Usuario recibido: '%s', Admin config: '%s'", usuario, adminUser));
        Logger.info(getClass().getSimpleName(), String.format("Verificando admin - Contraseña recibida: '%s', Admin config: '%s'", contrasena, adminPassword));
        
        boolean isAdmin = usuario.equals(adminUser) && contrasena.equals(adminPassword);
        Logger.info(getClass().getSimpleName(), "¿Es admin? " + isAdmin);
        
        return isAdmin;
    }

    @Override
    public PersonaEntity buscarPorUsuario(String usuario) {
        Logger.info(getClass().getSimpleName(), "Buscando usuario: " + usuario);
        
        // No buscar admin aquí ya que no es una persona
        if (usuario.equals(ConfigurationManager.getAdminUser())) {
            return null;
        }
        
        // Buscar en médicos
        PersonaEntity medico = buscarMedicoPorUsuario(usuario);
        if (medico != null) {
            return medico;
        }
        
        // Buscar en pacientes
        PersonaEntity paciente = buscarPacientePorUsuario(usuario);
        if (paciente != null) {
            return paciente;
        }
        
        return null;
    }
    
    private PersonaEntity autenticarMedico(String usuario, String contrasena) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_AUTHENTICATE_MEDICO)) {
            
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMedico(rs);
                }
            }
            
        } catch (SQLException e) {
            Logger.error(getClass().getSimpleName(), "Error al autenticar médico: " + usuario, e);
        }
        
        return null;
    }
    
    private PersonaEntity autenticarPaciente(String usuario, String contrasena) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_AUTHENTICATE_PACIENTE)) {
            
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPaciente(rs);
                }
            }
            
        } catch (SQLException e) {
            Logger.error(getClass().getSimpleName(), "Error al autenticar paciente: " + usuario, e);
        }
        
        return null;
    }
    
    private PersonaEntity buscarMedicoPorUsuario(String usuario) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_MEDICO_BY_USUARIO)) {
            
            stmt.setString(1, usuario);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMedico(rs);
                }
            }
            
        } catch (SQLException e) {
            Logger.error(getClass().getSimpleName(), "Error al buscar médico por usuario: " + usuario, e);
        }
        
        return null;
    }
    
    private PersonaEntity buscarPacientePorUsuario(String usuario) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_PACIENTE_BY_USUARIO)) {
            
            stmt.setString(1, usuario);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPaciente(rs);
                }
            }
            
        } catch (SQLException e) {
            Logger.error(getClass().getSimpleName(), "Error al buscar paciente por usuario: " + usuario, e);
        }
        
        return null;
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
    
    private PacienteEntity mapResultSetToPaciente(ResultSet rs) throws SQLException {
        PacienteEntity paciente = new PacienteEntity();
        paciente.setId(rs.getLong("id"));
        paciente.setNombre(rs.getString("nombre"));
        paciente.setApellido(rs.getString("apellido"));
        paciente.setDni(rs.getString("dni"));
        paciente.setUsuario(rs.getString("usuario"));
        paciente.setContrasena(rs.getString("contrasena"));
        return paciente;
    }

    // Los siguientes métodos son requeridos por IPersonaDAO pero no se usan para autenticación
    @Override
    public Optional<PersonaEntity> findById(Long id) {
        // No implementado para autenticación
        return Optional.empty();
    }

    @Override
    public List<PersonaEntity> findAll() {
        // No implementado para autenticación
        return new ArrayList<>();
    }

    @Override
    public PersonaEntity save(PersonaEntity entity) {
        // No implementado para autenticación
        return null;
    }

    @Override
    public void update(PersonaEntity entity) {
        // No implementado para autenticación
    }

    @Override
    public void delete(Long id) {
        // No implementado para autenticación
    }
} 