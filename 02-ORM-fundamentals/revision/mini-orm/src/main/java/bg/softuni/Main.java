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

    }
}