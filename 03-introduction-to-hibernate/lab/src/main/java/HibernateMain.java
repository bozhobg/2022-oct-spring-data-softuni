import entities.Student;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class HibernateMain {

    private static final String SELECT_ALL_STUDENTS = "FROM Student";
    private static final String FORMAT_ENTITY_PRINT = "id: %d - %s%n";

    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.configure();

        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student();
        Student student4 = new Student();

        List<Student> studentsToAdd = new ArrayList<>() {
            {
                add(student1);
                add(student2);
                add(student3);
                add(student4);
            }
        };

        for (int i = 1; i <= studentsToAdd.size(); i++) {
            Student current = studentsToAdd.get(i - 1);

            current.setName("I am " + i);

            session.persist(current);
        }

        List<Student> listAllStudents =
                session.createQuery(SELECT_ALL_STUDENTS, Student.class).list();


        Student st3 = session.get(Student.class, 3L);

        session.getTransaction().commit();

        System.out.printf(FORMAT_ENTITY_PRINT, st3.getId(), st3.getName());

        for (Student student : listAllStudents) {
            System.out.printf(FORMAT_ENTITY_PRINT, student.getId(), student.getName());
        }


        session.close();
    }
}
