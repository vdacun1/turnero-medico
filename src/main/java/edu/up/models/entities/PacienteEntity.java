package edu.up.models.entities;

/**
 * Entidad que representa un paciente en el sistema.
 * Hereda de PersonaEntity para los datos básicos.
 */
public class PacienteEntity extends PersonaEntity {

    public PacienteEntity() {
        super();
    }

    public PacienteEntity(String nombre, String apellido, String dni) {
        super(nombre, apellido, dni);
    }

    public PacienteEntity(String nombre, String apellido, String dni, String usuario, String contrasena) {
        super(nombre, apellido, dni, usuario, contrasena);
    }

    @Override
    public String getTipoPersona() {
        return "Paciente";
    }

    @Override
    public String toString() {
        return getNombreCompleto() + " (DNI: " + getDni() + ") - Paciente";
    }
} 