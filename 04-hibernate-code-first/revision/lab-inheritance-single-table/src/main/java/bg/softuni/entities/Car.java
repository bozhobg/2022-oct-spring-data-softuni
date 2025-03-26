package bg.softuni.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("car")
public class Car extends Vehicle {

    @Basic
    private Integer seats;

    public Car() {
        super();
    }

    public Car(String type, String model, BigDecimal price, String fuelType, Integer seats) {
        super(type, model, price, fuelType);
        this.seats = seats;
//        if protected superField allows this.superField = value;
    }

    public Integer getSeats() {
        return seats;
    }

    public Car setSeats(Integer seats) {
        this.seats = seats;
        return this;
    }
}
