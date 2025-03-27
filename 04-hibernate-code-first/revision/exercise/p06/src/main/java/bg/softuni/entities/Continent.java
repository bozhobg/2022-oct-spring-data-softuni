package bg.softuni.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "continents")
public class Continent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "continents")
    private Set<Country> countries;

    public Continent() {
        this.countries = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Continent setName(String name) {
        this.name = name;
        return this;
    }

    public Set<Country> getCountries() {
        return countries;
    }
}
