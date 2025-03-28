package bg.softuni.entities;

import bg.softuni.entities.enums.CompetitionTypeEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "competition_types")
public class CompetitionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private CompetitionTypeEnum name;

    public CompetitionType() {}

    public long getId() {
        return id;
    }

    public CompetitionTypeEnum getName() {
        return name;
    }

    public CompetitionType setName(CompetitionTypeEnum name) {
        this.name = name;
        return this;
    }
}
