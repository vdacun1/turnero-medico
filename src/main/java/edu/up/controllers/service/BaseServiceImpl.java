package edu.up.controllers.service;

import edu.up.controllers.dao.IBaseDAO;
import edu.up.utils.Logger;
import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<T> implements IBaseService<T> {
    
    protected final IBaseDAO<T> dao;
    
    protected BaseServiceImpl(IBaseDAO<T> dao) {
        this.dao = dao;
        Logger.info(getClass().getSimpleName(), "Inicializando servicio con DAO: " + dao.getClass().getSimpleName());
    }
    
    @Override
    public Optional<T> findById(Long id) {
        Logger.info(getClass().getSimpleName(), "Buscando entidad por ID: " + id);
        return dao.findById(id);
    }
    
    @Override
    public List<T> findAll() {
        Logger.info(getClass().getSimpleName(), "Buscando todas las entidades");
        return dao.findAll();
    }
    
    @Override
    public T save(T entity) {
        Logger.info(getClass().getSimpleName(), "Guardando nueva entidad: " + entity);
        return dao.save(entity);
    }
    
    @Override
    public void update(T entity) {
        Logger.info(getClass().getSimpleName(), "Actualizando entidad: " + entity);
        dao.update(entity);
    }
    
    @Override
    public void delete(Long id) {
        Logger.info(getClass().getSimpleName(), "Eliminando entidad con ID: " + id);
        dao.delete(id);
    }
} 