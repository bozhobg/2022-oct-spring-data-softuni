package bg.softuni.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "positions")
public class Position {

    @Id
    @Column(columnDefinition = "CHAR(2)", unique = true, nullable = false)
    private char[] id;

    @Column(name = "position_description")
    private String positionDescription;

    public char[] getId() {
        return id;
    }

    public Position setId(char[] id) {
        this.id = id;
        return this;
    }

    public String getPositionDescription() {
        return positionDescription;
    }

    public Position setPositionDescription(String positionDescription) {
        this.positionDescription = positionDescription;
        return this;
    }
}
