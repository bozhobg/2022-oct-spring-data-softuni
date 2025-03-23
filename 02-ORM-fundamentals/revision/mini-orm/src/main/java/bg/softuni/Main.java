package bg.softuni;

import bg.softuni.entities.Course;
import bg.softuni.entities.Student;
import bg.softuni.orm.EntityManager;
import bg.softuni.orm.MyConnector;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args)
            throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {

        EntityManager<Student> emStudent = new EntityManager(MyConnector.getConnection());
        // testing ground
//        Student oldStudent = new Student("a", "666", 13, LocalDate.now());
//        oldStudent.setId(6);
//        em.persist(oldStudent);
//
//        Student newStudent = new Student("last", "lastPass", 38, LocalDate.now());
//        em.persist(newStudent);

//        Student firstWithId = em.findFirst(Student.class, "where id = 2");
//        System.out.println(firstWithId.toString());

//        String where = "";
//        String where = "WHERE `username` LIKE '%bozho%'";
//        String where = "WHERE `age` = 13";
//        System.out.println("===== all students (where = " + where + "): =====");
//
//        for (Student st : em.find(Student.class, where)) {
//            System.out.println(st);
//        }

        EntityManager<Course> emCourse = new EntityManager<>(MyConnector.getConnection());
//        emCourse.doCreate(Course.class);
        emCourse.persist(new Course(
                "Math Fundamentals 2",
                LocalDate.now(),
                LocalDate.now().plusMonths(3)
        ));

//        emCourse.doAlter(new Course());

        Course courseById = emCourse.findFirst(Course.class, "WHERE `id` = 1");
        boolean isDelete = emCourse.delete(courseById);
        System.out.println("Row deleted: " + isDelete);

        MyConnector.getConnection().close();
    }
}