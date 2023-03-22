package ru.example.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;

import java.util.List;


public abstract class AbstractHibernateDao <T> implements IGenericDao <T>{
    private Class<T> clazz;
    protected final SessionFactory sessionFactory;

    public AbstractHibernateDao(Class<T> clazz, SessionFactory sessionFactory) {
        this.clazz = clazz;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void setClazz(Class<T> clazzToSet) {
        clazz = Preconditions.checkNotNull(clazzToSet, clazz.getName());
    }

    @Override
    public T findOne(long id) {
        return (T) getCurrentSession().get(clazz, id);
    }

    @Override
    public List<T> findAll() {
        return getCurrentSession().createQuery("from " + clazz.getName()).list();
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
    public void deleteById(long entityId) {
        final T entity = findOne(entityId);
        delete(entity);
    }
    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}