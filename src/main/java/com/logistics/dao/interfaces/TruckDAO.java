package com.logistics.dao.interfaces;

import com.logistics.dao.generic.GenericDAO;
import com.logistics.entity.truck.Truck;

import java.util.List;

public interface TruckDAO extends GenericDAO<Truck, Long> {
    Truck readByRegNumber(String regNumberLetters, String regNumberDigits);
    Truck readByRegNumber(String regNumber);
    List<Truck> readAppropriateTrucks(int weightCapacity);
}
