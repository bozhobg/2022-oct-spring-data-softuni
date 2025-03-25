import entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Pr07Rev {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.createQuery(
                        "select a from Address as a " +
                                "order by a.employees.size desc",
                        Address.class
                )
                .setMaxResults(10)
                .getResultList()
                .forEach((a) -> System.out.printf("%s, %s - %d employees\n",
                        a.getText(), a.getTown().getName(), a.getEmployees().size()));


    }
}
