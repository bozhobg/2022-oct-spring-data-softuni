package bg.softuni.entities;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
@Table(name = "plate_numbers")
public class PlateNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Basic
    private String number;

    @OneToOne(mappedBy = "plateNumber", targetEntity = Car.class, optional = false)
    private Car car;

    public PlateNumber() {}

    public PlateNumber(String number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public PlateNumber setNumber(String number) {
        this.number = number;
        return this;
    }

    public Car getCar() {
        return car;
    }

    public PlateNumber setCar(Car car) {
        this.car = car;
        return this;
    }
}
