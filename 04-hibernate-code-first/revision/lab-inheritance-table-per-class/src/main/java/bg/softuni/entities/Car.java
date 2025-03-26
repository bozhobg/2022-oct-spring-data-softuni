package bg.softuni.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cars")
public class Car extends Vehicle {

    @Basic
    private Integer seats;

    @OneToOne(optional = false) // runtime evaluation
    @JoinColumn(name = "plate_number_id", referencedColumnName = "id", nullable = false, unique = true)
    private PlateNumber plateNumber;

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

    public PlateNumber getPlateNumber() {
        return plateNumber;
    }

    public Car setPlateNumber(PlateNumber plateNumber) {
        this.plateNumber = plateNumber;
        return this;
    }
}
