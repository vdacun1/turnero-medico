package edu.up.models.entities;

/**
 * Clase base que representa una persona con datos básicos.
 * Contiene los campos comunes para todas las personas del sistema.
 */
public abstract class PersonaEntity {
  private Long id;
  private String nombre;
  private String apellido;
  private String dni;

  public PersonaEntity() {
  }

  public PersonaEntity(String nombre, String apellido, String dni) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.dni = dni;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  /**
   * Retorna el nombre completo de la persona.
   */
  public String getNombreCompleto() {
    return nombre + " " + apellido;
  }

  @Override
  public String toString() {
    return getNombreCompleto() + " (DNI: " + dni + ")";
  }
}
