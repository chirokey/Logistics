package com.logistics.entity;

import com.logistics.entity.truck.Truck;

import javax.persistence.*;

@Entity
@Table(name = "orderTB")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "complete", nullable = false)
    private Boolean complete;

    @OneToOne
    @JoinColumn(name = "truckId", foreignKey = @ForeignKey(name = "order_truck_fk"), nullable = true)
    private Truck truck;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }
}
