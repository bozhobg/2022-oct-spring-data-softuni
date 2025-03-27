package bg.softuni.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "visitations")
public class Visitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private Date date;

    @Basic
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Patient patient;

    public Visitation() {
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Visitation setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Visitation setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Patient getPatient() {
        return patient;
    }

    public Visitation setPatient(Patient patient) {
        this.patient = patient;
        return this;
    }


}
