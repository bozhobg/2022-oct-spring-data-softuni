package bg.softuni;

import bg.softuni.entities.Student;
import bg.softuni.orm.EntityManager;
import bg.softuni.orm.MyConnector;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args)
            throws SQLException, IllegalAccessException {

        EntityManager<Student> em = new EntityManager(MyConnector.getConnection());
        // testing ground
        Student oldStudent = new Student("a", "666", 13, LocalDate.now());
        oldStudent.setId(6);
        em.persist(oldStudent);

        Student newStudent = new Student("last", "lastPass", 38, LocalDate.now());
        em.persist(newStudent);

        MyConnector.getConnection().close();
    }
}