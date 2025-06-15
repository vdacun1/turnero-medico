package edu.up.controllers;

import edu.up.controllers.service.IMedicService;
import edu.up.models.entities.MedicoEntity;
import java.util.List;
import java.util.Optional;

public class MedicController {
    private final IMedicService medicService;
    
    public MedicController(IMedicService medicService) {
        this.medicService = medicService;
    }
    
    public Optional<MedicoEntity> findById(Long id) {
        return medicService.findById(id);
    }
    
    public List<MedicoEntity> findAll() {
        return medicService.findAll();
    }
    
    public MedicoEntity save(MedicoEntity medico) {
        return medicService.save(medico);
    }
    
    public void update(MedicoEntity medico) {
        medicService.update(medico);
    }
    
    public void delete(Long id) {
        medicService.delete(id);
    }
    
    public Optional<MedicoEntity> findByCode(String code) {
        return medicService.findByCode(code);
    }
    
    public List<MedicoEntity> findByName(String name) {
        return medicService.findByName(name);
    }
    
    public void deleteByCode(String code) {
        medicService.deleteByCode(code);
    }
} 