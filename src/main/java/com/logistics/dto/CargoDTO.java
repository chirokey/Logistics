package com.logistics.dto;

import com.logistics.dto.validation.annotation.DifferentCities;
import com.logistics.dto.validation.annotation.ValidEnum;
import com.logistics.entity.cargo.CargoStatus;

import javax.validation.constraints.*;

@DifferentCities
public class CargoDTO {
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    @Min(1)
    @Max(24000)
    private Integer weight;

    @ValidEnum(enumClass = CargoStatus.class)
    private String status;

    @NotEmpty
    private String loadingCity;

    @NotEmpty
    private String unloadingCity;

    public CargoDTO() {
        status = CargoStatus.PREPARED.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoadingCity() {
        return loadingCity;
    }

    public void setLoadingCity(String loadingCity) {
        this.loadingCity = loadingCity;
    }

    public String getUnloadingCity() {
        return unloadingCity;
    }

    public void setUnloadingCity(String unloadingCity) {
        this.unloadingCity = unloadingCity;
    }

    @Override
    public String toString() {
        return "CargoDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", status='" + status + '\'' +
                ", loadingCity='" + loadingCity + '\'' +
                ", unloadingCity='" + unloadingCity + '\'' +
                '}';
    }
}
