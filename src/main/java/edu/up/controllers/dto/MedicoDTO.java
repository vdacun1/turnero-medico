package edu.up.controllers.dto;

/**
 * DTO (Data Transfer Object) para transferir datos de médicos
 * entre la vista y el controlador sin exponer entidades
 */
public class MedicoDTO {
    
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    
    // Constructor vacío
    public MedicoDTO() {
    }
    
    // Constructor con datos básicos
    public MedicoDTO(String nombre, String apellido, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }
    
    // Constructor completo
    public MedicoDTO(Long id, String nombre, String apellido, String dni) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }
    
    // Getters y Setters
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
    
    // Método de utilidad para obtener nombre completo
    public String getNombreCompleto() {
        return (nombre != null ? nombre : "") + " " + (apellido != null ? apellido : "");
    }
    
    @Override
    public String toString() {
        return "MedicoDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                '}';
    }
} 