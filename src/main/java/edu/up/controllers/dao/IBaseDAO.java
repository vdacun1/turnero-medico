package edu.up.controllers.dao;

import java.util.List;
import java.util.Optional;

public interface IBaseDAO<T> {
    Optional<T> findById(Long id);
    List<T> findAll();
    T save(T entity);
    void update(T entity);
    void delete(Long id);
} 