package com.logistics.service;

import com.logistics.dao.interfaces.DriverDAO;
import com.logistics.entity.user.driver.Driver;
import com.logistics.dao.interfaces.CityDAO;
import com.logistics.dao.interfaces.TruckDAO;
import com.logistics.dto.user.DriverDTO;
import com.logistics.entity.truck.Truck;
import com.logistics.entity.user.UserRole;
import com.logistics.exceptions.PageNotFoundException;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class DriverService {
    private final static int recordsPerPage = 5;

    private TruckDAO truckDAO;
    private CityDAO cityDAO;
    private DriverDAO driverDAO;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DriverService(DriverDAO driverDAO, ModelMapper modelMapper, CityDAO cityDAO, TruckDAO truckDAO, PasswordEncoder passwordEncoder) {
        this.driverDAO = driverDAO;
        this.modelMapper = modelMapper;
        this.cityDAO = cityDAO;
        this.truckDAO = truckDAO;
        this.passwordEncoder = passwordEncoder;
        modelMapperSettings();
    }

    private void modelMapperSettings() {
        Converter<String, String> encodePassword = ctx -> passwordEncoder.encode(ctx.getSource());
        TypeMap<DriverDTO, Driver> typeMap = modelMapper.createTypeMap(DriverDTO.class, Driver.class);
        typeMap.addMappings(mapper -> mapper.using(encodePassword).map(DriverDTO::getPassword, Driver::setPassword));
    }

    private Driver mapDtoToEntity(DriverDTO driverDTO) {
        Driver driver = modelMapper.map(driverDTO, Driver.class);
        driver.setRole(UserRole.DRIVER);
        driver.setCurrentCity(cityDAO.readByName(driverDTO.getCurrentCity()));
        if (driverDTO.getCurrentTruck().equals("none")) {
            driver.setCurrentTruck(null);
        } else {
            String[] regNumberTokens = driverDTO.getCurrentTruck().split(" ");
            driver.setCurrentTruck(truckDAO.readByRegNumber(regNumberTokens[0], regNumberTokens[1]));
        }
        return driver;
    }

    private DriverDTO mapEntityToDTO(Driver driver) {
        DriverDTO driverDTO = modelMapper.map(driver, DriverDTO.class);
        driverDTO.setCurrentCity(driver.getCurrentCity().getName());
        if (driver.getCurrentTruck() == null) {
            driverDTO.setCurrentTruck("none");
        } else {
            driverDTO.setCurrentTruck(driver.getCurrentTruck().getRegNumberLetters() + " " +
                    driver.getCurrentTruck().getRegNumberDigits());
        }
        driverDTO.setMatchingPassword(driverDTO.getPassword());
        return driverDTO;
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    @SuppressWarnings("all")
    public List<DriverDTO> getDriversOnPageNumber(int pageNumber) {
        if (pageNumber < 1 || pageNumber > getNumberOfPages()) {
            throw new PageNotFoundException();
        }
        List<Driver> driverList = driverDAO.readLimit((pageNumber-1)*recordsPerPage, recordsPerPage);
        List<DriverDTO> driverDTOList = new ArrayList<>();
        for (Driver driver : driverList) {
            driverDTOList.add(mapEntityToDTO(driver));
        }
        return driverDTOList;
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    @SuppressWarnings("all")
    public int getNumberOfPages() {
        Long numberOfRecords = driverDAO.countRows();
        numberOfRecords = numberOfRecords == 0 ? recordsPerPage : numberOfRecords;
        return (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public void addDriver(DriverDTO driverDTO) {
        Driver driver = mapDtoToEntity(driverDTO);
        driverDAO.create(driver);
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public DriverDTO getDriverByID(Long driverID) {
        Driver driver = driverDAO.read(driverID);
        return mapEntityToDTO(driver);
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public void updateDriver(DriverDTO driverDTO) {
        Driver driver = mapDtoToEntity(driverDTO);
        Driver originalDriver = driverDAO.read(driverDTO.getId());
        originalDriver.setUsername(driver.getUsername());
        originalDriver.setFirstName(driver.getFirstName());
        originalDriver.setLastName(driver.getLastName());
        originalDriver.setPersonalNumber(driver.getPersonalNumber());
        originalDriver.setMonthWorkedHours(driver.getMonthWorkedHours());
        originalDriver.setStatus(driver.getStatus());
        originalDriver.setCurrentCity(driver.getCurrentCity());
        if (driverDTO.getCurrentTruck().equals("none")) {
            originalDriver.setCurrentTruck(null);
        } else {
            originalDriver.setCurrentTruck(driver.getCurrentTruck());
        }
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public void deleteDriver(Long driverID) {
        driverDAO.delete(driverID);
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public List<DriverDTO> getAppropriateDrivers(String truckRegNumber, long routeDistance) {
        String[] regNumberTokens = truckRegNumber.split(" ");
        Truck truck = truckDAO.readByRegNumber(regNumberTokens[0], regNumberTokens[1]);
        long hoursForWholeRoute = routeDistance/60000;
        long hoursOnOneDriver = hoursForWholeRoute/truck.getNumberOfDrivers();
        long months = hoursOnOneDriver/176;
        long hoursForCurrentMonth = hoursOnOneDriver - months*176;
        Long cityID = truck.getCurrentCity().getId();
        List<Driver> driverList = driverDAO.getAppropriateDrivers(cityID, (int)hoursForCurrentMonth);
        List<DriverDTO> driverDTOList = new ArrayList<>();
        for (Driver driver : driverList) {
            driverDTOList.add(mapEntityToDTO(driver));
        }
        return driverDTOList;
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public void updateDriversTruck(List<Integer> driverIDs, String truckRegNumber) {
        for (int i = 0; i < driverIDs.size(); i++) {
            for (int j = i+1; j < driverIDs.size(); j++) {
                if (driverIDs.get(i).equals(driverIDs.get(j))) {
                    throw new RuntimeException("equal drivers");
                }
            }
        }
        for (Integer driverID : driverIDs) {
            Driver driver = driverDAO.read((long)driverID);
            driver.setCurrentTruck(truckDAO.readByRegNumber(truckRegNumber));
        }
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public List<DriverDTO> getDriversByTruckRegNumber(String truckRegNumber) {
        Long truckID = truckDAO.readByRegNumber(truckRegNumber).getId();
        List<Driver> drivers = driverDAO.getDriversByTruckID(truckID);
        List<DriverDTO> driverDTOS = new ArrayList<>();
        for (Driver driver : drivers) {
            driverDTOS.add(mapEntityToDTO(driver));
        }
        return driverDTOS;
    }
}
