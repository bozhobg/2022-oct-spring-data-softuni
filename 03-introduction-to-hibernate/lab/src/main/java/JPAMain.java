import entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JPAMain {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("alabala-db-unit-name");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Student student1 = new Student("Alabala Student 1");
        entityManager.persist(student1);

        entityManager.detach(student1);

        student1.setName("I am one");

        entityManager.merge(student1);


        transaction.commit();
    }
}
