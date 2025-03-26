package bg.softuni.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "planes")
public class Plane extends Vehicle {

    @Column(name = "passenger_capacity")
    private Integer passengerCapacity;

    public Plane() {
    }

    public Plane(String type, String model, BigDecimal price, String fuelType, Integer passengerCapacity) {
        super(type, model, price, fuelType);
        this.passengerCapacity = passengerCapacity;
    }

    public Integer getPassengerCapacity() {
        return passengerCapacity;
    }

    public Plane setPassengerCapacity(Integer passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
        return this;
    }
}

