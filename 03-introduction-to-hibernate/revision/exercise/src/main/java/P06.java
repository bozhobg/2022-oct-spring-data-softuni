import entities.Address;
import entities.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class P06 {

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        String lastName = SC.nextLine();
        String inputAddress = SC.nextLine();

//        cannot persist (manage?) data into the database w/out transaction #begin() and #commit()
        transaction.begin();

        Address newAddress = new Address();
        newAddress.setText(inputAddress);
        em.persist(newAddress);

//        updates all employees with given last name

//        em.createQuery("""
//                        UPDATE Employee AS e
//                        WHERE e.lastName = :lastName
//                        """)
//                .setParameter("newAddress", newAddress)
//                .setParameter("lastName", lastName)
//                .executeUpdate();

        Employee employee = em.createQuery("""
                        FROM Employee AS e
                        WHERE e.lastName = :lastName
                        """, Employee.class)
                .setParameter("lastName", lastName)
                .setMaxResults(1)
                .getSingleResult();

        employee.setAddress(newAddress);
        em.persist(employee);

        transaction.commit();

        em.close();
    }
}
