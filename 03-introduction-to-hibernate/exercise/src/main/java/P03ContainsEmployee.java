import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class P03ContainsEmployee {
    public static void main(String[] args) {
        String[] tokensName = new Scanner(System.in).nextLine().split("\\s+");
        String containsFirstName = tokensName[0];
        String containsLastName = tokensName[1];

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();
//        EntityTransaction transaction = em.getTransaction();

//        List<Employee> employees = em.createQuery("FROM Employee e WHERE e.firstName = :fn AND e.lastName = :ln", Employee.class)
//                .setParameter("fn", containsFirstName)
//                .setParameter("ln", containsLastName)
//                .getResultList();

        Long countForNames = em.createQuery(
                "SELECT COUNT(*) FROM Employee e WHERE e.firstName = :fn AND e.lastName = :ln", Long.class)
                .setParameter("fn", containsFirstName)
                .setParameter("ln", containsLastName)
                .getSingleResult();

        String output = countForNames == 0 ? "No" : "Yes";
        System.out.println(output);
    }
}
