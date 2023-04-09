package ru.example.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;

import java.util.List;


public abstract class AbstractHibernateDao<T> implements IGenericDao<T> {
    private Class<T> clazz;
    protected final SessionFactory sessionFactory;

    public AbstractHibernateDao(Class<T> clazz, SessionFactory sessionFactory) {
        this.clazz = clazz;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public T findOne(int id) {
        return (T) getCurrentSession().get(clazz, id);
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
        final T entity = findOne(entityId);
        delete(entity);
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
