import entities.Address;
import entities.Employee;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class P13RemoveTowns {

    private static final String FORMAT_OUTPUT = "%d addresses in %s deleted%n";
    private static final String FORMAT_SINGLE_OUTPUT = "%d address in %s deleted%n";

    public static void main(String[] args) {
        String removeName = new Scanner(System.in).nextLine();

        EntityManager em = Persistence.createEntityManagerFactory("soft_uni").createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Town townToRemove;

        try {
            townToRemove =
                    em.createQuery("FROM Town t WHERE t.name = :removeName", Town.class)
                            .setParameter("removeName", removeName)
                            .setMaxResults(1)
                            .getSingleResult();
        } catch (NoResultException e) {
            System.out.printf(FORMAT_OUTPUT, 0, removeName);
            return;
        }

        List<Address> addressList = em.createQuery(
                        "FROM Address a WHERE a.town = :townToRemove"
                        , Address.class)
                .setParameter("townToRemove", townToRemove)
                .getResultList();

        for (Address address : addressList) {
            Set<Employee> employees = address.getEmployees();
            for (Employee employee : employees) {
                employee.setAddress(null);
            }
        }

        em.flush();

        int countAddressesRemoved = em.createQuery(
                "DELETE FROM Address a WHERE a.town = :townToRemove")
                .setParameter("townToRemove", townToRemove)
                .executeUpdate();

        em.remove(townToRemove);

        transaction.commit();

        System.out.println();


        if (countAddressesRemoved == 1) {
            System.out.printf(FORMAT_SINGLE_OUTPUT, 1, removeName);
        } else if (countAddressesRemoved == 0 || countAddressesRemoved > 1) {
            System.out.printf(FORMAT_OUTPUT, countAddressesRemoved, removeName);
    }
    }
}

