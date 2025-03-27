package bg.softuni.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "diagnoses")
public class Diagnose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private String name;

    @Basic
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Visitation visitation;

    public Diagnose() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Diagnose setName(String name) {
        this.name = name;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Diagnose setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Visitation getVisitation() {
        return visitation;
    }

    public Diagnose setVisitation(Visitation visitation) {
        this.visitation = visitation;
        return this;
    }
}
