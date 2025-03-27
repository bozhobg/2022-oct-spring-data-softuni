package bg.softuni.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends Person{

    @Column(name = "average_grade")
    private BigDecimal averageGrade;

    @Column(name = "attendance")
    private Double attendance;

    @ManyToMany(mappedBy = "students", fetch = FetchType.EAGER)
    private Set<Course> courses;

    public Student(){
        super();
    }

    public BigDecimal getAverageGrade() {
        return averageGrade;
    }

    public Student setAverageGrade(BigDecimal averageGrade) {
        this.averageGrade = averageGrade;
        return this;
    }

    public Double getAttendance() {
        return attendance;
    }

    public Student setAttendance(Double attendance) {
        this.attendance = attendance;
        return this;
    }
}
