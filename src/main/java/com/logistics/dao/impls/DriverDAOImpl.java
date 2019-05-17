package com.logistics.dao.impls;

import com.logistics.dao.interfaces.DriverDAO;
import com.logistics.entity.user.driver.Driver;
import com.logistics.dao.generic.GenericDAOImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class DriverDAOImpl extends GenericDAOImpl<Driver, Long> implements DriverDAO {
    @Autowired
    public DriverDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Driver.class);
    }

    @Override
    public List<Driver> getAppropriateDrivers(Long cityID, Integer hoursForCurrentMonth) {
        return getSessionFactory().getCurrentSession()
                .createQuery("select d from Driver d where d.currentCity.id = :cityID " +
                        "and d.monthWorkedHours + :hoursForCurrentMonth <= 176 " +
                        "and d.currentTruck is null", Driver.class)
                .setParameter("cityID", cityID)
                .setParameter("hoursForCurrentMonth", hoursForCurrentMonth)
                .getResultList();
    }

    @Override
    public List<Driver> getDriversByTruckID(Long truckID) {
        return getSessionFactory().getCurrentSession()
                .createQuery("select d from Driver d where d.currentTruck.id = :truckID", Driver.class)
                .setParameter("truckID", truckID)
                .getResultList();
    }
}
