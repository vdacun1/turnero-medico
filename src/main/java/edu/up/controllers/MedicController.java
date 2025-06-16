package edu.up.controllers;

import edu.up.controllers.dto.MedicoDTO;
import edu.up.controllers.service.IMedicService;
import edu.up.models.entities.MedicoEntity;
import edu.up.utils.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador para manejar operaciones de médicos
 * Actúa como intermediario entre la vista y el servicio
 * Maneja la conversión entre DTOs y entidades
 */
public class MedicController {
    private final IMedicService medicService;
    
    public MedicController(IMedicService medicService) {
        this.medicService = medicService;
    }
    
    /**
     * Resultado de operaciones del controlador
     */
    public static class OperationResult {
        private final boolean exitoso;
        private final String mensaje;
        
        public OperationResult(boolean exitoso, String mensaje) {
            this.exitoso = exitoso;
            this.mensaje = mensaje;
        }
        
        public boolean isExitoso() {
            return exitoso;
        }
        
        public String getMensaje() {
            return mensaje;
        }
    }
    
    /**
     * Obtiene todos los médicos como DTOs
     */
    public List<MedicoDTO> obtenerTodosMedicos() {
        Logger.info("MedicController", "Obteniendo todos los médicos");
        try {
            List<MedicoEntity> entidades = medicService.findAll();
            return entidades.stream()
                    .map(this::convertirADTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            Logger.error("MedicController", "Error al obtener médicos", e);
            throw new RuntimeException("Error al cargar médicos", e);
        }
    }
    
    /**
     * Guarda un médico desde un DTO
     */
    public OperationResult guardarMedico(MedicoDTO medicoDTO) {
        Logger.info("MedicController", "Guardando médico: " + medicoDTO);
        
        try {
            // Validar datos
            OperationResult validacion = validarDatosMedico(medicoDTO);
            if (!validacion.isExitoso()) {
                return validacion;
            }
            
            // Convertir DTO a entidad
            MedicoEntity entidad = convertirAEntidad(medicoDTO);
            
            // Guardar
            medicService.save(entidad);
            
            Logger.info("MedicController", "Médico guardado exitosamente");
            return new OperationResult(true, "Médico guardado exitosamente");
            
        } catch (Exception e) {
            Logger.error("MedicController", "Error al guardar médico", e);
            return new OperationResult(false, "Error al guardar médico: " + e.getMessage());
        }
    }
    
    /**
     * Elimina un médico por ID
     */
    public OperationResult eliminarMedico(Long id) {
        Logger.info("MedicController", "Eliminando médico con ID: " + id);
        
        try {
            if (id == null) {
                return new OperationResult(false, "ID de médico inválido");
            }
            
            medicService.delete(id);
            Logger.info("MedicController", "Médico eliminado exitosamente");
            return new OperationResult(true, "Médico eliminado exitosamente");
            
        } catch (Exception e) {
            Logger.error("MedicController", "Error al eliminar médico", e);
            return new OperationResult(false, "Error al eliminar médico: " + e.getMessage());
        }
    }
    
    /**
     * Valida los datos de un médico
     */
    private OperationResult validarDatosMedico(MedicoDTO medico) {
        if (medico == null) {
            return new OperationResult(false, "Datos de médico inválidos");
        }
        
        if (medico.getNombre() == null || medico.getNombre().trim().isEmpty()) {
            return new OperationResult(false, "El nombre es obligatorio");
        }
        
        if (medico.getApellido() == null || medico.getApellido().trim().isEmpty()) {
            return new OperationResult(false, "El apellido es obligatorio");
        }
        
        if (medico.getDni() == null || medico.getDni().trim().isEmpty()) {
            return new OperationResult(false, "El DNI es obligatorio");
        }
        
        return new OperationResult(true, "Datos válidos");
    }
    
    /**
     * Convierte una entidad a DTO
     */
    private MedicoDTO convertirADTO(MedicoEntity entidad) {
        return new MedicoDTO(
                entidad.getId(),
                entidad.getNombre(),
                entidad.getApellido(),
                entidad.getDni()
        );
    }
    
    /**
     * Convierte un DTO a entidad
     */
    private MedicoEntity convertirAEntidad(MedicoDTO dto) {
        MedicoEntity entidad = new MedicoEntity(
                dto.getNombre(),
                dto.getApellido(),
                dto.getDni()
        );
        if (dto.getId() != null) {
            entidad.setId(dto.getId());
        }
        return entidad;
    }
} 