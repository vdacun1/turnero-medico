package edu.up.models.entities;

/**
 * Entidad que representa un médico en el sistema.
 * Hereda de PersonaEntity para los datos básicos.
 * El código del médico es el mismo que su DNI.
 */
public class MedicoEntity extends PersonaEntity {

    public MedicoEntity() {
        super();
    }

    public MedicoEntity(String nombre, String apellido, String dni) {
        super(nombre, apellido, dni);
    }

    public MedicoEntity(String nombre, String apellido, String dni, String usuario, String contrasena) {
        super(nombre, apellido, dni, usuario, contrasena);
    }

    public String getCodigo() {
        return getDni(); // El código es el mismo que el DNI
    }

    public void setCodigo(String codigo) {
        setDni(codigo); // Al establecer el código, también se establece el DNI
    }

    @Override
    public String getTipoPersona() {
        return "Médico";
    }

    @Override
    public String toString() {
        return getNombreCompleto() + " (DNI: " + getDni() + ")";
    }
}
