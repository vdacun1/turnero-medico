package edu.up.models.entities;

/**
 * Representa un médico en el sistema.
 * Hereda de Persona y utiliza el DNI como código identificador.
 */
public class MedicoEntity extends PersonaEntity {

  public MedicoEntity() {
    super();
  }

  public MedicoEntity(String nombre, String apellido, String dni) {
    super(nombre, apellido, dni);
  }

  /**
   * Retorna el DNI como código del médico.
   */
  public String getCodigo() {
    return getDni();
  }

  /**
   * Establece el código del médico (actualiza el DNI).
   */
  public void setCodigo(String codigo) {
    setDni(codigo);
  }

  @Override
  public String toString() {
    return "Dr./Dra. " + getNombreCompleto() + " (Código: " + getCodigo() + ")";
  }
}
