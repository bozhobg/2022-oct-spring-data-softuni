package bg.softuni.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @ManyToMany(mappedBy = "drivers", targetEntity = Truck.class)
    private Set<Truck> trucks;

    public Driver() {
    }

    public Driver(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public Driver setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}
