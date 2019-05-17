package com.logistics.dto.user;

import com.logistics.dto.validation.annotation.ValidEnum;
import com.logistics.entity.user.driver.DriverStatus;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class DriverDTO extends UserDTO {

    @NotNull
    @Positive
    @Digits(integer = 5, fraction = 0)
    private Long personalNumber;

    @NotNull
    @Positive
    @Digits(integer = 3, fraction = 0)
    private Integer monthWorkedHours;

    @NotEmpty
    @ValidEnum(enumClass = DriverStatus.class)
    private String status;

    @NotEmpty
    private String currentCity;

    @NotEmpty
    private String currentTruck;

    public Long getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(Long personalNumber) {
        this.personalNumber = personalNumber;
    }

    public Integer getMonthWorkedHours() {
        return monthWorkedHours;
    }

    public void setMonthWorkedHours(Integer monthWorkedHours) {
        this.monthWorkedHours = monthWorkedHours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(String currentTruck) {
        this.currentTruck = currentTruck;
    }
}

