package com.logistics.service;

import com.logistics.dto.TruckDTO;
import com.logistics.dao.interfaces.CityDAO;
import com.logistics.dao.interfaces.TruckDAO;
import com.logistics.dto.CargoDTO;
import com.logistics.entity.truck.Truck;
import com.logistics.entity.user.UserRole;
import com.logistics.exceptions.PageNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class TruckService {
    private final static int recordsPerPage = 5;

    private TruckDAO truckDAO;
    private CityDAO cityDAO;
    private ModelMapper modelMapper;

    @Autowired
    public TruckService(TruckDAO truckDAO, ModelMapper modelMapper, CityDAO cityDAO) {
        this.truckDAO = truckDAO;
        this.cityDAO = cityDAO;
        this.modelMapper = modelMapper;
        modelMapperSettings();
    }

    private void modelMapperSettings() {
        TypeMap<Truck, TruckDTO> typeMap = modelMapper.createTypeMap(Truck.class, TruckDTO.class);
        typeMap.addMapping(src -> src.getCurrentCity().getName(), TruckDTO::setCurrentCity);

        TypeMap<TruckDTO, Truck> typeMapReverse = modelMapper.createTypeMap(TruckDTO.class, Truck.class);
        typeMapReverse.addMapping(src -> src.getCurrentCity(), (dest, v) -> dest.getCurrentCity().setName((String)v));
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public List<TruckDTO> getAllTrucks() {
        List<Truck> truckList = truckDAO.readAll();
        Type targetListType = new TypeToken<List<TruckDTO>>() {}.getType();
        return modelMapper.map(truckList, targetListType);
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public void addTruck(TruckDTO truckDTO) {
        Truck truck = modelMapper.map(truckDTO, Truck.class);
        truck.setCurrentCity(cityDAO.readByName(truck.getCurrentCity().getName()));
        truckDAO.create(truck);
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public void deleteTruck(Long truckID) {
        truckDAO.delete(truckID);
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public TruckDTO getTruckByID(Long truckID) {
        Truck truck = truckDAO.read(truckID);
        return modelMapper.map(truck, TruckDTO.class);
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    @SuppressWarnings("all")
    public List<TruckDTO> getTrucksOnPageNumber(int pageNumber) {
        if (pageNumber < 1 || pageNumber > getNumberOfPages()) {
            throw new PageNotFoundException();
        }
        List<Truck> truckList = truckDAO.readLimit((pageNumber-1)*recordsPerPage, recordsPerPage);
        Type targetListType = new TypeToken<List<TruckDTO>>() {}.getType();
        return modelMapper.map(truckList, targetListType);
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    @SuppressWarnings("all")
    public int getNumberOfPages() {
        Long numberOfRecords = truckDAO.countRows();
        numberOfRecords = numberOfRecords == 0 ? recordsPerPage : numberOfRecords;
        return (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public void updateTruck(TruckDTO truckDTO) {
        Truck truck = modelMapper.map(truckDTO, Truck.class);
        truck.setCurrentCity(cityDAO.readByName(truck.getCurrentCity().getName()));
        truckDAO.update(truck);
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public List<TruckDTO> getAppropriateTrucks(List<CargoDTO> cargoList) {
        int maxCargoWeight = -1;
        for (CargoDTO cargoDTO : cargoList) {
            if (cargoDTO.getWeight() > maxCargoWeight) {
                maxCargoWeight = cargoDTO.getWeight();
            }
        }
        List<Truck> truckList = truckDAO.readAppropriateTrucks(maxCargoWeight);
        Type targetListType = new TypeToken<List<TruckDTO>>() {}.getType();
        return modelMapper.map(truckList, targetListType);
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public int getWeightCapacityByRegNumber(String regNumber) {
        Truck truck = truckDAO.readByRegNumber(regNumber);
        return truck.getWeightCapacity();
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public int getNumberOfDrivers(String regNumber) {
        Truck truck = truckDAO.readByRegNumber(regNumber);
        return truck.getNumberOfDrivers();
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public TruckDTO getTruckByRegNumber(String regNumber) {
        Truck truck = truckDAO.readByRegNumber(regNumber);
        return modelMapper.map(truck, TruckDTO.class);
    }
}
