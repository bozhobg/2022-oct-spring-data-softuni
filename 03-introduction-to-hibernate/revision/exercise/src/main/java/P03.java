import entities.Employee;
import jakarta.persistence.*;

import java.util.Scanner;

public class P03 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        String input = new Scanner(System.in).nextLine();
        String[] splitInput = input.split(" ");

        transaction.begin();

        boolean isNotFound = em.createQuery("""
                        FROM Employee as e
                        WHERE e.firstName LIKE :fn AND e.lastName = :ln
                        """, Employee.class)
                .setParameter("fn", splitInput[0])
                .setParameter("ln", splitInput[1])
                .getResultList().isEmpty();

        transaction.commit();

        System.out.println(isNotFound ? "No" : "Yes");

        em.close();
    }
}
