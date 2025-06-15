package edu.up.controllers.service;

import edu.up.controllers.dao.IMedicDAO;
import edu.up.models.entities.MedicoEntity;
import edu.up.utils.Logger;
import java.util.List;
import java.util.Optional;

public class MedicServiceImpl extends BaseServiceImpl<MedicoEntity> implements IMedicService {
    
    private final IMedicDAO medicDAO;
    
    public MedicServiceImpl(IMedicDAO medicDAO) {
        super(medicDAO);
        this.medicDAO = medicDAO;
        Logger.info(getClass().getSimpleName(), "Inicializando servicio de médicos");
    }
    
    @Override
    public Optional<MedicoEntity> findByCode(String code) {
        Logger.info(getClass().getSimpleName(), "Buscando médico por código: " + code);
        return medicDAO.findByCode(code);
    }
    
    @Override
    public List<MedicoEntity> findByName(String name) {
        Logger.info(getClass().getSimpleName(), "Buscando médicos por nombre: " + name);
        return medicDAO.findByName(name);
    }
    
    @Override
    public void deleteByCode(String code) {
        Logger.info(getClass().getSimpleName(), "Eliminando médico por código: " + code);
        medicDAO.deleteByCode(code);
    }
} 