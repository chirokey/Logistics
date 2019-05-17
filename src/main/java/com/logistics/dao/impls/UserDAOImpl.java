package com.logistics.dao.impls;

import com.logistics.dao.generic.GenericDAOImpl;
import com.logistics.dao.interfaces.UserDAO;
import com.logistics.entity.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class UserDAOImpl extends GenericDAOImpl<User, Long> implements UserDAO {

    @Autowired
    public UserDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory, User.class);
    }

    @Override
    @Transactional
    public User readByUsername(String username) {
        Session currentSession = getSessionFactory().getCurrentSession();
        return currentSession
                .createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}
