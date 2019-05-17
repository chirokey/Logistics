package com.logistics.entity.waypoint;

import com.logistics.entity.cargo.Cargo;
import com.logistics.entity.City;
import com.logistics.entity.Order;

import javax.persistence.*;

@Entity
@Table(name = "waypoint")
public class Waypoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "waypointNumber", nullable = false)
    private Integer waypointNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "waypointType", columnDefinition = "enum('LOADING', 'UNLOADING')", nullable = false)
    private WaypointType waypointType;

    @ManyToOne
    @JoinColumn(name = "orderId", foreignKey = @ForeignKey(name = "waypoint_order_fk"), nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "cityId", foreignKey = @ForeignKey(name = "waypoint_city_fk"), nullable = false)
    private City city;

    @OneToOne
    @JoinColumn(name = "cargoId", foreignKey = @ForeignKey(name = "waypoint_cargo_fk"), nullable = false)
    private Cargo cargo;

    public Waypoint() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWaypointNumber() {
        return waypointNumber;
    }

    public void setWaypointNumber(Integer waypointNumber) {
        this.waypointNumber = waypointNumber;
    }

    public WaypointType getWaypointType() {
        return waypointType;
    }

    public void setWaypointType(WaypointType waypointType) {
        this.waypointType = waypointType;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}
