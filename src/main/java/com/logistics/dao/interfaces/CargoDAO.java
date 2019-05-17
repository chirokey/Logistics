package com.logistics.dao.interfaces;

import com.logistics.entity.cargo.Cargo;
import com.logistics.dao.generic.GenericDAO;

import java.util.List;

public interface CargoDAO extends GenericDAO<Cargo, Long> {
    List<Cargo> readByOrderID(Long orderID);
}
