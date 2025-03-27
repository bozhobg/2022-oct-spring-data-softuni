package bg.softuni.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "towns")
public class Town {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Country country;

    public Town() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Town setName(String name) {
        this.name = name;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public Town setCountry(Country country) {
        this.country = country;
        return this;
    }
}
