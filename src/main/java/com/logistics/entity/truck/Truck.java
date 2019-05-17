package com.logistics.entity.truck;

import com.logistics.entity.City;
import com.logistics.entity.Order;

import javax.persistence.*;

@Entity
@Table(name = "truck",
        uniqueConstraints = @UniqueConstraint(name = "regNumber",
                                              columnNames = {"regNumberLetters", "regNumberDigits"})
        )
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "regNumberLetters", columnDefinition = "char(2)", nullable = false)
    private String regNumberLetters;

    @Column(name = "regNumberDigits", columnDefinition = "char(5)", nullable = false)
    private String regNumberDigits;

    @Column(name = "numberOfDrivers", nullable = false)
    private Integer numberOfDrivers;

    @Column(name = "weightCapacity", nullable = false)
    private Integer weightCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "enum('SERVICEABLE', 'FAULTY')", nullable = false)
    private TruckStatus status;

    @ManyToOne()
    @JoinColumn(name = "currentCityId", foreignKey = @ForeignKey(name = "truck_city_fk"), nullable = false)
    private City currentCity;

    @OneToOne(mappedBy="truck")
    private Order order;

    public Truck() {
    }

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

    public TruckStatus getStatus() {
        return status;
    }

    public void setStatus(TruckStatus status) {
        this.status = status;
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }

    public String getRegNumber() {
        return regNumberLetters + " " + regNumberDigits;
    }
}
