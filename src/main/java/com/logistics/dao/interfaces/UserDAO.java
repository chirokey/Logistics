package com.logistics.dao.interfaces;

import com.logistics.dao.generic.GenericDAO;
import com.logistics.entity.user.User;

public interface UserDAO extends GenericDAO<User, Long> {
    User readByUsername(String username);
}
