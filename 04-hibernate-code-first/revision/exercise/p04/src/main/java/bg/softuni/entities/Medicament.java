package bg.softuni.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "medicaments")
public class Medicament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private String name;

    @ManyToMany
    @JoinTable(name = "medicaments_diagnoses",
            joinColumns = @JoinColumn(name = "medicament_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "diagnose_id", referencedColumnName = "id"))
    private Set<Diagnose> diagnoses;

    public Medicament() {
        this.diagnoses = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Medicament setName(String name) {
        this.name = name;
        return this;
    }

    public Set<Diagnose> getDiagnoses() {
        return this.diagnoses;
    }
}
