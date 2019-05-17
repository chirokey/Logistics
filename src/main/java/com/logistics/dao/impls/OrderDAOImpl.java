package com.logistics.dao.impls;

import com.logistics.dao.generic.GenericDAOImpl;
import com.logistics.dao.interfaces.OrderDAO;
import com.logistics.entity.Order;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDAOImpl extends GenericDAOImpl<Order, Long> implements OrderDAO {
    @Autowired
    public OrderDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Order.class);
    }
}
