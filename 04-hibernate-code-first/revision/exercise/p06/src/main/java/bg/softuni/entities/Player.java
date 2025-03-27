package bg.softuni.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class Player {
//    Players – Id, Name, Squad Number, Team, Position, Is Currently Injured

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    private String name;

    @Column(name = "squad_number")
    private Byte squadNumber;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    private Position position;

    @Column(name = "is_injured")
    private boolean isInjured;

    public Player() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Player setName(String name) {
        this.name = name;
        return this;
    }

    public Byte getSquadNumber() {
        return squadNumber;
    }

    public Player setSquadNumber(Byte squadNumber) {
        this.squadNumber = squadNumber;
        return this;
    }

    public Team getTeam() {
        return team;
    }

    public Player setTeam(Team team) {
        this.team = team;
        return this;
    }

    public Position getPosition() {
        return position;
    }

    public Player setPosition(Position position) {
        this.position = position;
        return this;
    }

    public boolean isInjured() {
        return isInjured;
    }

    public Player setInjured(boolean injured) {
        isInjured = injured;
        return this;
    }
}
