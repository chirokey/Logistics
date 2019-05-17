package com.logistics.dao.impls;

import com.logistics.entity.cargo.Cargo;
import com.logistics.dao.generic.GenericDAOImpl;
import com.logistics.dao.interfaces.CargoDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CargoDAOImpl extends GenericDAOImpl<Cargo, Long> implements CargoDAO {
    @Autowired
    public CargoDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Cargo.class);
    }

    @Override
    public List<Cargo> readByOrderID(Long orderID) {
        Session currentSession = getSessionFactory().getCurrentSession();
        return currentSession
                .createQuery("select c from Cargo c where c.id in " +
                        "(select distinct w.cargo.id from Waypoint w where w.order.id=:orderID)", Cargo.class)
                .setParameter("orderID", orderID)
                .getResultList();
    }
}
