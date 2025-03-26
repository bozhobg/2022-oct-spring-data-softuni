package bg.softuni.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trucks")
public class Truck extends Vehicle {

    @Column(name = "load_capacity")
    private Double loadCapacity;

    @ManyToMany
    @JoinTable(
            name = "trucks_drivers",
            joinColumns = @JoinColumn(name = "truck_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "driver_id", referencedColumnName = "id")
    )
    private Set<Driver> drivers;

    public Truck() {
        this.drivers = new HashSet<>();
    }

    public Truck(String type, String model, BigDecimal price, String fuelType, Double loadCapacity) {
        super(type, model, price, fuelType);
        this.drivers = new HashSet<>();
        this.loadCapacity = loadCapacity;
    }

    public Double getLoadCapacity() {
        return loadCapacity;
    }

    public Truck setLoadCapacity(Double loadCapacity) {
        this.loadCapacity = loadCapacity;
        return this;
    }
}
