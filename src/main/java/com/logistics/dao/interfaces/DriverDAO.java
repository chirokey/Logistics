package com.logistics.dao.interfaces;

import com.logistics.dao.generic.GenericDAO;
import com.logistics.entity.user.driver.Driver;

import java.util.List;

public interface DriverDAO extends GenericDAO<Driver, Long> {
    List<Driver> getAppropriateDrivers(Long cityID, Integer hoursForCurrentMonth);
    List<Driver> getDriversByTruckID(Long truckID);
}
