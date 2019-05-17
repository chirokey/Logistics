package com.logistics.service;

import com.logistics.dao.interfaces.UserDAO;
import com.logistics.entity.user.CustomUserDetails;
import com.logistics.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserDAO userDAO;

    @Autowired
    public CustomUserDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userDAO.readByUsername(username);
            return new CustomUserDetails(user);
        } catch (NoResultException e) {
            throw new UsernameNotFoundException("User with username = " + username + " not found", e);
        }
    }
}
