package com.logistics.dao.impls;

import com.logistics.dao.generic.GenericDAOImpl;
import com.logistics.dao.interfaces.TruckDAO;
import com.logistics.entity.truck.Truck;
import com.logistics.entity.truck.TruckStatus;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TruckDAOImpl extends GenericDAOImpl<Truck, Long> implements TruckDAO {

    @Autowired
    public TruckDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Truck.class);
    }

    @Override
    public Truck readByRegNumber(String regNumberLetters, String regNumberDigits) {
        return getSessionFactory().getCurrentSession()
                .createQuery("select t from Truck t " +
                                "where t.regNumberLetters = :regNumberLetters and t.regNumberDigits = :regNumberDigits",
                        Truck.class)
                .setParameter("regNumberLetters", regNumberLetters)
                .setParameter("regNumberDigits", regNumberDigits)
                .uniqueResult();
    }

    @Override
    public Truck readByRegNumber(String regNumber) {
        String[] regNumberTokens = regNumber.split(" ");
        return readByRegNumber(regNumberTokens[0], regNumberTokens[1]);
    }

    @Override
    public List<Truck> readAppropriateTrucks(int weightCapacity) {
        return getSessionFactory().getCurrentSession()
                .createQuery("select t from Truck t " +
                                "where t.status = :status " +
                                       "and t.weightCapacity >= :weightCapacity " +
                                       "and (select count(*) from Order o where o.truck.id = t.id and o.complete = false) = 0",
                        Truck.class)
                .setParameter("status", TruckStatus.SERVICEABLE)
                .setParameter("weightCapacity", weightCapacity)
                .getResultList();
    }
}
