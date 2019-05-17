package com.logistics.entity.user.driver;

import com.logistics.entity.City;
import com.logistics.entity.truck.Truck;
import com.logistics.entity.user.User;

import javax.persistence.*;

@Entity
@Table(name = "driver")
public class Driver extends User {

    @Column(name = "personalNumber", nullable = false, unique = true)
    private Long personalNumber;

    @Column(name = "monthWorkedHours", nullable = false)
    private Integer monthWorkedHours;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "enum('REST', 'DRIVER_MATE', 'DRIVER')", nullable = false)
    private DriverStatus status;

    @ManyToOne
    @JoinColumn(name = "currentCityId", foreignKey = @ForeignKey(name = "driver_city_fk"), nullable = false)
    private City currentCity;

    @ManyToOne
    @JoinColumn(name = "currentTruckId", foreignKey = @ForeignKey(name = "driver_truck_fk"))
    private Truck currentTruck;

    public Driver() {
    }

    public Integer getMonthWorkedHours() {
        return monthWorkedHours;
    }

    public void setMonthWorkedHours(Integer monthWorkedHours) {
        this.monthWorkedHours = monthWorkedHours;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }

    public Truck getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(Truck currentTruck) {
        this.currentTruck = currentTruck;
    }

    public Long getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(Long personalNumber) {
        this.personalNumber = personalNumber;
    }
}
