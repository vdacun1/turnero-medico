package edu.up.controllers.service;

import edu.up.models.entities.MedicoEntity;
import java.util.List;
import java.util.Optional;

public interface IMedicService extends IBaseService<MedicoEntity> {
    Optional<MedicoEntity> findByCode(String code);
    List<MedicoEntity> findByName(String name);
    void deleteByCode(String code);
} 