import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class P04 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tr = em.getTransaction();

//        tr.begin();

        List<String> result = em.createQuery("""
                        SELECT e.firstName 
                        FROM Employee AS e
                        WHERE e.salary > 50000  
                        """, String.class)
                .getResultList();

        result.stream().forEach(System.out::println);

//        tr.commit();

        em.close();
    }
}
