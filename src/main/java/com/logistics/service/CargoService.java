package com.logistics.service;

import com.logistics.dao.interfaces.CargoDAO;
import com.logistics.dto.CargoDTO;
import com.logistics.entity.cargo.Cargo;
import com.logistics.entity.user.UserRole;
import com.logistics.exceptions.PageNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class CargoService {
    private final static int recordsPerPage = 5;

    private ModelMapper modelMapper;
    private CargoDAO cargoDAO;

    @Autowired
    public CargoService(ModelMapper modelMapper, CargoDAO cargoDAO) {
        this.modelMapper = modelMapper;
        this.cargoDAO = cargoDAO;
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public Long saveCargo(CargoDTO cargoDTO) {
        Cargo cargo = modelMapper.map(cargoDTO, Cargo.class);
        return cargoDAO.create(cargo);
    }

    @Secured(UserRole.RoleString.ADMIN)
    public List<CargoDTO> getCargosOnPageNumber(int pageNumber, List<CargoDTO> cargoDTOList) {
        if (pageNumber < 1 || pageNumber > getNumberOfPages(cargoDTOList)) {
            throw new PageNotFoundException();
        }
        int upperBound = pageNumber * recordsPerPage;
        upperBound = upperBound > cargoDTOList.size() ? cargoDTOList.size() : upperBound;
        return cargoDTOList.subList((pageNumber - 1) * recordsPerPage, upperBound);
    }

    @Secured(UserRole.RoleString.ADMIN)
    public int getNumberOfPages(List<CargoDTO> cargoDTOList) {
        int numberOfRecords = cargoDTOList.size();
        numberOfRecords = numberOfRecords == 0 ? recordsPerPage : numberOfRecords;
        return (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public List<CargoDTO> getLoadsByOrderID(Long orderID) {
        List<Cargo> cargoList = cargoDAO.readByOrderID(orderID);
        Type targetListType = new TypeToken<List<CargoDTO>>() {}.getType();
        return modelMapper.map(cargoList, targetListType);
    }
}
