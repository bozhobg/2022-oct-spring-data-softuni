package bg.softuni.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "rounds")
public class Round {
//    Rounds – Id, Name (for example Groups, League, 1/8 Final, 1/4 Final, Semi-Final, Final…)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    private String name;

    public Round() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Round setName(String name) {
        this.name = name;
        return this;
    }
}
