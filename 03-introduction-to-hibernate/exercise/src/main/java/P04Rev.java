import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class P04Rev {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        em.createQuery("select e.firstName from Employee as e where e.salary > 50000", String.class)
                .getResultList()
                .forEach(System.out::println);
    }
}
