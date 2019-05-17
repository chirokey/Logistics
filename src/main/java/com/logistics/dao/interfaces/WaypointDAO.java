package com.logistics.dao.interfaces;

import com.logistics.dao.generic.GenericDAO;
import com.logistics.entity.waypoint.Waypoint;

import java.util.List;

public interface WaypointDAO extends GenericDAO<Waypoint, Long> {
    List<Waypoint> readByOrderID(Long orderID);
    List<String> readCitiesNamesByOrderID(Long orderID);
}
