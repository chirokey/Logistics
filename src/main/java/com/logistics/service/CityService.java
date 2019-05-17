package com.logistics.service;

import com.logistics.dao.interfaces.CityDAO;
import com.logistics.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CityService {
    private CityDAO cityDAO;

    @Autowired
    public CityService(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    @Transactional
    public List<String> getAllCities() {
        List<City> cityList = cityDAO.readAll();
        List<String> cityStringList = new ArrayList<>();
        for (City city : cityList) {
            cityStringList.add(city.getName());
        }
        return cityStringList;
    }
}
