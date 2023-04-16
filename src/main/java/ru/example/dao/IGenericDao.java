package ru.example.dao;

import java.util.List;
import java.util.Optional;

public interface IGenericDao<T> {

    Optional<T> findOne(final int id);

    List<T> findAll();

    T create(final T entity);

    Optional<T> update(final T entity);

    void delete(final T entity);

    void deleteById(final int entityId);
}