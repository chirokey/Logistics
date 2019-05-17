package com.logistics.dao.impls;

import com.logistics.dao.generic.GenericDAOImpl;
import com.logistics.dao.interfaces.CityDAO;
import com.logistics.entity.City;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public class CityDAOImpl extends GenericDAOImpl<City, Long> implements CityDAO {
    @Autowired
    public CityDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory, City.class);
    }

    @Override
    @Transactional
    public City readByName(String name) {
        Session currentSession = getSessionFactory().getCurrentSession();
        return currentSession
                .createQuery("select c from City c where c.name = :name", City.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
