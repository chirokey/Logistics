package com.logistics.dao.impls;

import com.logistics.dao.interfaces.WaypointDAO;
import com.logistics.entity.waypoint.Waypoint;
import com.logistics.dao.generic.GenericDAOImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WaypointDAOImpl extends GenericDAOImpl<Waypoint, Long> implements WaypointDAO {
    @Autowired
    public WaypointDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Waypoint.class);
    }

    @Override
    public List<Waypoint> readByOrderID(Long orderID) {
        Session currentSession = getSessionFactory().getCurrentSession();
        return currentSession
                .createQuery("select w from Waypoint w where w.order.id = :orderID order by w.waypointNumber asc", Waypoint.class)
                .setParameter("orderID", orderID)
                .getResultList();
    }

    @Override
    public List<String> readCitiesNamesByOrderID(Long orderID) {
        Session currentSession = getSessionFactory().getCurrentSession();
        return currentSession
                .createQuery("select distinct w.city.name from Waypoint w where w.order.id = :orderID", String.class)
                .setParameter("orderID", orderID)
                .getResultList();
    }
}
