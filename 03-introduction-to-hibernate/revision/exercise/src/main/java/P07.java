import entities.Address;
import entities.Town;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class P07 {

    private static final String OUTPUT_FORMAT = "%s, %s - %d employees%n";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        StringBuilder sb = new StringBuilder();

//        List<Address> result = em.createQuery("""
//                        FROM Address AS a
//                        JOIN Employee AS e ON e.address = a
//                        GROUP BY a.id
//                        ORDER BY COUNT(e) DESC
//                        LIMIT 10
//                        """, Address.class)
//                .getResultList();

        List<Address> result = em.createQuery("""
                        SELECT a
                        FROM Address AS a
                        ORDER BY size(employees) DESC
                        """, Address.class)
                .setMaxResults(10)
                .getResultList();

        for (Address a : result) {
            Town town = a.getTown();

            sb.append(String.format(
                    OUTPUT_FORMAT,
                    a.getText(),
                    town == null ? "[null]" : town.getName(),
                    a.getEmployees().size()));
        }

        System.out.println(sb);

        em.close();
    }
}
