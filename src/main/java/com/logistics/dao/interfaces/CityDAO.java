package com.logistics.dao.interfaces;

import com.logistics.dao.generic.GenericDAO;
import com.logistics.entity.City;

public interface CityDAO extends GenericDAO<City, Long> {
    City readByName(String name);
}
