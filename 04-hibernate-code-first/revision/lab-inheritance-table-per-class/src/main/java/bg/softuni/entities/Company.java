package bg.softuni.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Basic
    private String name;

    @OneToMany(mappedBy = "company", targetEntity = Plane.class)
    private Set<Plane> planes;

    public Company() {
        this.planes = new HashSet<>();
    }

    public Company(String name) {
        this.name = name;
        this.planes = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Company setName(String name) {
        this.name = name;
        return this;
    }

    public Set<Plane> getPlanes() {
        return planes;
    }

    public Company setPlanes(Set<Plane> planes) {
        this.planes = planes;
        return this;
    }
}
