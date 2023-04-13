package ru.example.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;

import java.util.List;
import java.util.Optional;


public abstract class AbstractHibernateDao<T> implements IGenericDao<T> {
    private Class<T> clazz;
    protected final SessionFactory sessionFactory;

    public AbstractHibernateDao(Class<T> clazz, SessionFactory sessionFactory) {
        this.clazz = clazz;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<T> findOne(int id) {
        T t = (T) getCurrentSession().get(clazz, id);
        return Optional.ofNullable(t);
    }

    @Override
    public List<T> findAll() {
        return getCurrentSession().createQuery("from " + clazz.getName(), clazz).list();
    }

    @Override
    public T create(T entity) {
        Preconditions.checkNotNull(entity, clazz.getName());
        getCurrentSession().saveOrUpdate(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        Preconditions.checkNotNull(entity, clazz.getName());
        return (T) getCurrentSession().merge(entity);
    }

    @Override
    public void delete(T entity) {
        Preconditions.checkNotNull(entity, clazz.getName());
        getCurrentSession().delete(entity);
    }

    @Override
    public void deleteById(int entityId) {
        final Optional<T> optEntity = findOne(entityId);
        optEntity.ifPresent(this::delete);

    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
