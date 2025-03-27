package bg.softuni.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "colors")
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String name;

    public Color() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Color setName(String name) {
        this.name = name;
        return this;
    }
}
