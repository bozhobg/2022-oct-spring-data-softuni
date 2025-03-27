package bg.softuni.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Basic
    private String address;

    @Basic
    private String email;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column
    private byte[] picture;

    @Column(name = "has_medical_insurance")
    private boolean hasMedicalInsurance;

    @OneToMany(mappedBy = "patient")
    private Set<Visitation> visitations;

    public Patient() {
        this.visitations = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Patient setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Patient setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Patient setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Patient setEmail(String email) {
        this.email = email;
        return this;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Patient setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public byte[] getPicture() {
        return picture;
    }

    public Patient setPicture(byte[] picture) {
        this.picture = picture;
        return this;
    }

    public boolean isHasMedicalInsurance() {
        return hasMedicalInsurance;
    }

    public Patient setHasMedicalInsurance(boolean hasMedicalInsurance) {
        this.hasMedicalInsurance = hasMedicalInsurance;
        return this;
    }

    public Set<Visitation> getVisitations() {
        return visitations;
    }
}
