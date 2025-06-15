package edu.up.controllers.service;

import java.util.List;
import java.util.Optional;

public interface IBaseService<T> {
    Optional<T> findById(Long id);
    List<T> findAll();
    T save(T entity);
    void update(T entity);
    void delete(Long id);
} 