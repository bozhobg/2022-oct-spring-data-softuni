import entities.Address;
import entities.Employee;
import entities.Town;
import jakarta.persistence.*;

import java.util.List;
import java.util.Scanner;

public class P13 {

    private static final String OUTPUT_FORMAT_SINGLE_OR_NO_ADDRESS = "%d address in %s deleted:";
    private static final String OUTPUT_FORMAT = "%d address in %s deleted";
    private static final String TOWN_NOT_FOUND_FORMAT = "Town: %s not found";

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        String input = new Scanner(System.in).nextLine();

        Town town;
        try {
            town = em.createQuery("""
                            FROM Town AS t
                            WHERE t.name = :input
                            """, Town.class)
                    .setParameter("input", input)
                    .setMaxResults(1)
                    .getSingleResult();

        } catch (NoResultException nre) {
//            TODO: sout: 0 addresses in `town` deleted ?
            em.close();
            System.out.println(String.format(OUTPUT_FORMAT_SINGLE_OR_NO_ADDRESS, 0, input));
            return;
//            throw new NoResultException(String.format(TOWN_NOT_FOUND_FORMAT, input));
        }

        EntityTransaction tr = em.getTransaction();

        tr.begin();
//        chain of clearing data Employee.address -> Address -> Town due to table relations

        List<Address> townAddresses = em.createQuery("""
                        FROM Address AS a
                        WHERE a.town = :existingTown
                        """, Address.class)
                .setParameter("existingTown", town)
                .getResultList();

        List<Employee> employeesWithTownAddress = em.createQuery("""
                        FROM Employee AS e
                        WHERE e.address IN (:townAddresses)
                        """, Employee.class)
                .setParameter("townAddresses", townAddresses)
                .getResultList();

        employeesWithTownAddress.forEach(e -> e.setAddress(null));
        townAddresses.forEach(em::remove);
        em.remove(town);

        tr.commit();

        int addressCount = townAddresses.size();

        if (addressCount > 1) {
            System.out.printf(OUTPUT_FORMAT, addressCount, input);
        } else {
            System.out.printf(OUTPUT_FORMAT_SINGLE_OR_NO_ADDRESS, addressCount, input);
        }

        em.close();
    }
}
