import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Scanner;

public class P06AddingNewAddressAndUpdatingEmployee {

    private static final String NEW_ADDRESS_TEXT = "Vitoshka 15";


    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        final String lastName = new Scanner(System.in).nextLine();

        transaction.begin();

        Address newAddress = new Address();
        newAddress.setText(NEW_ADDRESS_TEXT);
        em.persist(newAddress);

        Employee employee =
                em.createQuery("FROM Employee e WHERE e.lastName = :ln ", Employee.class)
                        .setParameter("ln", lastName)
                        .setMaxResults(1)
                        .getSingleResult();

        employee.setAddress(newAddress);
        em.persist(employee);

        transaction.commit();
    }
}
