package bg.softuni.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher extends Person {

    @Column(name = "email")
    private String email;

    @Column(name = "salary_per_hour")
    private BigDecimal salaryPerHour;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER)
    private Set<Course> courses;

    public Teacher() {
        super();
        this.courses = new HashSet<>();
    }

    public BigDecimal getSalaryPerHour() {
        return salaryPerHour;
    }

    public Teacher setSalaryPerHour(BigDecimal salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Teacher setEmail(String email) {
        this.email = email;
        return this;
    }

    public Set<Course> getCourses() {
        return courses;
    }
}
