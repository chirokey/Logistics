package com.logistics.dto;

import com.logistics.dto.validation.annotation.ValidEnum;
import com.logistics.entity.truck.TruckStatus;

import javax.validation.constraints.*;

public class TruckDTO {
    private Long id;

    private String regNumber;

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z]{2}", message = "2 letters")
    private String regNumberLetters;

    @NotEmpty
    @Pattern(regexp = "\\d{5}", message = "5 digits")
    private String regNumberDigits;

    @NotNull
    @Min(1)
    @Max(3)
    private Integer numberOfDrivers;

    @NotNull
    @Min(10)
    @Max(24)
    private Integer weightCapacity;

    @NotEmpty
    @ValidEnum(enumClass = TruckStatus.class)
    private String status;

    @NotEmpty
    private String currentCity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegNumberLetters() {
        return regNumberLetters;
    }

    public void setRegNumberLetters(String regNumberLetters) {
        this.regNumberLetters = regNumberLetters;
    }

    public String getRegNumberDigits() {
        return regNumberDigits;
    }

    public void setRegNumberDigits(String regNumberDigits) {
        this.regNumberDigits = regNumberDigits;
    }

    public Integer getNumberOfDrivers() {
        return numberOfDrivers;
    }

    public void setNumberOfDrivers(Integer numberOfDrivers) {
        this.numberOfDrivers = numberOfDrivers;
    }

    public Integer getWeightCapacity() {
        return weightCapacity;
    }

    public void setWeightCapacity(Integer weightCapacity) {
        this.weightCapacity = weightCapacity;
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

    public String getRegNumber() {
        return getRegNumberLetters() + " " + getRegNumberDigits();
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }
}
