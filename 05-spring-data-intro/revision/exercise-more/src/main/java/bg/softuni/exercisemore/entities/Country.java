package bg.softuni.exercisemore.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "country")
    private Set<Town> towns;

    public Country() {
        this.towns = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Country setName(String name) {
        this.name = name;
        return this;
    }

    public Set<Town> getTowns() {
        return towns;
    }

    public Country setTowns(Set<Town> towns) {
        this.towns = towns;
        return this;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }
}
