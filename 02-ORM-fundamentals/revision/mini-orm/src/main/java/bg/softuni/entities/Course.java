package bg.softuni.entities;

import bg.softuni.orm.annotations.Column;
import bg.softuni.orm.annotations.Entity;
import bg.softuni.orm.annotations.Id;

import java.time.LocalDate;

@Entity(name = "courses")
public class Course {

    @Id
    private long id;

    @Column
    private String name;

    @Column(name = "start_name")
    private LocalDate startDate;

    @Column(name = "end_name")
    private LocalDate endDate;

    @Column
    private String leader;

    public Course() {}

    public Course(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
