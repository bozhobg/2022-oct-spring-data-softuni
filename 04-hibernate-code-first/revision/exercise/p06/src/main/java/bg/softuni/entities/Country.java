package bg.softuni.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @Column(columnDefinition = "CHAR(3)", unique = true, nullable = false)
    private char[] id;

    @Basic
    private String name;

    @ManyToMany
    @JoinTable(name = "countries_continents",
            joinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "continent_id", referencedColumnName = "id"))
    private Set<Continent> continents;

    public Country() {
        this.continents = new HashSet<>();
    }

    public char[] getId() {
        return id;
    }

    public Country setId(char[] id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Country setName(String name) {
        this.name = name;
        return this;
    }

    public Set<Continent> getContinents() {
        return continents;
    }
}
