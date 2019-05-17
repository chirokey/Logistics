package com.logistics.dto;

import com.logistics.dto.validation.annotation.ValidEnum;
import com.logistics.entity.waypoint.WaypointType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class WaypointDTO {
    @NotNull
    @Positive
    private Integer waypointNumber;

    @NotEmpty
    @ValidEnum(enumClass = WaypointType.class)
    private String waypointType;

    @NotEmpty
    private String city;

    @NotNull
    private String cargoName;

    public Integer getWaypointNumber() {
        return waypointNumber;
    }

    public void setWaypointNumber(Integer waypointNumber) {
        this.waypointNumber = waypointNumber;
    }

    public String getWaypointType() {
        return waypointType;
    }

    public void setWaypointType(String waypointType) {
        this.waypointType = waypointType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }
}
