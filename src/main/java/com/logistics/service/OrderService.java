package com.logistics.service;

import com.logistics.dao.interfaces.OrderDAO;
import com.logistics.dao.interfaces.TruckDAO;
import com.logistics.dto.OrderDTO;
import com.logistics.entity.Order;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final static int recordsPerPage = 5;

    private ModelMapper modelMapper;
    private OrderDAO orderDAO;
    private TruckDAO truckDAO;

    @Autowired
    public OrderService(ModelMapper modelMapper, OrderDAO orderDAO, TruckDAO truckDAO) {
        this.modelMapper = modelMapper;
        this.orderDAO = orderDAO;
        this.truckDAO = truckDAO;
        modelMapperSettings();
    }

    private void modelMapperSettings() {
        TypeMap<Order, OrderDTO> typeMap = modelMapper.createTypeMap(Order.class, OrderDTO.class);
        typeMap.addMapping(src -> src.getTruck().getRegNumber(), OrderDTO::setTruck);
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public Long addOrder(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        return orderDAO.create(order);
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public void updateOrderTruck(OrderDTO orderDTO) {
        Order order = orderDAO.read(orderDTO.getId());
        order.setTruck(truckDAO.readByRegNumber(orderDTO.getTruck()));
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    @SuppressWarnings("all")
    public List<OrderDTO> getOrdersOnPageNumber(int pageNumber) {
        if (pageNumber < 1 || pageNumber > getNumberOfPages()) {
            throw new PageNotFoundException();
        }
        List<Order> orderList = orderDAO.readLimit((pageNumber-1)*recordsPerPage, recordsPerPage);
        Type targetListType = new TypeToken<List<OrderDTO>>() {}.getType();
        return modelMapper.map(orderList, targetListType);
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    @SuppressWarnings("all")
    public int getNumberOfPages() {
        Long numberOfRecords = orderDAO.countRows();
        numberOfRecords = numberOfRecords == 0 ? recordsPerPage : numberOfRecords;
        return (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public String getTruckRegNumber(Long orderID) {
        Order order = orderDAO.read(orderID);
        return order.getTruck().getRegNumber();
    }

    @Secured(UserRole.RoleString.ADMIN)
    @Transactional
    public void deleteOrder(Long orderID) {

    }
}
