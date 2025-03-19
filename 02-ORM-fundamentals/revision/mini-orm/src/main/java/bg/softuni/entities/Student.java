package bg.softuni.entities;

import bg.softuni.orm.Column;
import bg.softuni.orm.Entity;
import bg.softuni.orm.Id;

import java.time.LocalDate;

@Entity(name = "students")
public class Student {

    @Id
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "age")
    private int age;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    public Student(
            String username,
            String password,
            int age,
            LocalDate registrationDate
    ) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.registrationDate = registrationDate;
    }

}
