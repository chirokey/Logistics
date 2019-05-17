package com.logistics.dao.generic;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class GenericDAOImpl<T, PK extends Serializable> implements GenericDAO<T,PK> {

    private SessionFactory sessionFactory;

    private Class<T> type;

    public GenericDAOImpl(SessionFactory sessionFactory, Class<T> type) {
        this.sessionFactory = sessionFactory;
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PK create(T object) {
        return (PK) getSessionFactory().getCurrentSession().save(object);
    }

    @Override
    public T read(PK id) {
        return getSessionFactory().getCurrentSession().get(type, id);
    }

    @Override
    public void update(T object) {
        getSessionFactory().getCurrentSession().update(object);
    }

    @Override
    public void delete(T object) {
        getSessionFactory().getCurrentSession().delete(object);
    }

    @Override
    public void delete(PK id) {
        T object = read(id);
        delete(object);
    }

    private CriteriaQuery<T> constructSimpleCriteria(Session currentSession) {
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        Root<T> root = criteria.from(type);
        criteria.select(root);
        return criteria;
    }

    @Override
    public List<T> readAll() {
        Session currentSession = getSessionFactory().getCurrentSession();
        CriteriaQuery<T> criteria = constructSimpleCriteria(currentSession);
        return currentSession.createQuery(criteria).getResultList();
    }

    @Override
    public List<T> readLimit(int offset, int count) {
        Session currentSession = getSessionFactory().getCurrentSession();
        CriteriaQuery<T> criteria = constructSimpleCriteria(currentSession);
        return currentSession
                .createQuery(criteria)
                .setFirstResult(offset)
                .setMaxResults(count)
                .getResultList();
    }

    @Override
    public Long countRows() {
        Session currentSession = getSessionFactory().getCurrentSession();
        CriteriaBuilder builder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        criteria.select(builder.count(criteria.from(type)));
        return currentSession.createQuery(criteria).getSingleResult();
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
