package bg.softuni.entities;

import bg.softuni.entities.enums.CompetitionTypeEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "competitions")
public class Competition {
//    Competitions – Id, Name, Type (local, national, international)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "competition_type_id")
    private CompetitionType competitionType;

    public Competition() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Competition setName(String name) {
        this.name = name;
        return this;
    }

    public CompetitionType getCompetitionType() {
        return competitionType;
    }

    public Competition setCompetitionType(CompetitionType competitionType) {
        this.competitionType = competitionType;
        return this;
    }
}
